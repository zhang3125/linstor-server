package com.linbit.linstor.core;

import com.linbit.GuiceConfigModule;
import com.linbit.ImplementationError;
import com.linbit.ServiceName;
import com.linbit.SystemService;
import com.linbit.SystemServiceStartException;
import com.linbit.drbd.md.MetaDataModule;
import com.linbit.linstor.ControllerDatabase;
import com.linbit.linstor.ControllerLinstorModule;
import com.linbit.linstor.InitializationException;
import com.linbit.linstor.InternalApiConsts;
import com.linbit.linstor.LinStorModule;
import com.linbit.linstor.annotation.SystemContext;
import com.linbit.linstor.api.ApiModule;
import com.linbit.linstor.api.ApiType;
import com.linbit.linstor.api.BaseApiCall;
import com.linbit.linstor.api.prop.WhitelistProps;
import com.linbit.linstor.api.protobuf.ProtobufApiCall;
import com.linbit.linstor.api.protobuf.ProtobufApiType;
import com.linbit.linstor.api.rest.v1.config.GrizzlyHttpService;
import com.linbit.linstor.core.apicallhandler.ApiCallHandlerModule;
import com.linbit.linstor.core.apicallhandler.controller.CtrlApiCallHandlerModule;
import com.linbit.linstor.core.cfg.CtrlConfig;
import com.linbit.linstor.core.cfg.CtrlConfigModule;
import com.linbit.linstor.core.objects.Node;
import com.linbit.linstor.dbcp.DbInitializer;
import com.linbit.linstor.dbdrivers.ControllerDbModule;
import com.linbit.linstor.dbdrivers.DatabaseDriverInfo;
import com.linbit.linstor.debug.ControllerDebugModule;
import com.linbit.linstor.debug.DebugConsole;
import com.linbit.linstor.debug.DebugConsoleCreator;
import com.linbit.linstor.debug.DebugConsoleImpl;
import com.linbit.linstor.debug.DebugModule;
import com.linbit.linstor.event.EventModule;
import com.linbit.linstor.event.handler.EventHandler;
import com.linbit.linstor.event.handler.protobuf.controller.ResourceStateEventHandler;
import com.linbit.linstor.event.handler.protobuf.controller.VolumeDiskStateEventHandler;
import com.linbit.linstor.event.serializer.EventSerializer;
import com.linbit.linstor.event.serializer.protobuf.common.ResourceStateEventSerializer;
import com.linbit.linstor.event.serializer.protobuf.common.VolumeDiskStateEventSerializer;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.logging.LoggingModule;
import com.linbit.linstor.logging.StdErrorReporter;
import com.linbit.linstor.netcom.NetComModule;
import com.linbit.linstor.numberpool.DbNumberPoolInitializer;
import com.linbit.linstor.numberpool.NumberPoolModule;
import com.linbit.linstor.propscon.Props;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.security.ControllerSecurityModule;
import com.linbit.linstor.security.DbCoreObjProtInitializer;
import com.linbit.linstor.security.DbSecurityInitializer;
import com.linbit.linstor.security.Privilege;
import com.linbit.linstor.security.SecurityModule;
import com.linbit.linstor.tasks.LogArchiveTask;
import com.linbit.linstor.tasks.PingTask;
import com.linbit.linstor.tasks.ReconnectorTask;
import com.linbit.linstor.tasks.RetryResourcesTask;
import com.linbit.linstor.tasks.TaskScheduleService;
import com.linbit.linstor.timer.CoreTimer;
import com.linbit.linstor.timer.CoreTimerModule;
import com.linbit.linstor.transaction.ControllerTransactionMgrModule;

import javax.inject.Inject;
import javax.inject.Named;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.event.Level;

/**
 * linstor controller prototype
 *
 * @author Robert Altnoeder &lt;robert.altnoeder@linbit.com&gt;
 */
public final class Controller
{
    private static final String PROPSCON_KEY_NETCOM = "netcom";

    public static final int API_VERSION = 4;
    public static final int API_MIN_VERSION = API_VERSION;

    // Error & exception logging facility
    private final ErrorReporter errorReporter;

    // System security context
    private final AccessContext sysCtx;

    private final CoreTimer timerEventSvc;

    // Synchronization lock for major global changes
    private final ReadWriteLock reconfigurationLock;

    // Map of controllable system services
    private final Map<ServiceName, SystemService> systemServicesMap;

    // Database connection pool service
    private final ControllerDatabase controllerDb;

    private final DbInitializer dbInitializer;
    private final DbSecurityInitializer dbSecurityInitializer;
    private final DbCoreObjProtInitializer dbCoreObjProtInitializer;
    private final DbDataInitializer dbDataInitializer;
    private final DbNumberPoolInitializer dbNumberPoolInitializer;

    private final ApplicationLifecycleManager applicationLifecycleManager;

    // Controller configuration properties
    private final Props ctrlConf;

    // Map of all managed nodes
    private final CoreModule.NodesMap nodesMap;

    private final TaskScheduleService taskScheduleService;
    private final PingTask pingTask;
    private final ReconnectorTask reconnectorTask;
    private final LogArchiveTask logArchiveTask;

    private final DebugConsoleCreator debugConsoleCreator;
    private final ControllerNetComInitializer controllerNetComInitializer;

    private final WhitelistProps whitelistProps;

    private RetryResourcesTask retryResourcesTask;

    private final CtrlConfig ctrlCfg;

    @Inject
    public Controller(
        ErrorReporter errorReporterRef,
        @SystemContext
        AccessContext sysCtxRef,
        CoreTimer timerEventSvcRef,
        @Named(CoreModule.RECONFIGURATION_LOCK)
        ReadWriteLock reconfigurationLockRef,
        Map<ServiceName, SystemService> systemServicesMapRef,
        ControllerDatabase controllerDatabaseRef,
        DbInitializer dbConnectionPoolInitializerRef,
        DbSecurityInitializer dbSecurityInitializerRef,
        DbCoreObjProtInitializer dbCoreObjProtInitializerRef,
        DbDataInitializer dbDataInitializerRef,
        DbNumberPoolInitializer dbNumberPoolInitializerRef,
        ApplicationLifecycleManager applicationLifecycleManagerRef,
        @Named(LinStor.CONTROLLER_PROPS)
        Props ctrlConfRef,
        CoreModule.NodesMap nodesMapRef,
        TaskScheduleService taskScheduleServiceRef,
        PingTask pingTaskRef,
        ReconnectorTask reconnectorTaskRef,
        RetryResourcesTask retryResourcesTaskRef,
        LogArchiveTask logArchiveTaskRef,
        DebugConsoleCreator debugConsoleCreatorRef,
        ControllerNetComInitializer controllerNetComInitializerRef,
        WhitelistProps whitelistPropsRef,
        CtrlConfig ctrlCfgRef
    )
    {
        errorReporter = errorReporterRef;
        sysCtx = sysCtxRef;
        timerEventSvc = timerEventSvcRef;
        reconfigurationLock = reconfigurationLockRef;
        systemServicesMap = systemServicesMapRef;
        controllerDb = controllerDatabaseRef;
        dbInitializer = dbConnectionPoolInitializerRef;
        dbSecurityInitializer = dbSecurityInitializerRef;
        dbCoreObjProtInitializer = dbCoreObjProtInitializerRef;
        dbDataInitializer = dbDataInitializerRef;
        dbNumberPoolInitializer = dbNumberPoolInitializerRef;
        applicationLifecycleManager = applicationLifecycleManagerRef;
        ctrlConf = ctrlConfRef;
        nodesMap = nodesMapRef;
        taskScheduleService = taskScheduleServiceRef;
        pingTask = pingTaskRef;
        reconnectorTask = reconnectorTaskRef;
        logArchiveTask = logArchiveTaskRef;
        retryResourcesTask = retryResourcesTaskRef;
        debugConsoleCreator = debugConsoleCreatorRef;
        controllerNetComInitializer = controllerNetComInitializerRef;
        whitelistProps = whitelistPropsRef;
        ctrlCfg = ctrlCfgRef;
    }

    public void start(Injector injector, CtrlConfig linstorCfgRef)
        throws SystemServiceStartException, InitializationException
    {
        applicationLifecycleManager.installShutdownHook();

        reconfigurationLock.writeLock().lock();

        try
        {
            AccessContext initCtx = sysCtx.clone();
            initCtx.getEffectivePrivs().enablePrivileges(Privilege.PRIV_SYS_ALL);

            Level tmpLinLevel = null;
            try
            {
                tmpLinLevel = Level.valueOf(linstorCfgRef.getLinstorLogLevel().toUpperCase());
            }
            catch (IllegalArgumentException exc)
            {
                errorReporter.logError("Invalid Linstor Log level '" + linstorCfgRef.getLinstorLogLevel() + "'");
            }

            try
            {
                errorReporter.setLogLevel(
                    initCtx,
                    Level.valueOf(linstorCfgRef.getLogLevel().toUpperCase()),
                    tmpLinLevel
                );
            }
            catch (IllegalArgumentException exc)
            {
                errorReporter.logError("Invalid Log level '" + linstorCfgRef.getLogLevel() + "'");
            }

            taskScheduleService.addTask(pingTask);
            taskScheduleService.addTask(reconnectorTask);
            taskScheduleService.addTask(retryResourcesTask);
            taskScheduleService.addTask(logArchiveTask);

            systemServicesMap.put(controllerDb.getInstanceName(), controllerDb);
            systemServicesMap.put(taskScheduleService.getInstanceName(), taskScheduleService);
            systemServicesMap.put(timerEventSvc.getInstanceName(), timerEventSvc);

            dbInitializer.initialize(!linstorCfgRef.isDbVersionCheckDisabled());

            // Object protection loading has a hidden dependency on initializing the security objects
            // (via com.linbit.linstor.security.Role.GLOBAL_ROLE_MAP).
            // Hence the security objects should be initialized first.
            dbSecurityInitializer.initialize();

            dbCoreObjProtInitializer.initialize();
            dbDataInitializer.initialize();
            dbNumberPoolInitializer.initialize();

            initializeRestServer(injector);

            applicationLifecycleManager.startSystemServices(systemServicesMap.values());

            controllerNetComInitializer.initNetComServices(
                ctrlConf.getNamespace(PROPSCON_KEY_NETCOM).orElse(null),
                errorReporter,
                initCtx
            );

            whitelistProps.overrideDrbdProperties();

            connectToKnownNodes(errorReporter, initCtx);

            errorReporter.logInfo("Controller initialized");
        }
        catch (AccessDeniedException accessExc)
        {
            throw new ImplementationError(
                "The initialization security context does not have all privileges. " +
                "Initialization failed.",
                accessExc
            );
        }
        finally
        {
            reconfigurationLock.writeLock().unlock();
        }
    }

    private String restBindAddress(final String bindAddr, final int bindPort)
    {
        String restBindAddress;

        // Detect IPv6 addresses since they require a different bind address format
        if (bindAddr.indexOf(':') != -1)
        {
            restBindAddress = String.format("[%s]:%d", bindAddr, bindPort);
        }
        else
        {
            restBindAddress = String.format("%s:%d", bindAddr, bindPort);
        }

        return restBindAddress;
    }

    private void initializeRestServer(Injector injector)
    {
        boolean restEnabled = ctrlCfg.isRestEnabled();

        try
        {
            Path keyStorePath = null;
            String keyStorePassword;
            Path trustStorePath = null;
            String trustStorePassword;
            final String keyStorePathProp = ctrlCfg.getRestSecureKeystore();
            final String trustStorePathProp = ctrlCfg.getRestSecureTruststore();
            if (keyStorePathProp != null && ctrlCfg.isRestSecureEnabled())
            {
                keyStorePath = Paths.get(keyStorePathProp);
                if (!keyStorePath.isAbsolute())
                {
                    keyStorePath = ctrlCfg.getConfigPath().resolve(keyStorePath);
                }
            }
            if (trustStorePathProp != null)
            {
                trustStorePath = Paths.get(trustStorePathProp);
                if (!trustStorePath.isAbsolute())
                {
                    trustStorePath = ctrlCfg.getConfigPath().resolve(trustStorePath);
                }
            }

            keyStorePassword = ctrlCfg.getRestSecureKeystorePassword();
            trustStorePassword = ctrlCfg.getRestSecureTruststorePassword();

            if (restEnabled)
            {
                final GrizzlyHttpService grizzlyHttpService = new GrizzlyHttpService(
                    injector,
                    errorReporter,
                    systemServicesMap,
                    ctrlCfg.getRestBindAddressWithPort(),
                    ctrlCfg.getRestSecureBindAddressWithPort(),
                    keyStorePath,
                    keyStorePassword,
                    trustStorePath,
                    trustStorePassword,
                    ctrlCfg.getLogRestAccessLogPath(),
                    ctrlCfg.getLogRestAccessMode()
                );
                systemServicesMap.put(grizzlyHttpService.getInstanceName(), grizzlyHttpService);
            }
        }
        catch (Exception exc)
        {
            String reportId = errorReporter.reportError(Level.ERROR, exc);
            errorReporter.logError(
                "Initialization of the REST service failed, see error report %s for details",
                reportId
            );
        }
    }

    private void enterDebugConsole()
    {
        try
        {
            errorReporter.logInfo("Entering debug console");

            AccessContext privCtx = sysCtx.clone();
            AccessContext debugCtx = sysCtx.clone();
            privCtx.getEffectivePrivs().enablePrivileges(Privilege.PRIV_SYS_ALL);
            debugCtx.getEffectivePrivs().enablePrivileges(Privilege.PRIV_SYS_ALL);

            DebugConsole dbgConsole = debugConsoleCreator.createDebugConsole(privCtx, debugCtx, null);
            dbgConsole.stdStreamsConsole(DebugConsoleImpl.CONSOLE_PROMPT);
            System.out.println();

            errorReporter.logInfo("Debug console exited");
        }
        catch (Throwable error)
        {
            errorReporter.reportError(error);
        }

        try
        {
            AccessContext shutdownCtx = sysCtx.clone();
            // Just in case that someone removed the access control list entry
            // for the system's role or changed the security type for shutdown,
            // override access controls with the system context's privileges
            shutdownCtx.getEffectivePrivs().enablePrivileges(Privilege.PRIV_OBJ_USE, Privilege.PRIV_MAC_OVRD);
            applicationLifecycleManager.shutdown(shutdownCtx);
        }
        catch (AccessDeniedException accExc)
        {
            throw new ImplementationError(
                "Cannot shutdown() using the system's security context. " +
                "Suspected removal of privileges from the system context.",
                accExc
            );
        }
    }

    private void connectToKnownNodes(final ErrorReporter errorLogRef, final AccessContext initCtx)
    {
        if (!nodesMap.isEmpty())
        {
            errorLogRef.logInfo("Reconnecting to previously known nodes");
            Collection<Node> nodes = nodesMap.values();
            reconnectorTask.startReconnecting(nodes, initCtx);
            errorLogRef.logInfo("Reconnect requests sent");
        }
        else
        {
            errorLogRef.logInfo("No known nodes.");
        }
    }

    private static DatabaseDriverInfo.DatabaseType checkDatabaseConfig(
        ErrorReporter errorReporter,
        CtrlConfig linstorConfig
    )
    {
        DatabaseDriverInfo.DatabaseType dbType;
        String dbConnectionUrl = linstorConfig.getDbConnectionUrl();
        if (dbConnectionUrl.startsWith("jdbc"))
        {
            dbType = DatabaseDriverInfo.DatabaseType.SQL;
        }
        else if (dbConnectionUrl.startsWith("etcd"))
        {
            dbType = DatabaseDriverInfo.DatabaseType.ETCD;
        }
        else
        {
            errorReporter.logError("Database uri not supported: " + dbConnectionUrl);
            System.exit(InternalApiConsts.EXIT_CODE_CONFIG_PARSE_ERROR);
            throw new RuntimeException("Can't touch this");
        }

        return dbType;
    }

    public static void main(String[] args)
    {
        CtrlConfig cfg = new CtrlConfig(args);

        System.setProperty("log.module", LinStor.CONTROLLER_MODULE);
        System.setProperty("log.directory", cfg.getLogDirectory());

        System.out.printf("%s, Module %s\n", LinStor.PROGRAM, LinStor.CONTROLLER_MODULE);
        LinStor.printStartupInfo();

        StdErrorReporter errorLog = new StdErrorReporter(
            LinStor.CONTROLLER_MODULE,
            Paths.get(cfg.getLogDirectory()),
            cfg.isLogPrintStackTrace(),
            LinStor.getHostName(),
            cfg.getLogLevel(),
            cfg.getLinstorLogLevel(),
            () -> null
        );

        // check database type
        DatabaseDriverInfo.DatabaseType dbType = checkDatabaseConfig(errorLog, cfg);

        boolean dbgCnsEnabled = false;
        Controller instance = null;
        try
        {
            Thread.currentThread().setName("Main");

            dbgCnsEnabled = cfg.isDebugConsoleEnabled();

            errorLog.logInfo("Loading API classes started.");
            long startAPIClassLoadingTime = System.currentTimeMillis();
            ApiType apiType = new ProtobufApiType();
            ClassPathLoader classPathLoader = new ClassPathLoader(errorLog);
            List<String> packageSuffixes = Arrays.asList("common", "controller", "internal");

            List<Class<? extends BaseApiCall>> apiCalls = classPathLoader.loadClasses(
                ProtobufApiType.class.getPackage().getName(),
                packageSuffixes,
                BaseApiCall.class,
                ProtobufApiCall.class
            );

            /*
             * Dynamic loading is very slow compared to static loading, each .loadClasses
             * costs around ~400ms on my system. so we do it static now, there are only 4 event classes anyway
             * 
             * List<Class<? extends EventSerializer>> eventSerializers = classPathLoader.loadClasses(
             *      ProtobufEventSerializer.class.getPackage().getName(),
             *      packageSuffixes,
             *      EventSerializer.class,
             *      ProtobufEventSerializer.class
             * );
             * 
             * List<Class<? extends EventHandler>> eventHandlers = classPathLoader.loadClasses(
             *      ProtobufEventHandler.class.getPackage().getName(),
             *      packageSuffixes,
             *      EventHandler.class,
             *      ProtobufEventHandler.class
             * );
             */

            List<Class<? extends EventSerializer>> eventSerializers = Arrays.asList(
                ResourceStateEventSerializer.class,
                VolumeDiskStateEventSerializer.class
            );

            List<Class<? extends EventHandler>> eventHandlers = Arrays.asList(
                ResourceStateEventHandler.class,
                VolumeDiskStateEventHandler.class
            );
            errorLog.logInfo(
                String.format(
                    "API classes loading finished: %dms",
                    (System.currentTimeMillis() - startAPIClassLoadingTime)
                )
            );

            errorLog.logInfo("Dependency injection started.");
            long startDepInjectionTime = System.currentTimeMillis();
            Injector injector = Guice.createInjector(
                new GuiceConfigModule(),
                new LoggingModule(errorLog),
                new SecurityModule(),
                new ControllerSecurityModule(),
                new CtrlConfigModule(cfg),
                new CoreTimerModule(),
                new MetaDataModule(),
                new ControllerLinstorModule(),
                new LinStorModule(),
                new CoreModule(),
                new ControllerCoreModule(),
                new ControllerSatelliteCommunicationModule(),
                new ControllerDbModule(dbType),
                new NetComModule(),
                new NumberPoolModule(),
                new ApiModule(apiType, apiCalls),
                new ApiCallHandlerModule(),
                new CtrlApiCallHandlerModule(),
                new EventModule(eventSerializers, eventHandlers),
                new DebugModule(),
                new ControllerDebugModule(),
                new ControllerTransactionMgrModule(dbType)
            );
            errorLog.logInfo(
                String.format(
                    "Dependency injection finished: %dms",
                    (System.currentTimeMillis() - startDepInjectionTime)
                )
            );

            instance = injector.getInstance(Controller.class);
            instance.start(injector, cfg);

            if (dbgCnsEnabled)
            {
                instance.enterDebugConsole();
            }
        }
        catch (Throwable error)
        {
            errorLog.reportError(error);
            // If enabled, attempt to enter the debug console
            if (dbgCnsEnabled)
            {
                if (instance != null)
                {
                    try
                    {
                        instance.enterDebugConsole();
                    }
                    catch (Throwable dbgError)
                    {
                        errorLog.reportError(dbgError);
                    }
                }
            }
            System.exit(InternalApiConsts.EXIT_CODE_IMPL_ERROR);
        }

        System.out.println();
    }
}

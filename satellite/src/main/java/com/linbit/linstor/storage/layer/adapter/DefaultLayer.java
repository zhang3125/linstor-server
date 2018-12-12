package com.linbit.linstor.storage.layer.adapter;

import com.linbit.ImplementationError;
import com.linbit.linstor.Resource;
import com.linbit.linstor.Snapshot;
import com.linbit.linstor.Volume;
import com.linbit.linstor.Resource.RscFlags;
import com.linbit.linstor.Volume.VlmFlags;
import com.linbit.linstor.annotation.DeviceManagerContext;
import com.linbit.linstor.api.ApiCallRcImpl;
import com.linbit.linstor.api.ApiConsts;
import com.linbit.linstor.core.devmgr.DeviceHandler2;
import com.linbit.linstor.propscon.Props;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.storage.StorageException;
import com.linbit.linstor.storage.layer.DeviceLayer.NotificationListener;
import com.linbit.linstor.storage.layer.ResourceLayer;
import com.linbit.linstor.storage.layer.exceptions.ResourceException;
import com.linbit.linstor.storage.layer.exceptions.VolumeException;
import com.linbit.linstor.storage.utils.ResourceUtils;
import com.linbit.linstor.storage.utils.VolumeUtils;
import com.linbit.utils.RemoveAfterDevMgrRework;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RemoveAfterDevMgrRework
@Singleton
public class DefaultLayer implements ResourceLayer
{
    private final AccessContext sysCtx;
    private final Provider<NotificationListener> notificationListener;
    private final Provider<DeviceHandler2> resourceProcessor;

    private Props localNodeProps;

    @Inject
    public DefaultLayer(
        @DeviceManagerContext AccessContext sysCtxRef,
        Provider<NotificationListener> notificationListenerProviderRef,
        Provider<DeviceHandler2> resourceProcessorProviderRef
    )
    {
        sysCtx = sysCtxRef;
        notificationListener = notificationListenerProviderRef;
        resourceProcessor = resourceProcessorProviderRef;
    }

    @Override
    public String getName()
    {
        return this.getClass().getSimpleName();
    }

    @Override
    public void prepare(List<Resource> value, List<Snapshot> snapshots)
        throws StorageException, AccessDeniedException, SQLException
    {
        // no-op
    }

    @Override
    public void clearCache() throws StorageException
    {
        // no-op
    }

    @Override
    public void process(Resource rsc, Collection<Snapshot> snapshots, ApiCallRcImpl apiCallRc)
        throws StorageException, ResourceException, VolumeException, AccessDeniedException, SQLException
    {
        resourceProcessor.get().process(ResourceUtils.getSingleChild(rsc, sysCtx), snapshots, apiCallRc);

        // delete the resource / volume if all went well (that means, no exception from the previous .process call)

        for (Volume vlm : rsc.streamVolumes().collect(Collectors.toList()))
        {
            if (vlm.getFlags().isSet(sysCtx, VlmFlags.DELETE))
            {
                /*
                 * TODO: call the following method once the API is split into
                 * notifyVolumeDeleted(volume); // deletion of typed volume / no change in FreeSpace
                 * notifyStorageVolumeDeleted(volume, newFreeSpace);
                 */
            }
        }
        if (rsc.getStateFlags().isSet(sysCtx, RscFlags.DELETE))
        {
            apiCallRc.addEntry(
                ApiCallRcImpl.simpleEntry(
                    ApiConsts.DELETED | ApiConsts.MASK_RSC,
                    "Resource '" + rsc.getDefinition().getName() + "' [DEFAULT] deleted"
                )
            );

            notificationListener.get().notifyResourceDeleted(rsc);
        }
        else
        {
            apiCallRc.addEntry(
                ApiCallRcImpl.simpleEntry(
                    ApiConsts.DELETED | ApiConsts.MASK_RSC,
                    "Resource '" + rsc.getDefinition().getName() + "' [DEFAULT] applied"
                )
            );
            notificationListener.get().notifyResourceApplied(rsc);
        }

    }

    public Map<Resource, StorageException> adjustBottomUp(
        Collection<Resource> resources,
        Collection<Snapshot> snapshots
    )
        throws StorageException
    {
        Map<Resource, StorageException> exceptions = new HashMap<>();
        try
        {
            for (Resource rsc : resources)
            {
                if (rsc.getStateFlags().isSet(sysCtx, RscFlags.DELETE))
                {
                    if (!rsc.getChildResources(sysCtx).isEmpty())
                    {
                        exceptions.put(
                            rsc,
                            new StorageException(
                                "Resource marked for deletion could not be deleted as it still has children"
                            )
                        );
                    }
                    else
                    {
                        notificationListener.get().notifyResourceDeleted(rsc);
                    }
                }
                else
                {
                    Iterator<Volume> vlmIt = rsc.iterateVolumes();
                    while (vlmIt.hasNext())
                    {
                        Volume vlm = vlmIt.next();
                        if (vlm.getFlags().isSet(sysCtx, VlmFlags.DELETE))
                        {
                            if (VolumeUtils.getBackingVolume(sysCtx, vlm) != null)
                            {
                                exceptions.put(
                                    rsc,
                                    new StorageException(
                                        "Resource marked for deletion could not be deleted as it still has children"
                                    )
                                );
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (AccessDeniedException exc)
        {
            throw new ImplementationError(exc);
        }
        return exceptions;
    }

    @Override
    public void setLocalNodeProps(Props localNodePropsRef)
    {
        localNodeProps = localNodePropsRef;
    }
}
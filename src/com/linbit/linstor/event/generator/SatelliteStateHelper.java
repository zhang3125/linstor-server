package com.linbit.linstor.event.generator;

import com.linbit.linstor.Node;
import com.linbit.linstor.NodeName;
import com.linbit.linstor.annotation.ApiContext;
import com.linbit.linstor.core.CoreModule;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.satellitestate.SatelliteState;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.Function;

@Singleton
public class SatelliteStateHelper
{
    private final AccessContext accCtx;
    private final CoreModule.NodesMap nodesMap;
    private final ReadWriteLock nodesMapLock;

    @Inject
    public SatelliteStateHelper(
        @ApiContext AccessContext accCtxRef,
        CoreModule.NodesMap nodesMapRef,
        @Named(CoreModule.NODES_MAP_LOCK) ReadWriteLock nodesMapLockRef
    )
    {
        accCtx = accCtxRef;
        nodesMap = nodesMapRef;
        nodesMapLock = nodesMapLockRef;
    }

    public <T> T withSatelliteState(NodeName nodeName, Function<SatelliteState, T> extractor, T defaultIfNoPeer)
        throws AccessDeniedException
    {
        T value = defaultIfNoPeer;

        nodesMapLock.readLock().lock();
        try
        {
            Node node = nodesMap.get(nodeName);

            if (node != null)
            {
                Peer peer = node.getPeer(accCtx);

                if (peer != null)
                {
                    Lock readLock = peer.getSatelliteStateLock().readLock();
                    readLock.lock();
                    try
                    {
                        value = extractor.apply(peer.getSatelliteState());
                    }
                    finally
                    {
                        readLock.unlock();
                    }
                }
            }
        }
        finally
        {
            nodesMapLock.readLock().unlock();
        }

        return value;
    }
}
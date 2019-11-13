package com.linbit.linstor.utils.layer;

import com.linbit.linstor.core.objects.Resource;
import com.linbit.linstor.core.objects.Resource.Flags;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.AccessDeniedException;
import com.linbit.linstor.stateflags.StateFlags;
import com.linbit.linstor.storage.data.adapter.drbd.DrbdRscData;
import com.linbit.linstor.storage.interfaces.categories.resource.RscLayerObject;
import com.linbit.linstor.storage.kinds.DeviceLayerKind;

import java.util.Set;

public class DrbdLayerUtils
{
    public static boolean isAnyDrbdResourceExpected(AccessContext accCtx, Resource rscRef)
        throws AccessDeniedException
    {
        boolean ret = false;
        Set<RscLayerObject> drbdRscSet = LayerRscUtils
            .getRscDataByProvider(rscRef.getLayerData(accCtx), DeviceLayerKind.DRBD);
        for (RscLayerObject drbdRsc : drbdRscSet)
        {
            if (isDrbdResourceExpected(accCtx, (DrbdRscData) drbdRsc))
            {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public static boolean isDrbdResourceExpected(AccessContext accCtx, DrbdRscData rscData)
        throws AccessDeniedException
    {
        boolean isDevExpected = true;

        StateFlags<Flags> rscFlags = rscData.getResource().getStateFlags();
        if (rscFlags.isSet(accCtx, Resource.Flags.DRBD_DISKLESS))
        {
            isDevExpected = true;
        }
        else
        {
            if (rscFlags.isUnset(accCtx, Resource.Flags.NVME_INITIATOR))
            {
                // target NVME will never return a device, so drbd will never exist
                isDevExpected = false;
            }
        }

        return isDevExpected;
    }

    public static boolean isDrbdDevicePresent(DrbdRscData rscData)
    {
        return rscData.streamVlmLayerObjects().allMatch(
            vlmData -> vlmData.exists() && vlmData.getDevicePath() != null
        );
    }


}

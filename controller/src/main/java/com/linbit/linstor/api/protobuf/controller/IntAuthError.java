package com.linbit.linstor.api.protobuf.controller;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.linbit.linstor.InternalApiConsts;
import com.linbit.linstor.api.ApiCall;
import com.linbit.linstor.api.protobuf.ProtobufApiCall;
import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.netcom.Peer;
import com.linbit.linstor.proto.MsgApiCallResponseOuterClass.MsgApiCallResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author rpeinthor
 */
@ProtobufApiCall(
    name = InternalApiConsts.API_AUTH_ERROR,
    description = "Called by the satellite to indicate that controller authentication failed",
    requiresAuth = false,
    transactional = false
)
@Singleton
public class IntAuthError implements ApiCall
{
    private final Provider<Peer> clientProvider;
    private final ErrorReporter errorReporter;

    @Inject
    public IntAuthError(Provider<Peer> clientProviderRef, ErrorReporter errorReporterRef)
    {
        clientProvider = clientProviderRef;
        errorReporter = errorReporterRef;
    }

    @Override
    public void execute(InputStream msgDataIn)
        throws IOException
    {
        Peer client = clientProvider.get();
        client.setAuthenticated(false);
        client.setConnectionStatus(Peer.ConnectionStatus.AUTHENTICATION_ERROR);
        MsgApiCallResponse msgApiCallRc = MsgApiCallResponse.parseDelimitedFrom(msgDataIn);

        errorReporter.logError("Satellite authentication error: " + msgApiCallRc.getCause());
    }

}

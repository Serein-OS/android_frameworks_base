/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 R* limitations under the License.
 */

package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.Logging.Session;

import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;

import java.util.List;

/**
 * A component that provides an RPC servant implementation of {@link IConnectionServiceAdapter},
 * posting incoming messages on the main thread on a client-supplied delegate object.
 *
 * TODO: Generate this and similar classes using a compiler starting from AIDL interfaces.
 *
 * @hide
 */
final class ConnectionServiceAdapterServant {
    private static final int MSG_HANDLE_CREATE_CONNECTION_COMPLETE = 1;
    private static final int MSG_SET_ACTIVE = 2;
    private static final int MSG_SET_RINGING = 3;
    private static final int MSG_SET_DIALING = 4;
    private static final int MSG_SET_DISCONNECTED = 5;
    private static final int MSG_SET_ON_HOLD = 6;
    private static final int MSG_SET_RINGBACK_REQUESTED = 7;
    private static final int MSG_SET_CONNECTION_CAPABILITIES = 8;
    private static final int MSG_SET_IS_CONFERENCED = 9;
    private static final int MSG_ADD_CONFERENCE_CALL = 10;
    private static final int MSG_REMOVE_CALL = 11;
    private static final int MSG_ON_POST_DIAL_WAIT = 12;
    private static final int MSG_QUERY_REMOTE_CALL_SERVICES = 13;
    private static final int MSG_SET_VIDEO_STATE = 14;
    private static final int MSG_SET_VIDEO_CALL_PROVIDER = 15;
    private static final int MSG_SET_IS_VOIP_AUDIO_MODE = 16;
    private static final int MSG_SET_STATUS_HINTS = 17;
    private static final int MSG_SET_ADDRESS = 18;
    private static final int MSG_SET_CALLER_DISPLAY_NAME = 19;
    private static final int MSG_SET_CONFERENCEABLE_CONNECTIONS = 20;
    private static final int MSG_ADD_EXISTING_CONNECTION = 21;
    private static final int MSG_ON_POST_DIAL_CHAR = 22;
    private static final int MSG_SET_CONFERENCE_MERGE_FAILED = 23;
    private static final int MSG_PUT_EXTRAS = 24;
    private static final int MSG_REMOVE_EXTRAS = 25;
    private static final int MSG_ON_CONNECTION_EVENT = 26;
    private static final int MSG_SET_CONNECTION_PROPERTIES = 27;
    private static final int MSG_SET_PULLING = 28;
    private static final int MSG_SET_AUDIO_ROUTE = 29;
    private static final int MSG_ON_RTT_INITIATION_SUCCESS = 30;
    private static final int MSG_ON_RTT_INITIATION_FAILURE = 31;
    private static final int MSG_ON_RTT_REMOTELY_TERMINATED = 32;
    private static final int MSG_ON_RTT_UPGRADE_REQUEST = 33;
    private static final int MSG_SET_PHONE_ACCOUNT_CHANGED = 34;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_RELEASED = 35;

    private final IConnectionServiceAdapter mDelegate;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                internalHandleMessage(msg);
            } catch (RemoteException e) {
            }
        }

        // Internal method defined to centralize handling of RemoteException
        private void internalHandleMessage(Message msg) throws RemoteException {
            switch (msg.what) {
                case MSG_HANDLE_CREATE_CONNECTION_COMPLETE: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.handleCreateConnectionComplete(
                                (String) args.arg1,
                                (ConnectionRequest) args.arg2,
                                (ParcelableConnection) args.arg3,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_ACTIVE:
                    mDelegate.setActive((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_RINGING:
                    mDelegate.setRinging((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_DIALING:
                    mDelegate.setDialing((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_PULLING:
                    mDelegate.setPulling((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_DISCONNECTED: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setDisconnected((String) args.arg1, (DisconnectCause) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_ON_HOLD:
                    mDelegate.setOnHold((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_RINGBACK_REQUESTED:
                    mDelegate.setRingbackRequested((String) msg.obj, msg.arg1 == 1,
                            null /*Session.Info*/);
                    break;
                case MSG_SET_CONNECTION_CAPABILITIES:
                    mDelegate.setConnectionCapabilities((String) msg.obj, msg.arg1,
                            null /*Session.Info*/);
                    break;
                case MSG_SET_CONNECTION_PROPERTIES:
                    mDelegate.setConnectionProperties((String) msg.obj, msg.arg1,
                            null /*Session.Info*/);
                    break;
                case MSG_SET_IS_CONFERENCED: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setIsConferenced((String) args.arg1, (String) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_ADD_CONFERENCE_CALL: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.addConferenceCall(
                                (String) args.arg1, (ParcelableConference) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_REMOVE_CALL:
                    mDelegate.removeCall((String) msg.obj,
                            null /*Session.Info*/);
                    break;
                case MSG_ON_POST_DIAL_WAIT: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.onPostDialWait((String) args.arg1, (String) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_ON_POST_DIAL_CHAR: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.onPostDialChar((String) args.arg1, (char) args.argi1,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_QUERY_REMOTE_CALL_SERVICES:
                    mDelegate.queryRemoteConnectionServices((RemoteServiceCallback) msg.obj,
                            null /*Session.Info*/);
                    break;
                case MSG_SET_VIDEO_STATE:
                    mDelegate.setVideoState((String) msg.obj, msg.arg1, null /*Session.Info*/);
                    break;
                case MSG_SET_VIDEO_CALL_PROVIDER: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setVideoProvider((String) args.arg1,
                                (IVideoProvider) args.arg2, null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_IS_VOIP_AUDIO_MODE:
                    mDelegate.setIsVoipAudioMode((String) msg.obj, msg.arg1 == 1,
                            null /*Session.Info*/);
                    break;
                case MSG_SET_STATUS_HINTS: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setStatusHints((String) args.arg1, (StatusHints) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_ADDRESS: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setAddress((String) args.arg1, (Uri) args.arg2, args.argi1,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_CALLER_DISPLAY_NAME: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setCallerDisplayName(
                                (String) args.arg1, (String) args.arg2, args.argi1,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_CONFERENCEABLE_CONNECTIONS: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setConferenceableConnections((String) args.arg1,
                                (List<String>) args.arg2, null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_ADD_EXISTING_CONNECTION: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.addExistingConnection((String) args.arg1,
                                (ParcelableConnection) args.arg2, null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_CONFERENCE_MERGE_FAILED: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setConferenceMergeFailed((String) args.arg1,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_PUT_EXTRAS: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.putExtras((String) args.arg1, (Bundle) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_REMOVE_EXTRAS: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.removeExtras((String) args.arg1, (List<String>) args.arg2,
                                null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_ON_CONNECTION_EVENT: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.onConnectionEvent((String) args.arg1, (String) args.arg2,
                                (Bundle) args.arg3, null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_SET_AUDIO_ROUTE: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.setAudioRoute((String) args.arg1, args.argi1, (String) args.arg2,
                                (Session.Info) args.arg3);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_ON_RTT_INITIATION_SUCCESS:
                    mDelegate.onRttInitiationSuccess((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_ON_RTT_INITIATION_FAILURE:
                    mDelegate.onRttInitiationFailure((String) msg.obj, msg.arg1,
                            null /*Session.Info*/);
                    break;
                case MSG_ON_RTT_REMOTELY_TERMINATED:
                    mDelegate.onRttSessionRemotelyTerminated((String) msg.obj,
                            null /*Session.Info*/);
                    break;
                case MSG_ON_RTT_UPGRADE_REQUEST:
                    mDelegate.onRemoteRttRequest((String) msg.obj, null /*Session.Info*/);
                    break;
                case MSG_SET_PHONE_ACCOUNT_CHANGED: {
                    SomeArgs args = (SomeArgs) msg.obj;
                    try {
                        mDelegate.onPhoneAccountChanged((String) args.arg1,
                                (PhoneAccountHandle) args.arg2, null /*Session.Info*/);
                    } finally {
                        args.recycle();
                    }
                    break;
                }
                case MSG_CONNECTION_SERVICE_FOCUS_RELEASED:
                    mDelegate.onConnectionServiceFocusReleased(null /*Session.Info*/);
                    break;
            }
        }
    };

    private final IConnectionServiceAdapter mStub = new IConnectionServiceAdapter.Stub() {
        @Override
        public void handleCreateConnectionComplete(
                String id,
                ConnectionRequest request,
                ParcelableConnection connection,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = id;
            args.arg2 = request;
            args.arg3 = connection;
            mHandler.obtainMessage(MSG_HANDLE_CREATE_CONNECTION_COMPLETE, args).sendToTarget();
        }

        @Override
        public void setActive(String connectionId, Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_ACTIVE, connectionId).sendToTarget();
        }

        @Override
        public void setRinging(String connectionId, Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_RINGING, connectionId).sendToTarget();
        }

        @Override
        public void setDialing(String connectionId, Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_DIALING, connectionId).sendToTarget();
        }

        @Override
        public void setPulling(String connectionId, Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_PULLING, connectionId).sendToTarget();
        }

        @Override
        public void setDisconnected(String connectionId, DisconnectCause disconnectCause,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = disconnectCause;
            mHandler.obtainMessage(MSG_SET_DISCONNECTED, args).sendToTarget();
        }

        @Override
        public void setOnHold(String connectionId, Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_ON_HOLD, connectionId).sendToTarget();
        }

        @Override
        public void setRingbackRequested(String connectionId, boolean ringback,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_RINGBACK_REQUESTED, ringback ? 1 : 0, 0, connectionId)
                    .sendToTarget();
        }

        @Override
        public void setConnectionCapabilities(String connectionId, int connectionCapabilities,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(
                    MSG_SET_CONNECTION_CAPABILITIES, connectionCapabilities, 0, connectionId)
                    .sendToTarget();
        }

        @Override
        public void setConnectionProperties(String connectionId, int connectionProperties,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(
                    MSG_SET_CONNECTION_PROPERTIES, connectionProperties, 0, connectionId)
                    .sendToTarget();
        }

        @Override
        public void setConferenceMergeFailed(String callId, Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = callId;
            mHandler.obtainMessage(MSG_SET_CONFERENCE_MERGE_FAILED, args).sendToTarget();
        }

        @Override
        public void setIsConferenced(String callId, String conferenceCallId,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = callId;
            args.arg2 = conferenceCallId;
            mHandler.obtainMessage(MSG_SET_IS_CONFERENCED, args).sendToTarget();
        }

        @Override
        public void addConferenceCall(String callId, ParcelableConference parcelableConference,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = callId;
            args.arg2 = parcelableConference;
            mHandler.obtainMessage(MSG_ADD_CONFERENCE_CALL, args).sendToTarget();
        }

        @Override
        public void removeCall(String connectionId,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_REMOVE_CALL, connectionId).sendToTarget();
        }

        @Override
        public void onPostDialWait(String connectionId, String remainingDigits,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = remainingDigits;
            mHandler.obtainMessage(MSG_ON_POST_DIAL_WAIT, args).sendToTarget();
        }

        @Override
        public void onPostDialChar(String connectionId, char nextChar,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.argi1 = nextChar;
            mHandler.obtainMessage(MSG_ON_POST_DIAL_CHAR, args).sendToTarget();
        }

        @Override
        public void queryRemoteConnectionServices(RemoteServiceCallback callback,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_QUERY_REMOTE_CALL_SERVICES, callback).sendToTarget();
        }

        @Override
        public void setVideoState(String connectionId, int videoState,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_VIDEO_STATE, videoState, 0, connectionId).sendToTarget();
        }

        @Override
        public void setVideoProvider(String connectionId, IVideoProvider videoProvider,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = videoProvider;
            mHandler.obtainMessage(MSG_SET_VIDEO_CALL_PROVIDER, args).sendToTarget();
        }

        @Override
        public final void setIsVoipAudioMode(String connectionId, boolean isVoip,
                Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_SET_IS_VOIP_AUDIO_MODE, isVoip ? 1 : 0, 0,
                    connectionId).sendToTarget();
        }

        @Override
        public final void setStatusHints(String connectionId, StatusHints statusHints,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = statusHints;
            mHandler.obtainMessage(MSG_SET_STATUS_HINTS, args).sendToTarget();
        }

        @Override
        public final void setAddress(String connectionId, Uri address, int presentation,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = address;
            args.argi1 = presentation;
            mHandler.obtainMessage(MSG_SET_ADDRESS, args).sendToTarget();
        }

        @Override
        public final void setCallerDisplayName(
                String connectionId, String callerDisplayName, int presentation,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = callerDisplayName;
            args.argi1 = presentation;
            mHandler.obtainMessage(MSG_SET_CALLER_DISPLAY_NAME, args).sendToTarget();
        }

        @Override
        public final void setConferenceableConnections(String connectionId,
                List<String> conferenceableConnectionIds, Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = conferenceableConnectionIds;
            mHandler.obtainMessage(MSG_SET_CONFERENCEABLE_CONNECTIONS, args).sendToTarget();
        }

        @Override
        public final void addExistingConnection(String connectionId,
                ParcelableConnection connection, Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = connection;
            mHandler.obtainMessage(MSG_ADD_EXISTING_CONNECTION, args).sendToTarget();
        }

        @Override
        public final void putExtras(String connectionId, Bundle extras, Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = extras;
            mHandler.obtainMessage(MSG_PUT_EXTRAS, args).sendToTarget();
        }

        @Override
        public final void removeExtras(String connectionId, List<String> keys,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = keys;
            mHandler.obtainMessage(MSG_REMOVE_EXTRAS, args).sendToTarget();
        }

        @Override
        public final void setAudioRoute(String connectionId, int audioRoute,
                String bluetoothAddress, Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.argi1 = audioRoute;
            args.arg2 = bluetoothAddress;
            args.arg3 = sessionInfo;
            mHandler.obtainMessage(MSG_SET_AUDIO_ROUTE, args).sendToTarget();
        }

        @Override
        public final void onConnectionEvent(String connectionId, String event, Bundle extras,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = connectionId;
            args.arg2 = event;
            args.arg3 = extras;
            mHandler.obtainMessage(MSG_ON_CONNECTION_EVENT, args).sendToTarget();
        }

        @Override
        public void onRttInitiationSuccess(String connectionId, Session.Info sessionInfo)
                throws RemoteException {
            mHandler.obtainMessage(MSG_ON_RTT_INITIATION_SUCCESS, connectionId).sendToTarget();
        }

        @Override
        public void onRttInitiationFailure(String connectionId, int reason,
                Session.Info sessionInfo)
                throws RemoteException {
            mHandler.obtainMessage(MSG_ON_RTT_INITIATION_FAILURE, reason, 0, connectionId)
                    .sendToTarget();
        }

        @Override
        public void onRttSessionRemotelyTerminated(String connectionId, Session.Info sessionInfo)
                throws RemoteException {
            mHandler.obtainMessage(MSG_ON_RTT_REMOTELY_TERMINATED, connectionId).sendToTarget();
        }

        @Override
        public void onRemoteRttRequest(String connectionId, Session.Info sessionInfo)
                throws RemoteException {
            mHandler.obtainMessage(MSG_ON_RTT_UPGRADE_REQUEST, connectionId).sendToTarget();
        }

        @Override
        public void onPhoneAccountChanged(String callId, PhoneAccountHandle pHandle,
                Session.Info sessionInfo) {
            SomeArgs args = SomeArgs.obtain();
            args.arg1 = callId;
            args.arg2 = pHandle;
            mHandler.obtainMessage(MSG_SET_PHONE_ACCOUNT_CHANGED, args).sendToTarget();
        }

        @Override
        public void onConnectionServiceFocusReleased(Session.Info sessionInfo) {
            mHandler.obtainMessage(MSG_CONNECTION_SERVICE_FOCUS_RELEASED).sendToTarget();
        }

     //   @Override
      //  public void resetCdmaConnectionTime(String callId, Session.Info sessionInfo) {
            // Do nothing
      //  }
    };

    public ConnectionServiceAdapterServant(IConnectionServiceAdapter delegate) {
        mDelegate = delegate;
    }

    public IConnectionServiceAdapter getStub() {
        return mStub;
    }
}

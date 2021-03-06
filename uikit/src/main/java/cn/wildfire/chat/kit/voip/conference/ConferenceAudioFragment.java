/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.voip.conference;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.ViewModelProviders;

import org.webrtc.StatsReport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfire.chat.kit.GlideApp;
import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.R2;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfirechat.avenginekit.AVEngineKit;
import cn.wildfirechat.avenginekit.PeerConnectionClient;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.remote.ChatManager;

public class ConferenceAudioFragment extends Fragment implements AVEngineKit.CallSessionCallback {
    @BindView(R2.id.durationTextView)
    TextView durationTextView;
    @BindView(R2.id.audioContainerGridLayout)
    GridLayout audioContainerGridLayout;
    @BindView(R2.id.speakerImageView)
    ImageView speakerImageView;
    @BindView(R2.id.muteImageView)
    ImageView muteImageView;

    private List<String> participants;
    private UserInfo me;
    private UserViewModel userViewModel;
    private boolean isSpeakerOn;
    private boolean audioEnable = false;

    public static final String TAG = "ConferenceVideoFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.av_multi_audio_outgoing_connected, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session == null) {
            getActivity().finish();
            return;
        }

        audioEnable = session.isEnableAudio();
        muteImageView.setSelected(!audioEnable);

        initParticipantsView(session);
        updateParticipantStatus(session);
        updateCallDuration();

        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        isSpeakerOn = audioManager.getMode() == AudioManager.MODE_NORMAL;
        speakerImageView.setSelected(isSpeakerOn);
    }

    private void initParticipantsView(AVEngineKit.CallSession session) {

        me = userViewModel.getUserInfo(userViewModel.getUserId(), false);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int with = dm.widthPixels;

        audioContainerGridLayout.getLayoutParams().height = with;
        audioContainerGridLayout.removeAllViews();

        // session?????????participants??????????????????????????????
        participants = session.getParticipantIds();
        if (participants == null || participants.isEmpty()) {
            return;
        }
        List<UserInfo> participantUserInfos = userViewModel.getUserInfos(participants);
        participantUserInfos.add(me);
        int size = with / Math.max((int) Math.ceil(Math.sqrt(participantUserInfos.size())), 3);
        for (UserInfo userInfo : participantUserInfos) {
            ConferenceItem multiCallItem = new ConferenceItem(getActivity());
            multiCallItem.setTag(userInfo.uid);

            multiCallItem.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            multiCallItem.getStatusTextView().setText(R.string.connecting);
            GlideApp.with(multiCallItem).load(userInfo.portrait).placeholder(R.mipmap.avatar_def).into(multiCallItem.getPortraitImageView());
            audioContainerGridLayout.addView(multiCallItem);
        }
    }

    private void updateParticipantStatus(AVEngineKit.CallSession session) {
        int count = audioContainerGridLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = audioContainerGridLayout.getChildAt(i);
            String userId = (String) view.getTag();
            if (me.uid.equals(userId)) {
                ((ConferenceItem) view).getStatusTextView().setVisibility(View.GONE);
            } else {
                PeerConnectionClient client = session.getClient(userId);
                if (client.state == AVEngineKit.CallState.Connected) {
                    ((ConferenceItem) view).getStatusTextView().setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick(R2.id.minimizeImageView)
    void minimize() {
        ((ConferenceActivity) getActivity()).showFloatingView(null);
    }

    @OnClick(R2.id.addParticipantImageView)
    void addParticipant() {
        ((ConferenceActivity) getActivity()).addParticipant(AVEngineKit.MAX_AUDIO_PARTICIPANT_COUNT - participants.size() - 1);
    }

    @OnClick(R2.id.muteImageView)
    void mute() {
        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session != null && session.getState() == AVEngineKit.CallState.Connected) {
            session.muteAudio(audioEnable);
            audioEnable = !audioEnable;
            muteImageView.setSelected(!audioEnable);
        }
    }

    @OnClick(R2.id.speakerImageView)
    void speaker() {
        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session != null && session.getState() == AVEngineKit.CallState.Connected) {
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            isSpeakerOn = !isSpeakerOn;
            audioManager.setMode(isSpeakerOn ? AudioManager.MODE_NORMAL : AudioManager.MODE_IN_COMMUNICATION);
            speakerImageView.setSelected(isSpeakerOn);
            audioManager.setSpeakerphoneOn(isSpeakerOn);
        }
    }

    @OnClick(R2.id.hangupImageView)
    void hangup() {
        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session != null) {
            session.endCall();
        }
    }

    // hangup ??????
    @Override
    public void didCallEndWithReason(AVEngineKit.CallEndReason callEndReason) {
        // do nothing
    }

    @Override
    public void didChangeState(AVEngineKit.CallState callState) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        AVEngineKit.CallSession callSession = AVEngineKit.Instance().getCurrentSession();
        if (callState == AVEngineKit.CallState.Connected) {
            updateParticipantStatus(callSession);
        } else if (callState == AVEngineKit.CallState.Idle) {
            getActivity().finish();
        }
    }

    @Override
    public void didParticipantJoined(String userId) {
        if (participants.contains(userId)) {
            return;
        }
        int count = audioContainerGridLayout.getChildCount();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int with = dm.widthPixels;
        for (int i = 0; i < count; i++) {
            View view = audioContainerGridLayout.getChildAt(i);
            // ?????????????????????
            if (me.uid.equals(view.getTag())) {

                UserInfo info = userViewModel.getUserInfo(userId, false);
                ConferenceItem multiCallItem = new ConferenceItem(getActivity());
                multiCallItem.setTag(info.uid);

                multiCallItem.setLayoutParams(new ViewGroup.LayoutParams(with / 3, with / 3));

                multiCallItem.getStatusTextView().setText(R.string.connecting);
                GlideApp.with(multiCallItem).load(info.portrait).placeholder(R.mipmap.avatar_def).into(multiCallItem.getPortraitImageView());
                audioContainerGridLayout.addView(multiCallItem, i);
                break;
            }
        }
        participants.add(userId);
    }

    private ConferenceItem getUserConferenceItem(String userId) {
        return audioContainerGridLayout.findViewWithTag(userId);
    }

    @Override
    public void didParticipantConnected(String userId) {

    }

    @Override
    public void didParticipantLeft(String userId, AVEngineKit.CallEndReason callEndReason) {
        View view = audioContainerGridLayout.findViewWithTag(userId);
        if (view != null) {
            audioContainerGridLayout.removeView(view);
        }
        participants.remove(userId);

        Toast.makeText(getActivity(), "??????" + ChatManager.Instance().getUserDisplayName(userId) + "???????????????", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didChangeMode(boolean audioOnly) {

    }

    @Override
    public void didCreateLocalVideoTrack() {

    }

    @Override
    public void didReceiveRemoteVideoTrack(String userId) {

    }

    @Override
    public void didRemoveRemoteVideoTrack(String userId) {

    }

    @Override
    public void didError(String s) {
        Toast.makeText(getActivity(), "????????????" + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didGetStats(StatsReport[] statsReports) {

    }

    @Override
    public void didVideoMuted(String s, boolean b) {

    }

    @Override
    public void didReportAudioVolume(String userId, int volume) {
        Log.d(TAG, userId + " volume " + volume);
        ConferenceItem multiCallItem = getUserConferenceItem(userId);
        if (multiCallItem != null) {
            if (volume > 1000) {
                multiCallItem.getStatusTextView().setVisibility(View.VISIBLE);
                multiCallItem.getStatusTextView().setText("????????????");
            } else {
                multiCallItem.getStatusTextView().setVisibility(View.GONE);
                multiCallItem.getStatusTextView().setText("");
            }
        }
    }

    private Handler handler = new Handler();

    private void updateCallDuration() {
        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session != null && session.getState() == AVEngineKit.CallState.Connected) {
            long s = System.currentTimeMillis() - session.getConnectedTime();
            s = s / 1000;
            String text;
            if (s > 3600) {
                text = String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
            } else {
                text = String.format("%02d:%02d", s / 60, (s % 60));
            }
            durationTextView.setText(text);
        }
        handler.postDelayed(this::updateCallDuration, 1000);
    }
}

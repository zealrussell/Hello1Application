/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.voip.conference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.webrtc.StatsReport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfire.chat.kit.GlideApp;
import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.R2;
import cn.wildfire.chat.kit.user.UserViewModel;
import cn.wildfire.chat.kit.voip.conference.ConferenceActivity;
import cn.wildfire.chat.kit.voip.conference.ConferenceParticipantAdapter;
import cn.wildfirechat.avenginekit.AVEngineKit;
import cn.wildfirechat.model.UserInfo;

public class ConferenceIncomingFragment extends Fragment implements AVEngineKit.CallSessionCallback {

    @BindView(R2.id.invitorImageView)
    ImageView invitorImageView;
    @BindView(R2.id.invitorTextView)
    TextView invitorTextView;
    @BindView(R2.id.participantGridView)
    RecyclerView participantRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.av_multi_incoming, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        AVEngineKit.CallSession session = AVEngineKit.Instance().getCurrentSession();
        if (session == null || session.getState() == AVEngineKit.CallState.Idle) {
            getActivity().finish();
            return;
        }
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        UserInfo invitor = userViewModel.getUserInfo(session.initiator, false);
        invitorTextView.setText(invitor.displayName);
        GlideApp.with(this).load(invitor.portrait).placeholder(R.mipmap.avatar_def).into(invitorImageView);

        List<String> participants = session.getParticipantIds();
        participants.remove(invitor.uid);
        List<UserInfo> participantUserInfos = userViewModel.getUserInfos(participants);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity(), FlexDirection.ROW);
        manager.setJustifyContent(JustifyContent.CENTER);

        ConferenceParticipantAdapter adapter = new ConferenceParticipantAdapter();
        adapter.setParticipants(participantUserInfos);
        participantRecyclerView.setLayoutManager(manager);
        participantRecyclerView.setAdapter(adapter);
    }


    @OnClick(R2.id.hangupImageView)
    void hangup() {
        ((ConferenceActivity) getActivity()).hangup();
    }

    @OnClick(R2.id.acceptImageView)
    void accept() {
        ((ConferenceActivity) getActivity()).accept();
    }

    @Override
    public void didCallEndWithReason(AVEngineKit.CallEndReason reason) {
        getActivity().finish();
    }

    @Override
    public void didChangeState(AVEngineKit.CallState state) {

    }

    @Override
    public void didParticipantJoined(String userId) {

    }

    @Override
    public void didParticipantConnected(String userId) {

    }

    @Override
    public void didParticipantLeft(String userId, AVEngineKit.CallEndReason reason) {

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
    public void didError(String error) {

    }

    @Override
    public void didGetStats(StatsReport[] reports) {

    }

    @Override
    public void didVideoMuted(String userId, boolean videoMuted) {

    }

    @Override
    public void didReportAudioVolume(String userId, int volume) {

    }
}

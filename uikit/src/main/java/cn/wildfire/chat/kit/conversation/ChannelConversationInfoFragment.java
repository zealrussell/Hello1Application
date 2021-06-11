/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfire.chat.kit.WfcScheme;
import cn.wildfire.chat.kit.WfcUIKit;
import cn.wildfire.chat.kit.channel.ChannelViewModel;
import cn.wildfire.chat.kit.conversation.file.FileRecordActivity;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModel;
import cn.wildfire.chat.kit.conversationlist.ConversationListViewModelFactory;
import cn.wildfire.chat.kit.qrcode.QRCodeActivity;
import cn.wildfire.chat.kit.search.SearchMessageActivity;
import cn.wildfire.chat.kit.widget.OptionItemView;
import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.R2;
import cn.wildfirechat.model.ChannelInfo;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.ConversationInfo;

public class ChannelConversationInfoFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    // common
    @BindView(R2.id.portraitImageView)
    ImageView portraitImageView;
    @BindView(R2.id.stickTopSwitchButton)
    SwitchButton stickTopSwitchButton;
    @BindView(R2.id.silentSwitchButton)
    SwitchButton silentSwitchButton;

    @BindView(R2.id.channelNameOptionItemView)
    OptionItemView channelNameOptionItemView;
    @BindView(R2.id.channelDescOptionItemView)
    OptionItemView channelDescOptionItemView;

    private ConversationInfo conversationInfo;
    private ConversationViewModel conversationViewModel;
    private ChannelViewModel channelViewModel;
    private ChannelInfo channelInfo;

    public static ChannelConversationInfoFragment newInstance(ConversationInfo conversationInfo) {
        ChannelConversationInfoFragment fragment = new ChannelConversationInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("conversationInfo", conversationInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        conversationInfo = args.getParcelable("conversationInfo");
        assert conversationInfo != null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation_info_channel_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        conversationViewModel = WfcUIKit.getAppScopeViewModel(ConversationViewModel.class);
        channelViewModel = ViewModelProviders.of(this).get(ChannelViewModel.class);
        channelInfo = channelViewModel.getChannelInfo(conversationInfo.conversation.target, true);

        if (channelInfo != null) {
            initChannel(channelInfo);
        }

        channelViewModel.channelInfoLiveData().observe(this, channelInfos -> {
            if (channelInfos != null) {
                for (ChannelInfo info : channelInfos) {
                    if (conversationInfo.conversation.target.equals(info.channelId)) {
                        initChannel(info);
                    }
                }
            }

        });

        stickTopSwitchButton.setChecked(conversationInfo.isTop);
        silentSwitchButton.setChecked(conversationInfo.isSilent);
        stickTopSwitchButton.setOnCheckedChangeListener(this);
        silentSwitchButton.setOnCheckedChangeListener(this);
    }

    private void initChannel(ChannelInfo channelInfo) {
        channelNameOptionItemView.setDesc(channelInfo.name);
        channelDescOptionItemView.setDesc(channelInfo.desc);
        Glide.with(this).load(channelInfo.portrait).into(portraitImageView);
    }

    @OnClick(R2.id.searchMessageOptionItemView)
    void searchGroupMessage() {
        Intent intent = new Intent(getActivity(), SearchMessageActivity.class);
        intent.putExtra("conversation", conversationInfo.conversation);
        startActivity(intent);
    }

    @OnClick(R2.id.clearMessagesOptionItemView)
    void clearMessage() {
        conversationViewModel.clearConversationMessage(conversationInfo.conversation);
    }

    @OnClick(R2.id.channelQRCodeOptionItemView)
    void showChannelQRCode() {
        String qrCodeValue = WfcScheme.QR_CODE_PREFIX_CHANNEL + channelInfo.channelId;
        Intent intent = QRCodeActivity.buildQRCodeIntent(getActivity(), "频道二维码", channelInfo.portrait, qrCodeValue);
        startActivity(intent);
    }

    @OnClick(R2.id.fileRecordOptionItemView)
    void fileRecord(){
        Intent intent = new Intent(getActivity(), FileRecordActivity.class);
        intent.putExtra("conversation", conversationInfo.conversation);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void stickTop(boolean top) {
        ConversationListViewModel conversationListViewModel = ViewModelProviders
            .of(this, new ConversationListViewModelFactory(Arrays.asList(Conversation.ConversationType.Single, Conversation.ConversationType.Group, Conversation.ConversationType.Channel), Arrays.asList(0)))
            .get(ConversationListViewModel.class);
        conversationListViewModel.setConversationTop(conversationInfo, top);
    }

    private void silent(boolean silent) {
        conversationViewModel.setConversationSilent(conversationInfo.conversation, silent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.stickTopSwitchButton) {
            stickTop(isChecked);
        } else if (id == R.id.silentSwitchButton) {
            silent(isChecked);
        }

    }
}

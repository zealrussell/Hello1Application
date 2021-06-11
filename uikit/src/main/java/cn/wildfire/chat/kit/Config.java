/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit;

import android.os.Environment;

/**
 * Created by heavyrain lee on 2017/11/24.
 */

public class Config {

    /**
     * 仅仅是host，没有http开头，也不用配置端口，<strong> 底层会使用默认的80端口</strong>，不可配置为127.0.0.1 或者 192.168.0.1
     * <br>
     * <br>
     * 可以是IP，可以是域名，如果是域名的话只支持主域名或www域名或im或imtest或imdev的二级域名，其它二级域名不支持！建议使用域名。
     * <br>
     * <br>
     * 例如：example.com或www.example.com或im.example.com是支持的；xx.example.com或xx.yy.example.com是不支持的。
     * <br>
     * <br>
     */
    public static String IM_SERVER_HOST /*请仔细阅读上面的注释*/ = "101.133.166.127";

    // 注意APP_SERVER_ADDRESS已从kit中移除，移动到了AppService.java中
    //public static String APP_SERVER_ADDRESS = "http://wildfirechat.cn:8888";

    /**
     * 音视频通话所用的turn server配置
     * <br>
     * <br>
     * <strong>上线商用时，请自行部署turn 服务</strong>
     * <br>
     */
    public static String[][] ICE_SERVERS/*请仔细阅读上面的注释*/ = new String[][]{
        // 数组元素定义
        /*{"turn server uri", "userName", "password"}*/
        {"turn:turn.wildfirechat.cn:3478", "wfchat", "wfchat"}
    };

    //文件传输助手用户ID，服务器有个默认文件助手的机器人，如果修改它的ID，需要客户端和服务器数据库同步修改
    public static String FILE_TRANSFER_ID = "wfc_file_transfer";

    /**
     * 允许撤回多长时间内的消息，不能长于服务端相关配置，单位是秒
     */
    public static int RECALL_TIME_LIMIT = 60;

    /**
     * 允许重新编辑多长时间内的撤回消息，单位是秒
     */
    public static int RECALL_REEDIT_TIME_LIMIT = 60;

    /**
     * 语音消息最长时长，单位是秒
     */
    public static int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 60;

    public static String VIDEO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/video";
    public static String AUDIO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/audio";
    public static String PHOTO_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/photo";
    public static String FILE_SAVE_DIR = Environment.getExternalStorageDirectory().getPath() + "/wfc/file";

}

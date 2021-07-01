package com.example.hello1application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.sdk.utils.GeneralSimpleResultParser;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class OCRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        initTextSDK();
    }

    /**
     * 对应百度平台上的应用apiKey
     */
    private String apiKey = "pvWPFvcQexsHGL6FZ95Mkr3G";
    /**
     * 对应百度平台上的应用secretKey
     */
    private String secretKey = "SY6Ka7sF4wEHApMt8iEFBU2A7YXUklMB";

    /**
     * 用明文ak，sk初始化
     */
    private void initTextSDK() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.d("result-->","成功！"+token);
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.d("result-->","失败！"+error.getMessage());
            }
        }, getApplicationContext(),  apiKey, secretKey);
    }

    /**
     * 通用文字识别请求码
     */
    private static final int REQUEST_CODE_GENERAL_BASIC = 100;

    /**
     * 通用文字识别
     * @param view
     */
    public void generalBasic(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        //传入文件保存的路径
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, getSaveFile(getApplication()).getAbsolutePath());
        //传入文件类型
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
        //跳转页面时传递请求码，返回时根据请求码判断获取识别的数据。
        startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
    }

    /**
     * Toast提示
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    /**
     * 获取保存文件
     * @param context
     * @return
     */
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "bingli.jpg");
        return file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(this, getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result){
                            String res = "";

                            try {
                                GeneralResult jsonResult = new GeneralSimpleResultParser().parse(result);
                                List<? extends WordSimple> wordList = jsonResult.getWordList();
                                Log.d("ocrum-->", String.valueOf(jsonResult.getWordsResultNumber()));
                                for (WordSimple ws : wordList) {
                                    res += ws.getWords() + " ";
                                    Log.d("ocrword-->", ws.getWords());
                                }

                            }catch (OCRError e){
                                e.getMessage();
                            } finally {
                                Log.d("ocrresult-->",result);
                                showMsg(res);
                            }

                            //showMsg( getWord(result)) ;
                        }


                    });
        }
    }

    //从结果中切分出
    protected String getWord(String result){
        String ans = "";
        try {
            //第一次，取出括号内
            ans = result.substring(result.indexOf("[")+1,result.indexOf("]"));

            if(ans == null || ans.isEmpty()) ans="未识别到结果！";
            else {
//                if( ans.indexOf(":") < ans.length() )
//                    ans = ans.substring(result.indexOf(":")+1,result.indexOf("}"));
                ans = "识别结果：" + ans;
            }
            Log.d("ocrgetword-->",ans);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ans;
    }

}
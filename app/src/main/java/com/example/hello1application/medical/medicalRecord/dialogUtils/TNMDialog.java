package com.example.hello1application.medical.medicalRecord.dialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hello1application.R;

public class TNMDialog extends Dialog implements View.OnClickListener{

    //在构造方法里提前加载了样式
    private Context context; //上下文
    private View contentView; //布局文件view(原来是int类型的id)
    private int[] listenedItem; //监听的控件id

    private RadioGroup radio_tnm_T,radio_tnm_N,radio_tnm_M;
    private RadioButton radioButton_t,radioButton_n,radioButton_m; // 被选中的按钮
    StringBuilder stringBuilder = new StringBuilder();
    //private Button btn_tnm_sure;

    public TNMDialog(Context context, View contentView, int[] listenedItem){
        super(context);//加载dialog的样式，如果要更改弹窗的主题，记得写在这里
        this.context = context;
        this.contentView = contentView;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
        //dialogWindow.setWindowAnimations();设置动画效果
        setContentView(contentView);

        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth();// 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
        //遍历控件id添加点击注册
        for(int id:listenedItem){
            findViewById(id).setOnClickListener(this);
        }

        // add
        radio_tnm_T = findViewById(R.id.radio_tnm_T);
        radio_tnm_N = findViewById(R.id.radio_tnm_N);
        radio_tnm_M = findViewById(R.id.radio_tnm_M);
    }
    private OnCenterItemClickListener listener;

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(TNMDialog dialog, View view, String str, StringBuilder stringBuilder);
    }

    // 很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(TNMDialog.OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        dismiss();//注意：我在这里加了这句话，表示只要按任何一个控件的id,弹窗都会消失，不管是确定还是取消。
        String str = setTx_TNM_sure();
        listener.OnCenterItemClick(this,view,str,stringBuilder);
    }

    private String setTx_TNM_sure(){
        String[] splitStr;
        String[] splitStr2;
        CharSequence radio_t = null,radio_n = null,radio_m = null;
        for(int i=0;i<radio_tnm_T.getChildCount();i++){
            radioButton_t = (RadioButton)radio_tnm_T.getChildAt(i);
            if(radioButton_t.isChecked()){
                radio_t = radioButton_t.getText();

                splitStr = radioButton_t.getText().toString().split("：");
                stringBuilder.append(splitStr[0]+";");
                Log.i("T", "T选的是："+radio_t);
            }
        }
        for(int i=0;i<radio_tnm_N.getChildCount();i++){
            radioButton_n = (RadioButton)radio_tnm_N.getChildAt(i);
            if(radioButton_n.isChecked()){
                radio_n = radioButton_n.getText();

                splitStr2 = radioButton_n.getText().toString().split("：");
                stringBuilder.append(splitStr2[0]+";");
                Log.i("N", "N选的是："+radio_n);
            }
        }
        for(int i=0;i<radio_tnm_M.getChildCount();i++){
            radioButton_m = (RadioButton)radio_tnm_M.getChildAt(i);
            if(radioButton_m.isChecked()){
                radio_m = radioButton_m.getText();

                stringBuilder.append(radio_m+";");
                Log.i("M", "M选的是："+radio_m);
            }
        }

        String str = radio_t+";"+radio_n+";"+radio_m;
        return str;
    }
}

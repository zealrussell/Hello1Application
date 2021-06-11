package com.example.hello1application.medical.medicalRecord.dialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.hello1application.R;

public class DosageReviewDialog extends Dialog implements View.OnClickListener {
    //在构造方法里提前加载了样式
    private Context context; //上下文
    private View contentView; //布局文件view(原来是int类型的id)
    private int[] listenedItem; //监听的控件id

    private TextView tvAdaptDosage;
    private TextView tvReviewTimeShow;

    private String strAdaptDosage,strReviewTimeShow;
    private Button btnDosageSure;

    public DosageReviewDialog(Context context, View contentView, int[] listenedItem,String strAdaptDosage,String strReviewTimeShow){
        super(context);//加载dialog的样式，如果要更改弹窗的主题，记得写在这里
        this.context = context;
        this.contentView = contentView;
        this.listenedItem = listenedItem;
        this.strAdaptDosage = strAdaptDosage;
        this.strReviewTimeShow = strReviewTimeShow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }
    private DosageReviewDialog.OnCenterItemClickListener listener;

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(DosageReviewDialog dialog, View view);
    }

    // 很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(DosageReviewDialog.OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_dosage_sure:
                dismiss();//按这个控件的id,弹窗会消失
                listener.OnCenterItemClick(this,view);
                break;
        }

    }


    private void initView(){
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
            findViewById(id);
        }
    }

    private void initData(){
        // add
        tvAdaptDosage = findViewById(R.id.tv_adapt_dosage);
        tvReviewTimeShow = findViewById(R.id.tv_review_time_show);
        btnDosageSure = findViewById(R.id.btn_dosage_sure);

        tvAdaptDosage.setText(strAdaptDosage);
        tvReviewTimeShow.setText(strReviewTimeShow);

        btnDosageSure.setOnClickListener(this);
    }
}

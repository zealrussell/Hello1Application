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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.hello1application.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;


public class SideEffectsDialog extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    // TODO 监听器：自定义接口类型，是Activity传入的方式（Activity在实现了自定义接口，会以强制转换的方式传入content）
    private SideEffectsDialog.OnSideEffectsCenterItemClickListener listener;

    private Context context; //上下文
    private View contentView; //布局文件view(原来是int类型的id)
    private int[] listenedItem; //监听的控件id
    private Button mBtnSideEffectsSure;
    private StringBuilder stringBuilder = new StringBuilder(); // 让前端显示的信息（简略）
    private StringBuilder stringBuilderDb = new StringBuilder(); // 让数据库显示的信息（详细）
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> hashMapDb = new HashMap<>();
    Vector<String> list = new Vector<>();
    Vector<String> listDb = new Vector<>();

    public SideEffectsDialog(Context context, View contentView, int[] listenedItem){
        super(context);//加载dialog的样式，如果要更改弹窗的主题，记得写在这里
        this.context = context;
        this.contentView = contentView;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData(); // 初始化Dialog的样式以及数据
    }

    // TODO 自定义接口，目的是让Activity实现
    public interface OnSideEffectsCenterItemClickListener {
        void OnSideEffectsCenterItemClick(SideEffectsDialog dialog, View view, Vector<String> list, Vector<String> listDb);
    }

    // TODO 自定义方法，目的是让Activity去实例化该Dialog，然后调用此方法，让Activity得以将自己的content传进来
    public void setOnSideEffectsCenterItemClickListener(SideEffectsDialog.OnSideEffectsCenterItemClickListener listener) {
        this.listener = listener;
    }

    // TODO “点击事件”的监听： 按下“确定”按钮后，将信息回调给Activity
    @Override
    public void onClick(View view) {
        dismiss(); // 按“确定”或“取消”后，弹窗消失

        if(view.getId() == R.id.btn_sideEffects_sure){
            listener.OnSideEffectsCenterItemClick(this,view,list,listDb);
        }
    }

    // TODO “选中事件”的监听： checkbox选中的内容，并用StringBuilder将内容存起来
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        getSideEffectsContent(compoundButton,checked); // 获取副作用复发风险层选中的内容
    }

    /**
     * TODO ************************获取副作用复发风险层选中的内容**************************
     * @param compoundButton
     * @param checked
     */
    private void getSideEffectsContent(CompoundButton compoundButton, boolean checked){
        switch (compoundButton.getId()){
            case R.id.sideEffect_lowSick1:
                if(checked){
                    list.add("1");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("1");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick2:
                if(checked){
                    list.add("2");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("2");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick3:
                if(checked){
                    list.add("3");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("3");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick4:
                if(checked){
                    list.add("4");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("4");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick5:
                if(checked){
                    list.add("5");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("5");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick6:
                if(checked){
                    list.add("6");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("6");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick7:
                if(checked){
                    list.add("7");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("7");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_lowSick8:
                if(checked){
                    list.add("8");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("8");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk1:
                if(checked){
                    list.add("9");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("9");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk2:
                if(checked){
                    list.add("10");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("10");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk3:
                if(checked){
                    list.add("11");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("11");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk4:
                if(checked){
                    list.add("12");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("12");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk5:
                if(checked){
                    list.add("13");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("13");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk6:
                if(checked){
                    list.add("14");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("14");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk7:
                if(checked){
                    list.add("15");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("15");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_intermediateRisk8:
                if(checked){
                    list.add("16");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("16");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_highRisk1:
                if(checked){
                    list.add("17");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("17");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_highRisk2:
                if(checked){
                    list.add("18");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("18");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_highRisk3:
                if(checked){
                    list.add("19");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("19");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
            case R.id.sideEffect_highRisk4:
                if(checked){
                    list.add("20");
                    listDb.add(compoundButton.getText().toString()+";");
                }
                if(!checked){
                    list.remove("20");
                    listDb.remove(compoundButton.getText().toString()+";");
                }
                break;
        }
    }


    // TODO ********初始化副作用复发风险层的内容，包括Dialog的启动以及传入的控件id**********
    private void initData(){
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

        CheckBox box;
        //遍历控件id
        for(int id:listenedItem){
            box = findViewById(id);
            box.setOnCheckedChangeListener(this);
        }

        mBtnSideEffectsSure = findViewById(R.id.btn_sideEffects_sure);
        mBtnSideEffectsSure.setOnClickListener(this);
    }
}

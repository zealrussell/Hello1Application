package com.example.hello1application.medical.medicalRecord.record;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.hello1application.R;
import com.example.hello1application.common.http.Code;
import com.example.hello1application.common.http.model.IndexDetailGson;
import com.example.hello1application.common.http.model.InsertGson;
import com.example.hello1application.common.utils.StringUtils;
import com.example.hello1application.medical.BasicInfoActivity;
import com.example.hello1application.medical.medicalRecord.calculateDosage.DosageReviewActivity;
import com.example.hello1application.medical.medicalRecord.dialogUtils.DTCDialog;
import com.example.hello1application.medical.medicalRecord.dialogUtils.SideEffectsDialog;
import com.example.hello1application.medical.medicalRecord.dialogUtils.TNMDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PostMedicalRecordActivity extends AppCompatActivity
        implements OnChangeLisener, OnSureLisener, View.OnClickListener,RadioGroup.OnCheckedChangeListener,
        TNMDialog.OnCenterItemClickListener, DTCDialog.OnDtcCenterItemClickListener, SideEffectsDialog.OnSideEffectsCenterItemClickListener {

    private AlertDialog.Builder builder;
    private int choice;

    TextView tx_illSelector,tx_operationMethod,tx_operationDate,tx_TNM,tx_DTC,tx_negative_risk,tx_firstTreatDate,tx_firstTreat,tx_thisTreatDate;
    EditText edit_centerLymphMove,edit_neckLymphMove;
    LinearLayout layout_illSelector,layout_operationMethod,layout_operationDate,layout_gone;
    LinearLayout layout_centerLymphMove,layout_neckLymphMove,layout_distantMove;
    LinearLayout layout_TNM,layout_DTC,layout_negative_risk;
    LinearLayout layout_I131treat,layout_treatMethod,layout_AfterThreatMethod,layout_thyroidRestrainTreat,layout_firstTreatDate,layout_firstTreat,layout_ThisTreatDate;

    RadioGroup radio_centerLymphMove,radio_neckLymphMove,radio_distantMove,radio_I131treat,radio_treatMethod,radio_AfterThreatMethod,radio_thyroidRestrainTreat;

    Button btn_basicInfo,btn_record_sure;
    HashMap<String,String> hashMap = new HashMap<>(); //保存radio类型的字段
    StringBuilder sbDescription = new StringBuilder(); //传递给后端数据库
    PostMedicalRecord medicalRecord = new PostMedicalRecord();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        initData(); //初始化参数
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.layout_illSelector: //病情分类(选择肿瘤)，单选框
//                setTx_illSelector();
//                break;
//            case R.id.layout_operationMethod: //手术方式，单选框
//                setTx_operationMethod();
//                break;
//            case R.id.layout_operationDate: //手术日期，pickTime
//                setLayout_operationDate(R.id.tx_operationDate);
//                break;
//            case R.id.layout_TNM: //TNM_layout
//                setTx_TNM();
//                break;
//            case R.id.layout_DTC: //DTC复发风险层
//                setTx_DTC();
//                break;
//            case R.id.layout_firstTreatDate: // 首次就诊时间_layout
//                setLayout_operationDate(R.id.tx_firstTreatDate);
//                break;
//            case R.id.layout_firstTreat: // 首次诊断layout
//                setLayout_firstTreat();
//                break;
//            case R.id.btn_basicInfo: // 个人信息button
//                setButton_basicInfo();
//                break;
            case R.id.tx_illSelector: //病情分类(选择肿瘤)，单选框
                setTx_illSelector();
                break;
            case R.id.tx_operationMethod: //手术方式，单选框
                setTx_operationMethod();
                break;
            case R.id.tx_operationDate: //手术日期，pickTime
                setLayout_operationDate(R.id.tx_operationDate);
                break;
            case R.id.tx_TNM: //TNM_layout
                setTx_TNM();
                break;
            case R.id.tx_DTC: //DTC复发风险层
                setTx_DTC();
                break;
            case R.id.tx_negative_risk: //副作用复发复发风险层
                setTx_SideEffects();
                break;
            case R.id.tx_firstTreatDate: // 首次就诊时间_layout
                setLayout_operationDate(R.id.tx_firstTreatDate);
                break;
            case R.id.tx_firstTreat: // 首次诊断layout
                setLayout_firstTreat();
                break;
            case R.id.btn_basicInfo: // 个人信息button
                setButton_basicInfo();
                break;
            case R.id.btn_record_sure: // 点击“保存”
                setButton_recordSure();
                break;
            case R.id.tx_ThisTreatDate: // 本次诊断时间
                setLayout_operationDate(R.id.tx_ThisTreatDate);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        radioInfo(radioGroup);
    }


    // TODO *********************点击“保存“ 按钮”*****************************
    private void setButton_recordSure() {
        String neckLymphMoveNum = edit_neckLymphMove.getText().toString();
        if(!"".equals(neckLymphMoveNum)){
            hashMap.put("neckLymphMoveNum",neckLymphMoveNum);
        }
        String centerLymphMoveNum = edit_centerLymphMove.getText().toString();
        if(!"".equals(centerLymphMoveNum)){
            hashMap.put("centerLymphMoveNum",centerLymphMoveNum);
        }

        //String str = sbDescription.toString();
        String str = StringUtils.getMapToString(hashMap);

        medicalRecord.setOperationDescription(str);

        // 更新到数据库
        PostMedicalRecordNetRequest postMedicalRecordNetRequest = new PostMedicalRecordNetRequest();
        postMedicalRecordNetRequest.addPostMedicalRecord(medicalRecord,mHandler);
    }

    /**
     * 个人信息，button
     */
    private void setButton_basicInfo(){
        //跳转到ButtonActivity演示界面
        Intent intent = new Intent(PostMedicalRecordActivity.this, BasicInfoActivity.class); //从 A类 跳转到 B类
        startActivity(intent);
        Toast.makeText(PostMedicalRecordActivity.this, "个人信息", Toast.LENGTH_LONG).show();
    }



    // TODO ***************  病情分类，肿瘤（单选框） **********************
    private void setTx_illSelector(){
        //默认选中第一个
        final String[] items = {"甲状腺乳头状癌", "甲状腺滤泡癌", "甲状腺髓样癌", "未分化癌", "甲状腺良性肿瘤(包括甲亢)术后甲低", "非手术因素导致的甲低（桥本）"};
        choice = -1;
        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("单选列表")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(choice != -1){
                            // 根据不同的肿瘤形式，呈现不同的展示
                            illSelectorOnClickListener(choice);

                            medicalRecord.setMedicalCaseType(String.valueOf(choice));
                            medicalRecord.setMedicalCaseName(items[choice]);

                            tx_illSelector.setText(items[choice]);  //更新到textView上

                            //hashMap.put("illSelector",items[choice]);
                            Toast.makeText(PostMedicalRecordActivity.this, "您选择了" + items[choice], Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create().show();
    }



    // TODO ***************************手术方式（单选框）****************************
    private void setTx_operationMethod(){
        //默认选中第一个
        final String[] items = {"单侧切除", "单侧切除+同侧中央区清扫","全切+中央区清扫", "全切+中央区清扫+颈侧区清扫"};
        choice = -1;
        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("手术方式")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (choice != -1) {
                            medicalRecord.setOperationWay(items[choice]);

                            tx_operationMethod.setText(items[choice]);  //更新到textView上

                            //hashMap.put("operationMethod",items[choice]);
                            Toast.makeText(PostMedicalRecordActivity.this, "您选择了" + items[choice], Toast.LENGTH_LONG).show();

                        }
                    }
                });
        builder.create().show();
    }


    // TODO ********************* DTC分层   副作用风险分层 ，存在StringBuilder里***********************************
    private void setTx_DTC(){
        DTCDialog dtcDialog;

        View contentView = View.inflate(this,R.layout.dialog_dtc,null);
        //new Dialog(this);
        //实例化自定义的dialog
        dtcDialog = new DTCDialog(this,contentView,new int[]{R.id.dtc_lowSick1,R.id.dtc_lowSick2,R.id.dtc_lowSick3,R.id.dtc_lowSick4,R.id.dtc_lowSick5,R.id.dtc_intermediateRisk1,R.id.dtc_intermediateRisk2,R.id.dtc_intermediateRisk3,R.id.dtc_highRisk1,R.id.dtc_highRisk2,R.id.dtc_highRisk3,R.id.dtc_highRisk4,R.id.dtc_highRisk5,R.id.dtc_highRisk6});
        //绑定点击事件
        dtcDialog.setOnDtcCenterItemClickListener((DTCDialog.OnDtcCenterItemClickListener) this);
        //显示
        dtcDialog.show();
        //调用点击函数
    }
    @Override
    public void OnDtcCenterItemClick(DTCDialog dialog, View view, Vector<String> list, Vector<String> listDb) {
        if(view.getId() == R.id.btn_dtc_sure){
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilderDb = new StringBuilder();
            for(Object obj: list){
                stringBuilder.append(obj+";");
            }
            for(Object obj: listDb){
                stringBuilderDb.append(obj);
            }

            String str = stringBuilder.toString();

            hashMap.put("dtc",stringBuilderDb.toString());
            //sbDescription.append("dialog_dtc:"+stringBuilderDb.toString()+";\n");
            medicalRecord.setDtc(str);// 传递给后端的TSH目标判定（dialog_dtc）

            Toast.makeText(PostMedicalRecordActivity.this,"您选择的是"+str, Toast.LENGTH_LONG).show();
            tx_DTC.setText(str);
        }
    }


    // TODO *************************  副作用风险层  存在StringBuilder里*****************************************
    private void setTx_SideEffects(){
        SideEffectsDialog sideEffectsDialog;

        View contentView = View.inflate(this,R.layout.dialog_side_effects,null);
        //new Dialog(this);
        //实例化自定义的dialog
        sideEffectsDialog = new SideEffectsDialog(this,contentView,new int[]{R.id.sideEffect_lowSick1,R.id.sideEffect_lowSick2,R.id.sideEffect_lowSick3,R.id.sideEffect_lowSick4,R.id.sideEffect_lowSick5,R.id.sideEffect_lowSick6,R.id.sideEffect_lowSick7,R.id.sideEffect_lowSick8,R.id.sideEffect_intermediateRisk1,R.id.sideEffect_intermediateRisk2,R.id.sideEffect_intermediateRisk3,R.id.sideEffect_intermediateRisk4,R.id.sideEffect_intermediateRisk5,R.id.sideEffect_intermediateRisk6,R.id.sideEffect_intermediateRisk7,R.id.sideEffect_intermediateRisk8,R.id.sideEffect_highRisk1,R.id.sideEffect_highRisk2,R.id.sideEffect_highRisk3,R.id.sideEffect_highRisk4});
        //绑定点击事件
        sideEffectsDialog.setOnSideEffectsCenterItemClickListener((SideEffectsDialog.OnSideEffectsCenterItemClickListener)this);
        //显示
        sideEffectsDialog.show();
        //调用点击函数
    }
    @Override
    public void OnSideEffectsCenterItemClick(SideEffectsDialog dialog, View view, Vector<String> list, Vector<String> listDb) {
        if(view.getId() == R.id.btn_sideEffects_sure){
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilderDb = new StringBuilder();
            for(Object obj: list){
                stringBuilder.append(obj+";");
            }
            for(Object obj: listDb){
                stringBuilderDb.append(obj);
            }
            String str = stringBuilder.toString();

            hashMap.put("sideEffect",stringBuilderDb.toString());
            //sbDescription.append("sideEffects:"+stringBuilderDb.toString()+";\n");
            medicalRecord.setSideEffect(str);

            Toast.makeText(PostMedicalRecordActivity.this,"您选择的是"+str, Toast.LENGTH_LONG).show();
            tx_negative_risk.setText(str);
        }
    }


    // TODO *********************** TNM分期  (存在StringBuilder里)***********************
    private void setTx_TNM(){
        TNMDialog tnmDialog;
        View contentView = View.inflate(this,R.layout.dialog_tnm,null);
        //实例化自定义的dialog, contentView就是R.layout.dialog_tnm
        tnmDialog = new TNMDialog(this,contentView,new int[]{R.id.radio_tnm_T,R.id.radio_tnm_N,R.id.radio_tnm_M,R.id.btn_tnm_sure});
        //绑定点击事件
        tnmDialog.setOnCenterItemClickListener((TNMDialog.OnCenterItemClickListener) this);
        //显示
        tnmDialog.show();
        //调用点击函数
    }
    // TNM分期，Activity接收回调
    @Override
    public void OnCenterItemClick(TNMDialog dialog, View view,String str,StringBuilder stringBuilder) {
        if(view.getId() == R.id.btn_tnm_sure){
            hashMap.put("tnm",str);
            //sbDescription.append("dialog_tnm:"+str+";\n");

            Toast.makeText(PostMedicalRecordActivity.this,"您选择的是"+stringBuilder.toString(), Toast.LENGTH_LONG).show();
            tx_TNM.setText(stringBuilder.toString());
        }
    }



  // TODO ************************首次诊断 (存在StringBuilder里)*******************************
    private void setLayout_firstTreat(){
        final List<Integer> choice = new ArrayList<>();

        final String[] items = {"桥本甲状腺炎","亚急性甲状腺炎","甲亢I131治疗后","FT3","FT4","TSH",
                            "Tg","TgAb","TRAB","TPOAB","甲状腺摄碘率检查"};
        //默认都未选中
        boolean[] isSelect = {false, false, false, false,false, false, false, false, false, false, false};

        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher)
                .setTitle("首次诊断")
                .setMultiChoiceItems(items, isSelect, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            choice.add(i);
                        } else {
                            choice.remove(i);
                        }
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder str = new StringBuilder();

                        for (int j = 0; j < choice.size(); j++) {
                            str.append(items[choice.get(j)]+";");
                        }

                        tx_firstTreat.setText(str);
                        //sbDescription.append("layout_firstTreat:"+str.toString()+";\n");
                        hashMap.put("firstTreat",str.toString());
                        Toast.makeText(PostMedicalRecordActivity.this, "您选择了" + str, Toast.LENGTH_LONG).show();
                    }
                });

        builder.create().show();

    }



    // TODO ******************日期选择，pickTime控件（手术时间、首次诊断时间）**********************
    private void setLayout_operationDate(int sureLister_id){
        showDatePickDialog(DateType.TYPE_YMD,sureLister_id);
    }
    // 日期控件
    private void showDatePickDialog(DateType type,int sureLister_id) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(type);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(this);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(this,sureLister_id);
        dialog.show();
    }
    // 日期控件的“改变”监听
    @Override
    public void onChanged(Date date) {

    }
    // 日期控件的“确定”监听
    @Override
    public void onSure(Date date,int sureLister_id) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);

        switch(sureLister_id){
            // 手术时间
            case R.id.tx_operationDate:
                tx_operationDate.setText(dateStr);
                medicalRecord.setOperationDate(dateStr);

                Toast.makeText(PostMedicalRecordActivity.this,"您选择的日期是"+dateStr,Toast.LENGTH_LONG).show();
                break;
            // 首次诊断时间
            case R.id.tx_firstTreatDate:
                tx_firstTreatDate.setText(dateStr);
                hashMap.put("firstTreatDate",dateStr);
                //sbDescription.append("firstTreatDate:"+dateStr2+";\n");

                Toast.makeText(PostMedicalRecordActivity.this,"您选择的日期是"+dateStr,Toast.LENGTH_LONG).show();
                break;
            case R.id.tx_ThisTreatDate:
                tx_thisTreatDate.setText(dateStr);
                hashMap.put("thisTreatDate",dateStr);

                Toast.makeText(PostMedicalRecordActivity.this,"您选择的日期是"+dateStr,Toast.LENGTH_LONG).show();
                break;
        }

    }


    /**
     * TODO ***************选择不同的病情后，呈现不同的展示***********************
     * @param type 如type=0时，可能是“xxx癌”
     */
    public void illSelectorOnClickListener(int type){
        switch (type){
            case 0:{ caseZeroOne(); break; } // 甲状腺乳头状癌
            case 1:{ caseZeroOne(); break; } // 甲状腺滤泡癌
            case 2:{ caseTwo(); break; } // 甲状腺髓样癌
            case 3:{ caseThree(); break; } //未分化癌
            case 4:{ caseFour(); break; } //良性肿瘤
            case 5:{ caseFive(); break; } // 非手术因素导致的甲低
        }
    }



    // TODO *****************初始化控件***********************
    private void initData() {
        tx_illSelector = findViewById(R.id.tx_illSelector);
        tx_operationMethod = findViewById(R.id.tx_operationMethod);
        tx_operationDate = findViewById(R.id.tx_operationDate);
        tx_TNM = findViewById(R.id.tx_TNM);
        tx_DTC = findViewById(R.id.tx_DTC);
        tx_negative_risk = findViewById(R.id.tx_negative_risk);
        tx_firstTreatDate = findViewById(R.id.tx_firstTreatDate);
        tx_firstTreat = findViewById(R.id.tx_firstTreat);
        tx_thisTreatDate = findViewById(R.id.tx_ThisTreatDate);
        edit_centerLymphMove = findViewById(R.id.edit_centerLymphMove);
        edit_neckLymphMove = findViewById(R.id.edit_neckLymphMove);

        layout_illSelector = findViewById(R.id.layout_illSelector);
        layout_operationMethod = findViewById(R.id.layout_operationMethod);
        layout_operationDate = findViewById(R.id.layout_operationDate);
        layout_TNM = findViewById(R.id.layout_TNM);
        layout_DTC = findViewById(R.id.layout_DTC);
        layout_firstTreatDate = findViewById(R.id.layout_firstTreatDate);
        layout_firstTreat = findViewById(R.id.layout_firstTreat);

        layout_centerLymphMove = findViewById(R.id.layout_centerLymphMove);
        layout_neckLymphMove = findViewById(R.id.layout_neckLymphMove);
        layout_distantMove = findViewById(R.id.layout_distantMove);
        layout_TNM = findViewById(R.id.layout_TNM);
        layout_DTC = findViewById(R.id.layout_DTC);
        layout_negative_risk = findViewById(R.id.layout_negative_risk);
        layout_I131treat = findViewById(R.id.layout_I131treat);
        layout_treatMethod = findViewById(R.id.layout_treatMethod);
        layout_AfterThreatMethod = findViewById(R.id.layout_AfterThreatMethod);
        layout_thyroidRestrainTreat = findViewById(R.id.layout_thyroidRestrainTreat);
        layout_firstTreatDate = findViewById(R.id.layout_firstTreatDate);
        layout_firstTreat = findViewById(R.id.layout_firstTreat);
        layout_ThisTreatDate = findViewById(R.id.layout_ThisTreatDate);
        layout_gone = findViewById(R.id.layout_gone);

        btn_basicInfo = findViewById(R.id.btn_basicInfo);
        btn_record_sure = findViewById(R.id.btn_record_sure);

        // 病情分类，肿瘤单选框
        tx_illSelector.setOnClickListener(this);
        // 手术方式，单选框
        tx_operationMethod.setOnClickListener(this);
        // 日期选择，pickTime
        //ButterKnife.bind(this);
        tx_operationDate.setOnClickListener(this);
        // TNM分期
        tx_TNM.setOnClickListener(this);
        tx_DTC.setOnClickListener(this); // dialog_dtc
        tx_negative_risk.setOnClickListener(this);
        // 首次就诊时间
        tx_firstTreatDate.setOnClickListener(this);
        // 首次诊断
        tx_firstTreat.setOnClickListener(this);
        // 本次就诊时间
        tx_firstTreatDate.setOnClickListener(this);
        // 本次就诊时间
        tx_thisTreatDate.setOnClickListener(this);

        // 个人信息buttion
        btn_basicInfo.setOnClickListener(this);
        btn_record_sure.setOnClickListener(this);

        radio_centerLymphMove = findViewById(R.id.radio_centerLymphMove);
        radio_neckLymphMove = findViewById(R.id.radio_neckLymphMove);
        radio_distantMove = findViewById(R.id.radio_distantMove);
        radio_I131treat = findViewById(R.id.radio_I131treat);
        radio_treatMethod = findViewById(R.id.radio_treatMethod);
        radio_AfterThreatMethod = findViewById(R.id.radio_AfterThreatMethod);
        radio_thyroidRestrainTreat = findViewById(R.id.radio_thyroidRestrainTreat);

        radio_centerLymphMove.setOnCheckedChangeListener(this); // 中央区淋巴转移(是、否)
        radio_neckLymphMove.setOnCheckedChangeListener(this); // 颈侧区淋巴转移（是、否）
        radio_distantMove.setOnCheckedChangeListener(this); // 远处转移
        radio_I131treat.setOnCheckedChangeListener(this); // I131治疗
        radio_treatMethod.setOnCheckedChangeListener(this); // 治疗手段
        radio_AfterThreatMethod.setOnCheckedChangeListener(this); // 术后治疗手段
        radio_thyroidRestrainTreat.setOnCheckedChangeListener(this); //甲状腺素抑制治疗
    }


    final Handler mHandler = new Handler(){
        // 定义处理消息的方法
        public void handleMessage (Message msg) {
            switch(msg.what) {
                case Code.MESSAGE_OK:
                    InsertGson gson = (InsertGson) msg.obj;
                    if(gson.getMsg().equals("ok")){
                        Toast.makeText(PostMedicalRecordActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                    }
                    Log.d("PostMedicalRecordNetRequest", "msg:" + msg.obj);
                    break;
            }
        }
    };

    // TODO 获取界面上 radioGroup的值
    private void radioInfo(RadioGroup radioGroup){
        int radioGroupId = radioGroup.getId();
        RadioButton radioButton;

        switch (radioGroupId) {
            case R.id.radio_centerLymphMove:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("centerLymphMove",radioButton.getText().toString());
                break;
            case R.id.radio_neckLymphMove:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("neckLymphMove",radioButton.getText().toString());
                break;
            case R.id.radio_distantMove:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("distantMove",radioButton.getText().toString());
                break;
            case R.id.radio_I131treat:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("I131treat",radioButton.getText().toString());
                break;
            case R.id.radio_treatMethod:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("treatMethod",radioButton.getText().toString());
                break;
            case R.id.radio_AfterThreatMethod:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("afterThreatMethod",radioButton.getText().toString());
                break;
            case R.id.radio_thyroidRestrainTreat:
                radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                hashMap.put("thyroidRestrainTreat",radioButton.getText().toString());
                break;
        }
    }


    // TODO 选择不同的病情后，展示的内容不一样
    private void caseZeroOne(){
        layout_gone.setVisibility(View.VISIBLE);
        layout_operationMethod.setVisibility(View.VISIBLE);
        layout_operationDate.setVisibility(View.VISIBLE);
        layout_centerLymphMove.setVisibility(View.VISIBLE);
        layout_neckLymphMove.setVisibility(View.VISIBLE);
        layout_distantMove.setVisibility(View.VISIBLE);
        layout_TNM.setVisibility(View.VISIBLE);
        layout_DTC.setVisibility(View.VISIBLE);
        layout_negative_risk.setVisibility(View.VISIBLE);
        layout_I131treat.setVisibility(View.VISIBLE);

        layout_treatMethod.setVisibility(View.GONE);
        if(hashMap.containsKey("treatMethod"))   hashMap.remove("treatMethod");

        layout_AfterThreatMethod.setVisibility(View.GONE);
        if(hashMap.containsKey("afterThreatMethod")) hashMap.remove("afterThreatMethod");

        layout_thyroidRestrainTreat.setVisibility(View.GONE);
        if(hashMap.containsKey("thyroidRestrainTreat")) hashMap.remove("thyroidRestrainTreat");

        layout_firstTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreat")) hashMap.remove("firstTreat");

        layout_firstTreat.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreatDate")) hashMap.remove("firstTreatDate");

        layout_ThisTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("thisTreatDate")) hashMap.remove("thisTreatDate");
    }
    private void caseTwo(){
        layout_gone.setVisibility(View.VISIBLE);
        layout_operationMethod.setVisibility(View.VISIBLE);
        layout_operationDate.setVisibility(View.VISIBLE);
        layout_centerLymphMove.setVisibility(View.VISIBLE);
        layout_neckLymphMove.setVisibility(View.VISIBLE);
        layout_distantMove.setVisibility(View.VISIBLE);
        layout_TNM.setVisibility(View.VISIBLE);

        layout_DTC.setVisibility(View.GONE);
        if(hashMap.containsKey("dtc")) hashMap.remove("dtc");
        medicalRecord.setDtc("");

        layout_negative_risk.setVisibility(View.GONE);
        if(hashMap.containsKey("sideEffect")) hashMap.remove("sideEffect");
        medicalRecord.setSideEffect("");

        layout_I131treat.setVisibility(View.GONE);
        if(hashMap.containsKey("I131treat")) hashMap.remove("I131treat");

        layout_treatMethod.setVisibility(View.GONE);
        if(hashMap.containsKey("treatMethod"))   hashMap.remove("treatMethod");

        layout_AfterThreatMethod.setVisibility(View.GONE);
        if(hashMap.containsKey("afterThreatMethod")) hashMap.remove("afterThreatMethod");

        layout_thyroidRestrainTreat.setVisibility(View.GONE);
        if(hashMap.containsKey("thyroidRestrainTreat")) hashMap.remove("thyroidRestrainTreat");

        layout_firstTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreat")) hashMap.remove("firstTreat");

        layout_firstTreat.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreatDate")) hashMap.remove("firstTreatDate");

        layout_ThisTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("thisTreatDate")) hashMap.remove("thisTreatDate");
    }
    private void caseThree(){
        layout_gone.setVisibility(View.VISIBLE);
        layout_operationMethod.setVisibility(View.VISIBLE);
        layout_operationDate.setVisibility(View.VISIBLE);
        layout_centerLymphMove.setVisibility(View.VISIBLE);
        layout_neckLymphMove.setVisibility(View.VISIBLE);
        layout_distantMove.setVisibility(View.VISIBLE);
        layout_TNM.setVisibility(View.VISIBLE);
        layout_treatMethod.setVisibility(View.VISIBLE);
        layout_AfterThreatMethod.setVisibility(View.VISIBLE);
        layout_thyroidRestrainTreat.setVisibility(View.VISIBLE);

        layout_DTC.setVisibility(View.GONE);
        if(hashMap.containsKey("dtc")) hashMap.remove("dtc");
        medicalRecord.setDtc("");

        layout_negative_risk.setVisibility(View.GONE);
        if(hashMap.containsKey("sideEffect")) hashMap.remove("sideEffect");
        medicalRecord.setSideEffect("");

        layout_I131treat.setVisibility(View.GONE);
        if(hashMap.containsKey("I131treat")) hashMap.remove("I131treat");

        layout_firstTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreat")) hashMap.remove("firstTreat");

        layout_firstTreat.setVisibility(View.GONE);
        if(hashMap.containsKey("firstTreatDate")) hashMap.remove("firstTreatDate");

        layout_ThisTreatDate.setVisibility(View.GONE);
        if(hashMap.containsKey("thisTreatDate")) hashMap.remove("thisTreatDate");
    }
    private void caseFour(){
        layout_gone.setVisibility(View.VISIBLE);
        layout_operationMethod.setVisibility(View.VISIBLE);
        layout_operationDate.setVisibility(View.VISIBLE);

        layout_centerLymphMove.setVisibility(View.GONE);
        layout_neckLymphMove.setVisibility(View.GONE);
        layout_distantMove.setVisibility(View.GONE);
        layout_TNM.setVisibility(View.GONE);
        layout_DTC.setVisibility(View.GONE);
        medicalRecord.setDtc("");
        layout_negative_risk.setVisibility(View.GONE);
        medicalRecord.setSideEffect("");
        layout_I131treat.setVisibility(View.GONE);
        layout_treatMethod.setVisibility(View.GONE);
        layout_AfterThreatMethod.setVisibility(View.GONE);
        layout_thyroidRestrainTreat.setVisibility(View.GONE);
        layout_firstTreatDate.setVisibility(View.GONE);
        layout_firstTreat.setVisibility(View.GONE);
        layout_ThisTreatDate.setVisibility(View.GONE);
        hashMap.clear();
    }
    private void caseFive(){
        layout_gone.setVisibility(View.VISIBLE);
        layout_firstTreatDate.setVisibility(View.VISIBLE);
        layout_firstTreat.setVisibility(View.VISIBLE);
        layout_ThisTreatDate.setVisibility(View.VISIBLE);

        layout_operationDate.setVisibility(View.GONE);
        medicalRecord.setOperationDate("");
        tx_operationDate.setText("选择>");
        layout_operationMethod.setVisibility(View.GONE);
        medicalRecord.setOperationWay("");
        tx_operationMethod.setText("选择>");

        layout_centerLymphMove.setVisibility(View.GONE);
        layout_neckLymphMove.setVisibility(View.GONE);
        layout_distantMove.setVisibility(View.GONE);
        layout_TNM.setVisibility(View.GONE);
        layout_DTC.setVisibility(View.GONE);
        medicalRecord.setDtc("");
        layout_negative_risk.setVisibility(View.GONE);
        medicalRecord.setSideEffect("");
        layout_I131treat.setVisibility(View.GONE);
        layout_treatMethod.setVisibility(View.GONE);
        layout_AfterThreatMethod.setVisibility(View.GONE);
        layout_thyroidRestrainTreat.setVisibility(View.GONE);

        String firstTreat = "", firstTreatDate ="";
        if(hashMap.containsKey("firstTreat")){
            firstTreat = hashMap.get("firstTreat");
        }
        if(hashMap.containsKey("firstTreatDate")){
            firstTreatDate = hashMap.get("firstTreatDate");
        }
        hashMap.clear();
        hashMap.put("firstTreat",firstTreat);
        hashMap.put("firstTreatDate",firstTreatDate);
    }


}



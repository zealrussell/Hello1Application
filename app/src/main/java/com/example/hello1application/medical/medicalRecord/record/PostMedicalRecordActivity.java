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
    HashMap<String,String> hashMap = new HashMap<>(); //??????radio???????????????
    StringBuilder sbDescription = new StringBuilder(); //????????????????????????
    PostMedicalRecord medicalRecord = new PostMedicalRecord();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        initData(); //???????????????
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.layout_illSelector: //????????????(????????????)????????????
//                setTx_illSelector();
//                break;
//            case R.id.layout_operationMethod: //????????????????????????
//                setTx_operationMethod();
//                break;
//            case R.id.layout_operationDate: //???????????????pickTime
//                setLayout_operationDate(R.id.tx_operationDate);
//                break;
//            case R.id.layout_TNM: //TNM_layout
//                setTx_TNM();
//                break;
//            case R.id.layout_DTC: //DTC???????????????
//                setTx_DTC();
//                break;
//            case R.id.layout_firstTreatDate: // ??????????????????_layout
//                setLayout_operationDate(R.id.tx_firstTreatDate);
//                break;
//            case R.id.layout_firstTreat: // ????????????layout
//                setLayout_firstTreat();
//                break;
//            case R.id.btn_basicInfo: // ????????????button
//                setButton_basicInfo();
//                break;
            case R.id.tx_illSelector: //????????????(????????????)????????????
                setTx_illSelector();
                break;
            case R.id.tx_operationMethod: //????????????????????????
                setTx_operationMethod();
                break;
            case R.id.tx_operationDate: //???????????????pickTime
                setLayout_operationDate(R.id.tx_operationDate);
                break;
            case R.id.tx_TNM: //TNM_layout
                setTx_TNM();
                break;
            case R.id.tx_DTC: //DTC???????????????
                setTx_DTC();
                break;
            case R.id.tx_negative_risk: //??????????????????????????????
                setTx_SideEffects();
                break;
            case R.id.tx_firstTreatDate: // ??????????????????_layout
                setLayout_operationDate(R.id.tx_firstTreatDate);
                break;
            case R.id.tx_firstTreat: // ????????????layout
                setLayout_firstTreat();
                break;
            case R.id.btn_basicInfo: // ????????????button
                setButton_basicInfo();
                break;
            case R.id.btn_record_sure: // ??????????????????
                setButton_recordSure();
                break;
            case R.id.tx_ThisTreatDate: // ??????????????????
                setLayout_operationDate(R.id.tx_ThisTreatDate);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        radioInfo(radioGroup);
    }


    // TODO *********************?????????????????? ?????????*****************************
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

        // ??????????????????
        PostMedicalRecordNetRequest postMedicalRecordNetRequest = new PostMedicalRecordNetRequest();
        postMedicalRecordNetRequest.addPostMedicalRecord(medicalRecord,mHandler);
    }

    /**
     * ???????????????button
     */
    private void setButton_basicInfo(){
        //?????????ButtonActivity????????????
        Intent intent = new Intent(PostMedicalRecordActivity.this, BasicInfoActivity.class); //??? A??? ????????? B???
        startActivity(intent);
        Toast.makeText(PostMedicalRecordActivity.this, "????????????", Toast.LENGTH_LONG).show();
    }



    // TODO ***************  ???????????????????????????????????? **********************
    private void setTx_illSelector(){
        //?????????????????????
        final String[] items = {"?????????????????????", "??????????????????", "??????????????????", "????????????", "?????????????????????(????????????)????????????", "??????????????????????????????????????????"};
        choice = -1;
        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("????????????")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(choice != -1){
                            // ???????????????????????????????????????????????????
                            illSelectorOnClickListener(choice);

                            medicalRecord.setMedicalCaseType(String.valueOf(choice));
                            medicalRecord.setMedicalCaseName(items[choice]);

                            tx_illSelector.setText(items[choice]);  //?????????textView???

                            //hashMap.put("illSelector",items[choice]);
                            Toast.makeText(PostMedicalRecordActivity.this, "????????????" + items[choice], Toast.LENGTH_LONG).show();
                        }
                    }
                });
        builder.create().show();
    }



    // TODO ***************************???????????????????????????****************************
    private void setTx_operationMethod(){
        //?????????????????????
        final String[] items = {"????????????", "????????????+?????????????????????","??????+???????????????", "??????+???????????????+???????????????"};
        choice = -1;
        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("????????????")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice = i;
                    }
                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (choice != -1) {
                            medicalRecord.setOperationWay(items[choice]);

                            tx_operationMethod.setText(items[choice]);  //?????????textView???

                            //hashMap.put("operationMethod",items[choice]);
                            Toast.makeText(PostMedicalRecordActivity.this, "????????????" + items[choice], Toast.LENGTH_LONG).show();

                        }
                    }
                });
        builder.create().show();
    }


    // TODO ********************* DTC??????   ????????????????????? ?????????StringBuilder???***********************************
    private void setTx_DTC(){
        DTCDialog dtcDialog;

        View contentView = View.inflate(this,R.layout.dialog_dtc,null);
        //new Dialog(this);
        //?????????????????????dialog
        dtcDialog = new DTCDialog(this,contentView,new int[]{R.id.dtc_lowSick1,R.id.dtc_lowSick2,R.id.dtc_lowSick3,R.id.dtc_lowSick4,R.id.dtc_lowSick5,R.id.dtc_intermediateRisk1,R.id.dtc_intermediateRisk2,R.id.dtc_intermediateRisk3,R.id.dtc_highRisk1,R.id.dtc_highRisk2,R.id.dtc_highRisk3,R.id.dtc_highRisk4,R.id.dtc_highRisk5,R.id.dtc_highRisk6});
        //??????????????????
        dtcDialog.setOnDtcCenterItemClickListener((DTCDialog.OnDtcCenterItemClickListener) this);
        //??????
        dtcDialog.show();
        //??????????????????
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
            medicalRecord.setDtc(str);// ??????????????????TSH???????????????dialog_dtc???

            Toast.makeText(PostMedicalRecordActivity.this,"???????????????"+str, Toast.LENGTH_LONG).show();
            tx_DTC.setText(str);
        }
    }


    // TODO *************************  ??????????????????  ??????StringBuilder???*****************************************
    private void setTx_SideEffects(){
        SideEffectsDialog sideEffectsDialog;

        View contentView = View.inflate(this,R.layout.dialog_side_effects,null);
        //new Dialog(this);
        //?????????????????????dialog
        sideEffectsDialog = new SideEffectsDialog(this,contentView,new int[]{R.id.sideEffect_lowSick1,R.id.sideEffect_lowSick2,R.id.sideEffect_lowSick3,R.id.sideEffect_lowSick4,R.id.sideEffect_lowSick5,R.id.sideEffect_lowSick6,R.id.sideEffect_lowSick7,R.id.sideEffect_lowSick8,R.id.sideEffect_intermediateRisk1,R.id.sideEffect_intermediateRisk2,R.id.sideEffect_intermediateRisk3,R.id.sideEffect_intermediateRisk4,R.id.sideEffect_intermediateRisk5,R.id.sideEffect_intermediateRisk6,R.id.sideEffect_intermediateRisk7,R.id.sideEffect_intermediateRisk8,R.id.sideEffect_highRisk1,R.id.sideEffect_highRisk2,R.id.sideEffect_highRisk3,R.id.sideEffect_highRisk4});
        //??????????????????
        sideEffectsDialog.setOnSideEffectsCenterItemClickListener((SideEffectsDialog.OnSideEffectsCenterItemClickListener)this);
        //??????
        sideEffectsDialog.show();
        //??????????????????
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

            Toast.makeText(PostMedicalRecordActivity.this,"???????????????"+str, Toast.LENGTH_LONG).show();
            tx_negative_risk.setText(str);
        }
    }


    // TODO *********************** TNM??????  (??????StringBuilder???)***********************
    private void setTx_TNM(){
        TNMDialog tnmDialog;
        View contentView = View.inflate(this,R.layout.dialog_tnm,null);
        //?????????????????????dialog, contentView??????R.layout.dialog_tnm
        tnmDialog = new TNMDialog(this,contentView,new int[]{R.id.radio_tnm_T,R.id.radio_tnm_N,R.id.radio_tnm_M,R.id.btn_tnm_sure});
        //??????????????????
        tnmDialog.setOnCenterItemClickListener((TNMDialog.OnCenterItemClickListener) this);
        //??????
        tnmDialog.show();
        //??????????????????
    }
    // TNM?????????Activity????????????
    @Override
    public void OnCenterItemClick(TNMDialog dialog, View view,String str,StringBuilder stringBuilder) {
        if(view.getId() == R.id.btn_tnm_sure){
            hashMap.put("tnm",str);
            //sbDescription.append("dialog_tnm:"+str+";\n");

            Toast.makeText(PostMedicalRecordActivity.this,"???????????????"+stringBuilder.toString(), Toast.LENGTH_LONG).show();
            tx_TNM.setText(stringBuilder.toString());
        }
    }



  // TODO ************************???????????? (??????StringBuilder???)*******************************
    private void setLayout_firstTreat(){
        final List<Integer> choice = new ArrayList<>();

        final String[] items = {"??????????????????","?????????????????????","??????I131?????????","FT3","FT4","TSH",
                            "Tg","TgAb","TRAB","TPOAB","????????????????????????"};
        //??????????????????
        boolean[] isSelect = {false, false, false, false,false, false, false, false, false, false, false};

        builder = new AlertDialog.Builder(PostMedicalRecordActivity.this).setIcon(R.mipmap.ic_launcher)
                .setTitle("????????????")
                .setMultiChoiceItems(items, isSelect, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            choice.add(i);
                        } else {
                            choice.remove(i);
                        }
                    }
                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder str = new StringBuilder();

                        for (int j = 0; j < choice.size(); j++) {
                            str.append(items[choice.get(j)]+";");
                        }

                        tx_firstTreat.setText(str);
                        //sbDescription.append("layout_firstTreat:"+str.toString()+";\n");
                        hashMap.put("firstTreat",str.toString());
                        Toast.makeText(PostMedicalRecordActivity.this, "????????????" + str, Toast.LENGTH_LONG).show();
                    }
                });

        builder.create().show();

    }



    // TODO ******************???????????????pickTime?????????????????????????????????????????????**********************
    private void setLayout_operationDate(int sureLister_id){
        showDatePickDialog(DateType.TYPE_YMD,sureLister_id);
    }
    // ????????????
    private void showDatePickDialog(DateType type,int sureLister_id) {
        DatePickDialog dialog = new DatePickDialog(this);
        //????????????????????????
        dialog.setYearLimt(5);
        //????????????
        dialog.setTitle("????????????");
        //????????????
        dialog.setType(type);
        //?????????????????????????????????????????????
        dialog.setMessageFormat("yyyy-MM-dd");
        //??????????????????
        dialog.setOnChangeLisener(this);
        //??????????????????????????????
        dialog.setOnSureLisener(this,sureLister_id);
        dialog.show();
    }
    // ?????????????????????????????????
    @Override
    public void onChanged(Date date) {

    }
    // ?????????????????????????????????
    @Override
    public void onSure(Date date,int sureLister_id) {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);

        switch(sureLister_id){
            // ????????????
            case R.id.tx_operationDate:
                tx_operationDate.setText(dateStr);
                medicalRecord.setOperationDate(dateStr);

                Toast.makeText(PostMedicalRecordActivity.this,"?????????????????????"+dateStr,Toast.LENGTH_LONG).show();
                break;
            // ??????????????????
            case R.id.tx_firstTreatDate:
                tx_firstTreatDate.setText(dateStr);
                hashMap.put("firstTreatDate",dateStr);
                //sbDescription.append("firstTreatDate:"+dateStr2+";\n");

                Toast.makeText(PostMedicalRecordActivity.this,"?????????????????????"+dateStr,Toast.LENGTH_LONG).show();
                break;
            case R.id.tx_ThisTreatDate:
                tx_thisTreatDate.setText(dateStr);
                hashMap.put("thisTreatDate",dateStr);

                Toast.makeText(PostMedicalRecordActivity.this,"?????????????????????"+dateStr,Toast.LENGTH_LONG).show();
                break;
        }

    }


    /**
     * TODO ***************????????????????????????????????????????????????***********************
     * @param type ???type=0??????????????????xxx??????
     */
    public void illSelectorOnClickListener(int type){
        switch (type){
            case 0:{ caseZeroOne(); break; } // ?????????????????????
            case 1:{ caseZeroOne(); break; } // ??????????????????
            case 2:{ caseTwo(); break; } // ??????????????????
            case 3:{ caseThree(); break; } //????????????
            case 4:{ caseFour(); break; } //????????????
            case 5:{ caseFive(); break; } // ??????????????????????????????
        }
    }



    // TODO *****************???????????????***********************
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

        // ??????????????????????????????
        tx_illSelector.setOnClickListener(this);
        // ????????????????????????
        tx_operationMethod.setOnClickListener(this);
        // ???????????????pickTime
        //ButterKnife.bind(this);
        tx_operationDate.setOnClickListener(this);
        // TNM??????
        tx_TNM.setOnClickListener(this);
        tx_DTC.setOnClickListener(this); // dialog_dtc
        tx_negative_risk.setOnClickListener(this);
        // ??????????????????
        tx_firstTreatDate.setOnClickListener(this);
        // ????????????
        tx_firstTreat.setOnClickListener(this);
        // ??????????????????
        tx_firstTreatDate.setOnClickListener(this);
        // ??????????????????
        tx_thisTreatDate.setOnClickListener(this);

        // ????????????buttion
        btn_basicInfo.setOnClickListener(this);
        btn_record_sure.setOnClickListener(this);

        radio_centerLymphMove = findViewById(R.id.radio_centerLymphMove);
        radio_neckLymphMove = findViewById(R.id.radio_neckLymphMove);
        radio_distantMove = findViewById(R.id.radio_distantMove);
        radio_I131treat = findViewById(R.id.radio_I131treat);
        radio_treatMethod = findViewById(R.id.radio_treatMethod);
        radio_AfterThreatMethod = findViewById(R.id.radio_AfterThreatMethod);
        radio_thyroidRestrainTreat = findViewById(R.id.radio_thyroidRestrainTreat);

        radio_centerLymphMove.setOnCheckedChangeListener(this); // ?????????????????????(?????????)
        radio_neckLymphMove.setOnCheckedChangeListener(this); // ????????????????????????????????????
        radio_distantMove.setOnCheckedChangeListener(this); // ????????????
        radio_I131treat.setOnCheckedChangeListener(this); // I131??????
        radio_treatMethod.setOnCheckedChangeListener(this); // ????????????
        radio_AfterThreatMethod.setOnCheckedChangeListener(this); // ??????????????????
        radio_thyroidRestrainTreat.setOnCheckedChangeListener(this); //????????????????????????
    }


    final Handler mHandler = new Handler(){
        // ???????????????????????????
        public void handleMessage (Message msg) {
            switch(msg.what) {
                case Code.MESSAGE_OK:
                    InsertGson gson = (InsertGson) msg.obj;
                    if(gson.getMsg().equals("ok")){
                        Toast.makeText(PostMedicalRecordActivity.this, "????????????", Toast.LENGTH_LONG).show();
                    }
                    Log.d("PostMedicalRecordNetRequest", "msg:" + msg.obj);
                    break;
            }
        }
    };

    // TODO ??????????????? radioGroup??????
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


    // TODO ???????????????????????????????????????????????????
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
        tx_operationDate.setText("??????>");
        layout_operationMethod.setVisibility(View.GONE);
        medicalRecord.setOperationWay("");
        tx_operationMethod.setText("??????>");

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



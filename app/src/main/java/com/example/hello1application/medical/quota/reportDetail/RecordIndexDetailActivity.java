package com.example.hello1application.medical.quota.reportDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.hello1application.R;
import com.example.hello1application.medical.quota.ruleViewDemo.AllRulerCallback;
import com.example.hello1application.medical.quota.ruleViewDemo.RulerView;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

public class RecordIndexDetailActivity extends AppCompatActivity
        implements View.OnClickListener, OnChangeLisener, OnSureLisener, AdapterView.OnItemSelectedListener {

    private TextView tvhorizontal;
    private RulerView horizontalRullView;
    private TextView tv_recordIndex_name;
    private Button btn_assayDate;
    private Button btn_recordConfirm;
    private String dateStr = null;
    private String unitStr = null;
    private Spinner spinner_index_unit;
    private EditText et_index;
    private EditText et_minIndex;
    private EditText et_maxIndex;
    private int reportDetail_type = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_index_detail);

        //ButterKnife.bind(this); 为啥取消了就可以跳转了
        initData();
    }

    private void initData(){

        tv_recordIndex_name = findViewById(R.id.tv_recordIndex_name);
        btn_assayDate = findViewById(R.id.btn_assayDate);
        btn_assayDate.setOnClickListener(this);

        btn_recordConfirm = findViewById(R.id.btn_recordConfirm);
        btn_recordConfirm.setOnClickListener(this);

        spinner_index_unit =findViewById(R.id.spinner_index_unit);
        spinner_index_unit.setOnItemSelectedListener(this);

        et_index = findViewById(R.id.et_index);
        et_minIndex = findViewById(R.id.et_minIndex);
        et_maxIndex = findViewById(R.id.et_maxIndex);

        Intent intent = getIntent ();
        reportDetail_type = intent.getExtras().getInt("reportDetail_type");
        tv_recordIndex_name.setText(intent.getExtras().getString("reportDetail_name"));
    }

    /**
     * 该Activity的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            // 选择化验日期
            case R.id.btn_assayDate:
                setLayout_operationDate(R.id.btn_assayDate);
                break;
            // 选择"确定"按钮
            case R.id.btn_recordConfirm:
                getDateAndIndex();
                break;
        }
    }


    /**
     * *****************************日期选择，pickTime控件****************************************
     */
    private void setLayout_operationDate(int sureLister_id){
        showDatePickDialog(DateType.TYPE_YMD,sureLister_id);
    }
    /**
     * pickTime 日期控件
     * @param type
     */
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
    /***
     * 日期控件的“改变”监听
     * @param date
     */
    @Override
    public void onChanged(Date date) {

    }
    /***
     * 日期控件的“确定”监听
     * @param date
     * @param sureLister_id
     */
    @Override
    public void onSure(Date date,int sureLister_id) {
        dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);

        switch(sureLister_id){
            case R.id.btn_assayDate:
                if(dateStr != null){
                    btn_assayDate.setText(dateStr);
                    Toast.makeText(RecordIndexDetailActivity.this,"你选择的日期是"+dateStr,Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    /**
     * TODO ③ 回调过程3，(C)
     * TODO **************点击“确定”后返回IndexDetailsActivity，并传递数据，执行IndexDetailsActivity的onActivityResult()方法*************************************
     */
    public void getDateAndIndex(){
        String editIndex = et_index.getText().toString();
        String editMinIndex = et_minIndex.getText().toString();
        String editMaxIndex = et_maxIndex.getText().toString();

        boolean flag = true;
        if("".equals(dateStr)){
            // 提示日期未选择
            Toast.makeText(RecordIndexDetailActivity.this,"未选择日期",Toast.LENGTH_LONG).show();
            flag = false;
        }
        if("".equals(editIndex)||"".equals(unitStr)){
            // 提示指标或单位未填写
            Toast.makeText(RecordIndexDetailActivity.this,"未填写指标或单位",Toast.LENGTH_LONG).show();
            flag = false;
        }
        if("".equals(editMinIndex) || "".equals(editMaxIndex)){
            // 提示标准值区间未填写
            Toast.makeText(RecordIndexDetailActivity.this,"未填写标准值",Toast.LENGTH_LONG).show();
            flag = false;
        }

        if(flag == true){
            btn_recordConfirm.setEnabled(true);

            Bundle bundle = new Bundle();
            bundle.putString("recordIndex_dateStr",dateStr);
            bundle.putString("recordIndex_indexStr",editIndex);
            bundle.putString("unitStr",unitStr);
            bundle.putString("editMinIndex",editMinIndex);
            bundle.putString("editMaxIndex",editMaxIndex);

            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
        if(flag == false)
            btn_recordConfirm.setEnabled(false);
    }



    /**
     * **************************Spinner下拉选择框************************************
     */
    public void getSpinnerInfo(){
        //String[] arr = {"",""};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item);
        spinner_index_unit.setAdapter(adapter);
        spinner_index_unit.setOnItemSelectedListener(this);
    }
    /**
     * @param adapterView
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String content = adapterView.getItemAtPosition(position).toString();
        switch (adapterView.getId()){
            case R.id.spinner_index_unit:
                unitStr = content;
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /***
     * 废弃代码，刻度尺选择数据
         private void initRuleView() {
         tvhorizontal = (TextView)findViewById(R.id.tv_horizontal);
         horizontalRullView = (RulerView)findViewById(R.id.rulerView_horizontal);

         horizontalRullView.setMin(0);     //设置刻度尺最小值
         horizontalRullView.setMax(30);   //设置刻度尺最大值
         horizontalRullView.setInterval(10);    //设置刻度尺的间距
         horizontalRullView.setNumber(12);     //设置刻度尺第一次显示的位置
         horizontalRullView.setTextOffset(10);     //根据显示的数字自主调节刻度尺数字的左右位置

         horizontalRullView.setRuleListener(new AllRulerCallback() {
        @Override
        public void onRulerSelected(int length, int value) {
        indexStr = String.valueOf(value);
        //获得即时显示的数字
        tvhorizontal.setText(String.valueOf(value));
        }
        });
     }**/
}

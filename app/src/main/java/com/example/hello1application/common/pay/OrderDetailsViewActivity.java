package com.example.hello1application.common.pay;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hello1application.R;

public class OrderDetailsViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvBody;
    TextView tvSubject;
    TextView tvGoodNumber;
    TextView tvMoney;
    TextView tvProductCode;
    Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_view);

        initView();
    }

    private void initView() {
        tvBody = findViewById(R.id.order_body);
        tvSubject = findViewById(R.id.order_subject);
        tvGoodNumber = findViewById(R.id.goods_number);
        tvMoney = findViewById(R.id.goods_money);
        tvProductCode = findViewById(R.id.goods_productCode);
        btConfirm = findViewById(R.id.bt_confirm);

        btConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_confirm:
                // 点击确认
                //Intent intent = new Intent(OrderDetailsViewActivity.this, JpayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("body",tvBody.getText().toString());
                bundle.putString("subject",tvSubject.getText().toString());
                bundle.putString("total_amount",tvMoney.getText().toString());
                bundle.putString("product_code",tvProductCode.getText().toString());
                //intent.putExtras(bundle);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }
}

package com.example.hello1application.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.hello1application.R;

public class AActivity extends AppCompatActivity {

    private AFragment afragment;
    private Button btn_aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        // 实例化AActivity
        afragment = AFragment.newInstance("zhan");
        // 把AAFragment添加到AActivity
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.frame_id1 ,afragment).
                commit();

    }
}

package com.example.xiarui.trueorfalse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_load;
    private EditText edt_id;
    private EditText edt_password;
    private TextView tv_xh;
    private TextView tv_password;
    private LinearLayout ll_x;
    private LinearLayout ll_xh;
    private LinearLayout ll_mima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        //隐藏标题栏
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }


        init();


        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edt_id.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if(id.equals("1710211059")&&password.equals("111111")){
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    edt_id.setText("");
                    edt_password.setText("");
                }
            }
        });

    }

    private void init(){
        btn_load = findViewById(R.id.btn_load);
        edt_id = findViewById(R.id.editText1);
        edt_password = findViewById(R.id.editText2);
        tv_xh = findViewById(R.id.textView2);
        tv_password = findViewById(R.id.textView3);
        ll_x = findViewById(R.id.linearLayout);
        ll_xh = findViewById(R.id.ll_xh);
        ll_mima = findViewById(R.id.ll_mima);

        //设置部件透明
        ll_xh.setBackgroundColor(Color.TRANSPARENT);
        ll_mima.setBackgroundColor(Color.TRANSPARENT);
        tv_xh.setBackgroundColor(Color.TRANSPARENT);
        tv_password.setBackgroundColor(Color.TRANSPARENT);
        ll_x.setBackgroundColor(Color.TRANSPARENT);
        edt_id.setBackgroundColor(Color.TRANSPARENT);
        edt_password.setBackgroundColor(Color.TRANSPARENT);
        btn_load.setBackgroundColor(Color.TRANSPARENT);

    }

}

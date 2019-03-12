package com.example.xiarui.trueorfalse;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.GestureDetector.*;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener,OnGestureListener {
    private MyHelper myHelper;
    private SQLiteDatabase db;
    private TextView textShow;
    private Riddle thisRiddle;
    private int index;
    private  List<Riddle> riddles;
    private Button btn_true;
    private Button btn_false;
    private Button btn_pu;
    private Button btn_pd;
    private Button btn_show;
    private int[] clickable = new int[10];
    private int score = 0;
    private TextView tv_score;
    private GestureDetector detector;  //手势管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        //创建手势管理器
        detector = new GestureDetector(this);

        init();


        myHelper = new MyHelper(this);
        db = myHelper.getReadableDatabase();

        Cursor cursor = db.query("riddle",null,null,null,
                        null,null,null,null);
        riddles = getRiddlesByCursor(cursor);   //   获取问题集合。
        cursor.close();    //关闭游标
        db.close();   //关闭数据库

        thisRiddle  = riddles.get(index);    //获取当前下标的题目对象
        textShow.setText(thisRiddle.getId()+". "+thisRiddle.getQuestion());

        tv_score.setText(score+"");

    }

    private void init(){


        textShow = findViewById(R.id.textShow);
        btn_pu = findViewById(R.id.btn_pu);
        btn_pd = findViewById(R.id.btn_pd);
        btn_true = findViewById(R.id.btn_true);
        btn_false = findViewById(R.id.btn_false);
        btn_show = findViewById(R.id.btn_show);
        tv_score = findViewById(R.id.tv_score);



        //添加动作监听器
        btn_pu.setOnClickListener(this);
        btn_pd.setOnClickListener(this);
        btn_true.setOnClickListener(this);
        btn_false.setOnClickListener(this);
        btn_show.setOnClickListener(this);


        //设置Button的背景颜色
        //int c1 = Color.rgb(200,200,200);
        btn_pu.setBackgroundColor(Color.TRANSPARENT);
        btn_pd.setBackgroundColor(Color.TRANSPARENT);
        btn_true.setBackgroundColor(Color.TRANSPARENT);
        btn_false.setBackgroundColor(Color.TRANSPARENT);
        btn_show.setBackgroundColor(Color.TRANSPARENT);

    }



    public List<Riddle> getRiddlesByCursor(Cursor cursor){
        List<Riddle> riddles;
        if(cursor.getCount() == 0){
            return null;
        }else{
            cursor.moveToFirst();
            Riddle riddle = new Riddle();
            riddle.setId(cursor.getString(cursor.getColumnIndex("_id")));
            riddle.setQuestion(cursor.getString(1));
            riddle.setAnswer(cursor.getString(2));
            riddles = new ArrayList<Riddle>();
            riddles.add(riddle);
        }
        while(cursor.moveToNext()){
            Riddle riddle = new Riddle();
            riddle.setId(cursor.getString(cursor.getColumnIndex("_id")));
            riddle.setQuestion(cursor.getString(1));
            riddle.setAnswer(cursor.getString(2));
            riddles.add(riddle);
        }
        return riddles;
    }

    @Override
    public void onClick(View view) {
       switch(view.getId()){
           case R.id.btn_pu:
               if(index>0){
                   index--;
                   thisRiddle = riddles.get(index);
                   textShow.setText(index+1+". "+thisRiddle.getQuestion());
                   setClickable();  //判断是否可以点击并设置
               }else{
                   Toast toast = Toast.makeText(Main2Activity.this,R.string.NoLast,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
               }
               break;
           case R.id.btn_pd:
               if(index<9){
                   index++;
                   thisRiddle  = riddles.get(index);
                   textShow.setText(index+1+". "+thisRiddle.getQuestion());

                   setClickable();  //判断是否可以点击并设置
               }else{
                   Toast toast = Toast.makeText(Main2Activity.this,R.string.NoNext,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
               }
               break;
           case R.id.btn_true:
               if(thisRiddle.getAnswer().equals("1")){
                        //回答正确
                   Toast toast = Toast.makeText(Main2Activity.this,R.string.CorrectAnswer,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
                   score+=10;
                   tv_score.setText(score+"");
                   //正确
               }else{
                                                                     //回答错误
                   Toast toast = Toast.makeText(Main2Activity.this,R.string.WrongAnswer,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
                   //错误
               }

               clickable[index] = 1;
               setClickable();  //判断是否可以点击并设置
               break;
           case R.id.btn_false:
               if(thisRiddle.getAnswer().equals("0")){

                   Toast toast = Toast.makeText(Main2Activity.this,R.string.CorrectAnswer,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
                    score+=10;
                    tv_score.setText(score+"");
                   //正确
               }else{

                   Toast toast = Toast.makeText(Main2Activity.this,R.string.WrongAnswer,Toast.LENGTH_LONG);
                   showMyToast(toast,1000);
                   //错误
               }

               clickable[index] = 1;
               setClickable();  //判断是否可以点击并设置
               break;

           case R.id.btn_show:
               int k = 0;
               for(int i : clickable){
                   if(i==1){
                        k++;
                   }
               }

               textShow.setText("答题数："+k+"/10 , 当前得分："+score);

               //-------------
               //设置不可点击
               int color =  Color.TRANSPARENT;
               btn_true.setClickable(false);
               // btn_true.setBackgroundColor(Color.rgb(96,96,96));
               btn_true.setTextColor(color);
               btn_false.setClickable(false);
               btn_false.setTextColor(color);
               //--------------
       }
    }

    //判断按钮是否可以点击，并设置其是否能点击
    private void setClickable(){
        if(clickable[index] == 1){

            //   ==1表示已经回答过了，不可被点击

            int color = Color.TRANSPARENT;
            btn_true.setClickable(false);
           // btn_true.setBackgroundColor(Color.rgb(96,96,96));
            btn_true.setTextColor(color);
            btn_false.setClickable(false);
            btn_false.setTextColor(color);
           // btn_false.setBackgroundColor(Color.rgb(96,96,96));

        }else{

            int color =  Color.rgb(0,0,0);
            btn_true.setClickable(true);
          //  btn_true.setBackgroundColor(Color.rgb(200,200,200));
            btn_false.setClickable(true);
            //btn_false.setBackgroundColor(Color.rgb(200,200,200));

            btn_true.setTextColor(color);

            btn_false.setTextColor(color);
        }
    }

    //设置Toast的时间
    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        //从右往左移动
        if(motionEvent.getX()-motionEvent1.getX()>150){
            //  下一条
           btn_pd.performClick();

        }
        //从走往右移动
        //  上一条
        if(motionEvent.getX()-motionEvent1.getX()<-150){
            btn_pu.performClick();
        }
        return false;
    }

}

package com.milkywaylhy.ex33thread2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        //현재시간을 5초마다 계속 Toast 로 출력
        thread= new MyThread();
        thread.start(); //자동 run()발동함.
    }

    class MyThread extends Thread{

        boolean isRun=true;

        @Override
        public void run() {
            while (isRun){

                //현재시간을 가지고 있는 객체 생성
                Date now= new Date();
                String s= now.toString();

                //별도 Thread는 UI 작업을 할 수 없음
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread(){
            isRun=false;
        }
    }



    //액티비티가 메모리에서 없어질때 자동으로 호출되는
    //콜백메소드 [onCreate()의 정반대]
    @Override
    protected void onDestroy() {

        //Thread 종료
        //thread.stop();//error 강제종료는 금지!!
        //Thread를 멈추는 것은 run()메소드가 종료되면 됨
        //우리 코드에서는 while()문 때문에 run()이
        //안 끝나는 것임..
        //while문의 조건값이 false가 되면 while이 끝나고
        //더불어 run()도 종료됨..
        //thread.isRun= false;
        //객체지향은 객체 멤버는 객체 스스로 변경하도록..
        thread.stopThread();

        super.onDestroy();
    }


}
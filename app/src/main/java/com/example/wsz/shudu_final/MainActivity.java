package com.example.wsz.shudu_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

   // private  Button bt_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
        /*bt_start=(Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
                                @Override
                           public void onClick(View view) {
                                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                   startActivity(intent);
                     }
                     }
       );*/
    }
}

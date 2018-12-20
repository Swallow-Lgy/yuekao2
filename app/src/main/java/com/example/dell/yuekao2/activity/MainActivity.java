package com.example.dell.yuekao2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.view.LayoutViewGroup;
import com.example.dell.yuekao2.view.TitleBar;

public class MainActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private LayoutViewGroup layoutViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleBar = findViewById(R.id.titlebar);
         layoutViewGroup = findViewById(R.id.layout);
         titleBar.setOnBouttomClick(new TitleBar.onButonClick() {
           @Override
          public void onButtonClickLister(String str) {
               TextView tv = new TextView(MainActivity.this);
               tv.setTextSize(20);
               tv.setText(str);
               layoutViewGroup.addView(tv);
           }
       });
         titleBar.setOnClick(new TitleBar.onButon() {
             @Override
             public void onClick() {
                 Intent intent = new Intent(MainActivity.this,LoaginActivity.class);
                 startActivity(intent);
             }
         });
    }
}

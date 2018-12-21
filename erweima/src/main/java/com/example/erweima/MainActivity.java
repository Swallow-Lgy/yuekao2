package com.example.erweima;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class MainActivity extends AppCompatActivity {
    private Button saoQR,creatQR;
    private EditText editText;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saoQR = findViewById(R.id.saoQR);
        creatQR = findViewById(R.id.creatQR);
        editText = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        saoQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoaginActivity.class);
                startActivity(intent);
            }
        });
       creatQR.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sc();
           }
       });
    }
    public  void sc(){
           QRTask qrTask = new QRTask(this,imageView,editText);
           qrTask.execute(editText.getText().toString());
    }
    static class QRTask extends AsyncTask<String,Void,Bitmap>{
        private WeakReference<Context> mContext;
        private WeakReference<ImageView> mimageView;
        public QRTask(Context context,ImageView imageView,EditText ed){
            mContext = new WeakReference<>(context);
            mimageView=new WeakReference<>(imageView);
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            int size = mContext.get().getResources().getDimensionPixelOffset(R.dimen.ss);

             return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                mimageView.get().setImageBitmap(bitmap);
            }
            else {
                Toast.makeText(mContext.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

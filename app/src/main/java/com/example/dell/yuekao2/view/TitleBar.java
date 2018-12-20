package com.example.dell.yuekao2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dell.yuekao2.R;

public class TitleBar extends LinearLayout {
    private Context mContext;
    public TitleBar(Context context) {
        super(context);
        mContext = context;
        init();
    }
    public TitleBar(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    private void init() {
        final View view = View.inflate(mContext, R.layout.titlebar,null);
        final EditText editText =  view.findViewById(R.id.titleedit);
        view.findViewById(R.id.titlefind).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (monButonClick!=null){
                     monButonClick.onButtonClickLister(editText.getText().toString());
                 }
            }
        });
        view.findViewById(R.id.textgo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monButon!=null){
                    monButon.onClick();
                }
            }
        });
        addView(view);
    }
    onButonClick monButonClick;
    public void setOnBouttomClick(onButonClick onBouttomClick){
        monButonClick=onBouttomClick;
    }
    public interface onButonClick{
        void onButtonClickLister(String str);
    }
    onButon monButon;
    public void setOnClick(onButon onClick){
        monButon = onClick;
    };
    public interface onButon{
        void onClick();
    }
}

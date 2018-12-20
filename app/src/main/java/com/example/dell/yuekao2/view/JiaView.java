package com.example.dell.yuekao2.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.adpter.MyCarGoodsAdpter;
import com.example.dell.yuekao2.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class JiaView extends LinearLayout implements View.OnClickListener  {
    private EditText editText;
    public JiaView(Context context) {
        super(context);
        init(context);
    }

    public JiaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JiaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;
    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.jiaitem,null);
        ImageView jia=view.findViewById(R.id.jia);
        jia.setOnClickListener(this);
        ImageView jian=view.findViewById(R.id.jian);
        jian.setOnClickListener(this);
        editText = view.findViewById(R.id.jsedit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                Integer integer = Integer.valueOf(str);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addView(view);
    }
    private int num;
    private List<ShopCarBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private MyCarGoodsAdpter carGoodsAdpter;
    public void setData(MyCarGoodsAdpter myCarGoodsAdpter,List<ShopCarBean.DataBean.ListBean>list,int i){
            this.list=list;
            this.position=i;
            this.carGoodsAdpter=myCarGoodsAdpter;
            num=list.get(i).getNum();
            editText.setText(num+"");
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case  R.id.jia:
                num++;
                editText.setText(num + "");
                list.get(position).setNum(num);
                monCall.onCallBack();
                carGoodsAdpter.notifyDataSetChanged();
                break;
            case R.id.jian:
                if (num>1){
                    num--;
                }
                else {
                    Toast.makeText(context,"我是有底线的",Toast.LENGTH_SHORT);
                }

                editText.setText(num+"");
                list.get(position).setNum(num);
                monCall.onCallBack();
                carGoodsAdpter.notifyDataSetChanged();
                break;
                default:
                    break;
        }

    }
    OnCall monCall;
    public void setOnCallBack(OnCall onCall){
        monCall=onCall;
    }
    public interface  OnCall{
        void onCallBack();
    }
}

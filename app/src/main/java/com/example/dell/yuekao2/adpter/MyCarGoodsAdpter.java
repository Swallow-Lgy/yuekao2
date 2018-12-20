package com.example.dell.yuekao2.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.bean.GoodsBean;
import com.example.dell.yuekao2.bean.ShopCarBean;
import com.example.dell.yuekao2.view.JiaView;

import java.util.ArrayList;
import java.util.List;

public class MyCarGoodsAdpter extends RecyclerView.Adapter<MyCarGoodsAdpter.MyViewHolder>  {
    private List<ShopCarBean.DataBean.ListBean> mList;
    private Context mContext;

    public MyCarGoodsAdpter(List<ShopCarBean.DataBean.ListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.cargoodsitem,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        String images = mList.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(mContext).load(split[0]).into(myViewHolder.imageView);
        myViewHolder.price.setText(mList.get(i).getPrice()+"");
        myViewHolder.title.setText(mList.get(i).getTitle());
        myViewHolder.checkBox.setChecked(mList.get(i).isCheck());
        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //给自己改变状态
                mList.get(i).setCheck(isChecked);
                if (monCheck!=null){
                    monCheck.onCheckClick();
                }
            }
        });
        myViewHolder.jiaView.setData(this,mList,i);
        myViewHolder.jiaView.setOnCallBack(new JiaView.OnCall() {
            @Override
            public void onCallBack() {
                if (monCheck!=null){
                    monCheck.onCheckClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
      private CheckBox checkBox;
      private TextView title,price;
      private ImageView imageView;
      private JiaView jiaView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.spcheck);
            title = itemView.findViewById(R.id.spname);
            price = itemView.findViewById(R.id.spprice);
            imageView = itemView.findViewById(R.id.spimage);
            jiaView = itemView.findViewById(R.id.jiaview);
        }
    }
    public void selectOrRemoveAll(boolean check){
        for (ShopCarBean.DataBean.ListBean listBean :mList){
            listBean.setCheck(check);
        }
        notifyDataSetChanged();
    }
    onCheck monCheck;
    public  void setOnCheckLister(onCheck onCheck){
        monCheck=onCheck;
    }
    public interface onCheck{
        void onCheckClick();
    }
}

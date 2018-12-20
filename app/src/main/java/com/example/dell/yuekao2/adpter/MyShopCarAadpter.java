package com.example.dell.yuekao2.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

public class MyShopCarAadpter extends RecyclerView.Adapter<MyShopCarAadpter.ViewHolder> {
     private List<ShopCarBean.DataBean> mList;
     private Context mContext;

    public MyShopCarAadpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<ShopCarBean.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.merchantitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
         viewHolder.textView.setText(mList.get(i).getSellerName());
        final MyCarGoodsAdpter myCarGoodsAdpter = new MyCarGoodsAdpter(mList.get(i).getList(), mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
        viewHolder.recyclerView.setAdapter(myCarGoodsAdpter);
        viewHolder.checkBox.setChecked(mList.get(i).isCheck());
        myCarGoodsAdpter.setOnCheckLister(new MyCarGoodsAdpter.onCheck() {
            @Override
            public void onCheckClick() {
                if (monClick!=null){
                    monClick.onCheckClick(mList);
                }
                List<ShopCarBean.DataBean.ListBean> listBeans = mList.get(i).getList();
                boolean isAllCheck=true;
                for (ShopCarBean.DataBean.ListBean bean:listBeans){
                    if (!bean.isCheck()){
                        isAllCheck=false;
                        break;
                    }
                }
                viewHolder.checkBox.setChecked(isAllCheck);
                mList.get(i).setCheck(isAllCheck);
            }
        });
        //点击事件
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(i).setCheck(viewHolder.checkBox.isChecked());
               myCarGoodsAdpter.selectOrRemoveAll(viewHolder.checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView textView;
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.merchant);
            recyclerView = itemView.findViewById(R.id.merchantrecycler);
            checkBox = itemView.findViewById(R.id.merchantcheck);
        }
    }
    onClick monClick;
    public  void setOnClickLister(onClick onClick){
        monClick=onClick;
    }
    public interface onClick{
        void onCheckClick(List<ShopCarBean.DataBean> list);
    }
}

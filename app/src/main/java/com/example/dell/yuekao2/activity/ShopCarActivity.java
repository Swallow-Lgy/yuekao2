package com.example.dell.yuekao2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.adpter.MyCarGoodsAdpter;
import com.example.dell.yuekao2.adpter.MyShopCarAadpter;
import com.example.dell.yuekao2.bean.ShopCarBean;
import com.example.dell.yuekao2.presenter.IPresenterImpl;
import com.example.dell.yuekao2.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopCarActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private MyShopCarAadpter shopCarAadpter;
    private IPresenterImpl miPresenter;
    private String url="http://www.zhaoapi.cn/product/getCarts";
    private RecyclerView recyclerView;
    private Button sum;
    private TextView total;
    private CheckBox selecAll;
    private List<ShopCarBean.DataBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        recyclerView = findViewById(R.id.shoprecycle);
        total = findViewById(R.id.total);
        sum = findViewById(R.id.sum);
        selecAll = findViewById(R.id.selectAll);
        selecAll.setOnClickListener(this);
        shopCarAadpter = new MyShopCarAadpter(this);
        miPresenter = new IPresenterImpl(this);
        init();
        shopCarAadpter.setOnClickLister(new MyShopCarAadpter.onClick() {
            @Override
            public void onCheckClick(List<ShopCarBean.DataBean> list) {
                double totalPrice=0;
                int num=0;
                int totalnum=0;
                for (int a=0;a<list.size();a++){
                    List<ShopCarBean.DataBean.ListBean> listAll = list.get(a).getList();
                   for (int i=0;i<listAll.size();i++){
                       totalnum=totalnum+listAll.get(i).getNum();
                       if (listAll.get(i).isCheck()){
                           totalPrice= totalPrice+listAll.get(i).getNum()*listAll.get(i).getPrice();
                           num=num+listAll.get(i).getNum();
                       }
                   }
                }
                if (num<totalnum){
                    selecAll.setChecked(false);
                }
                else {
                    selecAll.setChecked(true);
                }
                total.setText("合计:"+totalPrice);
                sum.setText("去结算("+num+")");
            }

        });
    }
    private void getData() {
        HashMap<String,String>map = new HashMap<>();
        map.put("uid","23421");
        miPresenter.requestData(url,map,ShopCarBean.class);
    }
    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(shopCarAadpter);
        getData();
    }



    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.selectAll:
                      checkSeleck(selecAll.isChecked());
                      shopCarAadpter.notifyDataSetChanged();
                   break;
                   default:
                       break;
           }
    }
   public void checkSeleck(boolean check){
          double totalPrice=0;
          int num=0;
          for (int a=0;a<mList.size();a++){
              ShopCarBean.DataBean dataBean  =  mList.get(a);
              dataBean.setCheck(check);
              List<ShopCarBean.DataBean.ListBean> list = mList.get(a).getList();
              for (int i=0;i<list.size();i++){
                  list.get(i).setCheck(check);
                  totalPrice = totalPrice+(list.get(i).getNum()*list.get(i).getPrice());
                   num = num+list.get(i).getNum();
              }
          }
          if (check){
              total.setText("合计:"+totalPrice);
              sum.setText("去计算("+num+")");
          }
          else {
              total.setText("合计:0.000");
              sum.setText("去结算(0)");
          }
   }
    @Override
    public void success(Object data) {
        if (data instanceof ShopCarBean){
            ShopCarBean carBean = (ShopCarBean) data;
            mList = carBean.getData();
           shopCarAadpter.setmList(mList);
        }
    }
}

package com.example.dell.yuekao2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.adpter.MyGoogsAdpter;
import com.example.dell.yuekao2.bean.AddCarBean;
import com.example.dell.yuekao2.bean.GoodsBean;
import com.example.dell.yuekao2.presenter.IPresenterImpl;
import com.example.dell.yuekao2.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;

public class LoaginActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private XRecyclerView xRecyclerView;
    private IPresenterImpl miPresenter;
    private MyGoogsAdpter googsAdpter;
    private String url="http://www.zhaoapi.cn/product/searchProducts";
    private String urlCar="http://www.zhaoapi.cn/product/addCart";
    private int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loagin);
        xRecyclerView = findViewById(R.id.xrecycle);
        miPresenter = new IPresenterImpl(this);
        googsAdpter = new MyGoogsAdpter(this);
        init();
        //加入购物车
        googsAdpter.setOnCarClickLister(new MyGoogsAdpter.onCarClick() {
            @Override
            public void OnCarClickLister(int pid) {
                loaData(pid);
                Toast.makeText(LoaginActivity.this,"加入成功",Toast.LENGTH_LONG).show();
            }
        });
        googsAdpter.setOnButtonClick(new MyGoogsAdpter.onButClick() {
            @Override
            public void onButtonClick() {
                Intent intent = new Intent(LoaginActivity.this,ShopCarActivity.class);
                startActivity(intent);
            }
        });
        googsAdpter.setOnLongClickLister(new MyGoogsAdpter.longClick() {
            @Override
            public void longClickLister(View view, int position) {
                googsAdpter.del(view,position);
            }
        });
    }
      public void loaData(int id){
        HashMap<String,String>map = new HashMap<>();
        map.put("uid","23421");
        map.put("pid",id+"");
        miPresenter.requestData(urlCar,map,AddCarBean.class);
      }
    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setAdapter(googsAdpter);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                 page=1;
                 getData();
                 xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                  getData();
                  xRecyclerView.loadMoreComplete();
            }
        });
        getData();
    }
     public void getData(){
         HashMap<String,String> map = new HashMap<>();
         map.put("keywords","手机");
         map.put("page",page+"");
         miPresenter.requestData(url,map,GoodsBean.class);
      }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void success(Object data) {
          if (data instanceof GoodsBean){
              GoodsBean bean = (GoodsBean) data;
              if (page==1){
                  googsAdpter.setmList(bean.getData());
              }
              else {
                  googsAdpter.admList(bean.getData());
              }
              page++;
          }
    }
}

package com.example.dell.yuekao2.adpter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.yuekao2.R;
import com.example.dell.yuekao2.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class MyGoogsAdpter extends RecyclerView.Adapter<MyGoogsAdpter.ViewHolder> {
    private List<GoodsBean.DataBean> mList;
    private Context mContext;

    public MyGoogsAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    public void setmList(List<GoodsBean.DataBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void admList(List<GoodsBean.DataBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.goodsitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String images = mList.get(i).getImages();
        String[] split = images.split("\\|");
        Glide.with(mContext).load(split[0]).into(viewHolder.icon);
        viewHolder.title.setText(mList.get(i).getTitle());
        viewHolder.price.setText(mList.get(i).getPrice()+"");
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monCarClick!=null){
                    monCarClick.OnCarClickLister(mList.get(i).getPid());
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (monButClick!=null){
                     monButClick.onButtonClick();
                 }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClick!=null){
                    mLongClick.longClickLister(v,i);
                }
                return true;
            }
        });
    }
   public int getId(int posions){
       int pid = mList.get(posions).getPid();
       return pid;
   }
   public void del(final View view , final int i){
        final float f = view.getX();
          ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationX",0,500);
          animator.setDuration(1000);
          animator.start();
          animator.addListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {
                  mList.remove(i);
                  notifyDataSetChanged();
                  view.setX(f);
              }

              @Override
              public void onAnimationEnd(Animator animation) {

              }

              @Override
              public void onAnimationCancel(Animator animation) {

              }

              @Override
              public void onAnimationRepeat(Animator animation) {

              }
          });
   }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,price;
        private ImageView icon;
        private Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.goodstitle);
            price = itemView.findViewById(R.id.goodsprice);
            icon = itemView.findViewById(R.id.goodsicon);
            button = itemView.findViewById(R.id.goodsbut);
        }
    }
    onCarClick monCarClick;
    public void setOnCarClickLister(onCarClick onCarClick){
        monCarClick = onCarClick;
    }
    public interface onCarClick{
        void OnCarClickLister(int pid);
    }
    onButClick monButClick;
    public void setOnButtonClick(onButClick onButClick){
        monButClick=onButClick;
    }
    public interface onButClick{
        void onButtonClick();
    }
    longClick mLongClick;
    public void setOnLongClickLister(longClick longClick){
        mLongClick=longClick;
    }
    public  interface longClick{
        void longClickLister(View view,int position);
    }

}

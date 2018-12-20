package com.example.dell.yuekao2.presenter;

import com.example.dell.yuekao2.callBack.MyCallBack;
import com.example.dell.yuekao2.model.IModelImpl;
import com.example.dell.yuekao2.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IModelImpl miModel;
    private IView miView;
    public IPresenterImpl(IView iView){
        miView = iView;
        miModel = new IModelImpl();
    }
    @Override
    public void requestData(String url, Map<String, String> prame, Class clazz) {
        miModel.requestData(url, prame, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                miView.success(data);
            }
        });
    }
}

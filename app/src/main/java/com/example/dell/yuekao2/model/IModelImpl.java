package com.example.dell.yuekao2.model;

import com.example.dell.yuekao2.callBack.MyCallBack;
import com.example.dell.yuekao2.util.ICall;
import com.example.dell.yuekao2.util.OkHttpUtil;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void requestData(String url, Map<String, String> prams, Class clazz, final MyCallBack callBack) {
        OkHttpUtil.getmIstacn().postEqueue(url, prams, new ICall() {
            @Override
            public void faile(Exception e) {
                callBack.setData(e);
            }

            @Override
            public void success(Object data) {
                 callBack.setData(data);
            }
        },clazz);
    }
}

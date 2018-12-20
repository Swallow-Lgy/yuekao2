package com.example.dell.yuekao2.model;



import com.example.dell.yuekao2.callBack.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String url, Map<String,String>prams,Class clazz,MyCallBack callBack);
}

package com.netty.rpc.request;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author xyl
 * @Create 2018-11-28 15:53
 * @Desc 封装client期望的类名，方法名等。
 **/
public class RpcRequest implements Serializable {

    private Long id;    //request的唯一标识，对应于response的id
    private String className;   //期望的类名
    private String methodName;  //期望的方法名
    private Class<?>[] parameterTypes;      //期望的方法参数类型
    private Object[] parameters;        //期望的方法参数
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId='" + id + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
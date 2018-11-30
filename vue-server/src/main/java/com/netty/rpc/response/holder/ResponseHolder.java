package com.netty.rpc.response.holder;

import com.netty.rpc.response.RpcResponse;

/**
 * @Author xyl
 * @Create 2018-11-28 16:10
 * @Desc 写点注释吧
 **/
public class ResponseHolder {

    private RpcResponse response;

    public void setResponse(RpcResponse response) {
        this.response = response;
        synchronized (this) {
            notify();
        }
    }

    public RpcResponse getResponse() throws Exception {
        synchronized (this) {
            wait(10000);
        }
        return response;
    }

    public RpcResponse getResponse(long timeout) throws Exception {
        synchronized (this) {
            wait(timeout);
        }
        return response;
    }
}

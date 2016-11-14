package com.lance.freebook.MVP.Entity;

/**
 * Created by Administrator on 2016/11/14.
 */

public class HttpResult<T> {
    public String requestTime;
    public int code;
    public T data;

    public HttpResult(String requestTime, int code, T data) {
        this.requestTime = requestTime;
        this.code = code;
        this.data = data;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.demo.advice;

import lombok.Data;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/6/2 18:07
 * @Description TODO
 */
@Data
public class ResponseInfo<T> {
    // <T> T表示返回值是一个泛型
    /**
     * 状态码
     */
    protected int code;
    /**
     * 响应信息
     */
    protected String msg;
    /**
     * 返回数据
     */
    private T data;
    private long count;


    public ResponseInfo(T data, long count) {
        this.data = data;
        this.count = count;
    }

    /**
     * 若没有数据返回，默认状态码为 0，提示信息为“操作成功！”
     */
    public ResponseInfo() {
        this.code = 0;
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     *
     * @param code 响应码
     * @param msg  响应信息
     */
    public ResponseInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为 0，默认提示信息为“操作成功！”
     *
     * @param data 响应数据
     */
    public ResponseInfo(T data) {
        this.data = data;
        this.code = 0;
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为 0，人为指定提示信息
     *
     * @param data 响应数据
     * @param msg  响应信息
     */
    public ResponseInfo(T data, String msg) {
        this.data = data;
        this.code = 0;
        this.msg = msg;
    }

    // 省略 get 和 set 方法
}


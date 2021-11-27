package com.demo.advice;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/8/9 14:27
 * @description
 * @modify
 */
public class BusinessErrorException extends RuntimeException {
    // 程序运行起来，叫做runtime，没有运行前，一般叫做编译期
    // 异常至少有两种，编译期异常，运行期异常（throw

    // 序列化id  就像一个密钥
    private static final long serialVersionUID = -7480022450501760611L;

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常提示信息
     */
    private String message;

    // 和ResponseInfo很像，也是构造方法赋值，但它不能自由赋值了，只接受BusinessMsgEnum的值
    public BusinessErrorException(BusinessMsgEnum businessMsgEnum) {
        this.code = businessMsgEnum.code;
        this.message = businessMsgEnum.msg;
    }

//    public BusinessErrorException(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

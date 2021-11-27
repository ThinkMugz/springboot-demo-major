package com.demo.advice;

// 枚举类一般用于定义
// 比如，性别 public enum SexEnum{ }
// status  color
public enum BusinessMsgEnum {
    // 枚举类命名规范：全部大写，以下划线分割
    /**
     * 参数异常
     */
    PARMETER_EXCEPTION(101, "参数异常!"),
    /**
     * 等待超时
     */
    SERVICE_TIME_OUT(102, "服务超时！"),
    /**
     * 参数过大
     */
    PARMETER_BIG_EXCEPTION(903, "内容不能超过200字，请重试!"),
    /**
     * 数据库操作失败
     */
    DATABASE_EXCEPTION(509, "数据库操作异常，请联系管理员！"),
    /**
     * 500 : 一劳永逸的提示也可以在这定义
     */
    UNEXPECTED_EXCEPTION(500, "系统发生异常，请联系管理员！"),
    // 还可以定义更多的业务异常
    ERROR_EXCEPTION(1000, "系统出锅啦！！！");
    /**
     * 消息码
     */
    public int code;
    /**
     * 消息内容
     */
    public String msg;

    private BusinessMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    // set get方法

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

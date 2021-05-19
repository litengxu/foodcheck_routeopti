package com.bjfu.fcro.common.exception;


/**枚举类实现接口，可以在此定义新的异常类型*/
public enum CommonEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误! 尝试重新登录"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!"),
    NO_EXCEl("505","上传文件的格式不正确"),
    TOKRN_NO_TIME("506","登陆时间过期，请重新登录");
    /** 错误码 */
    private String resultCode;

    /** 错误描述 */
    private String resultMsg;

    CommonEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

}


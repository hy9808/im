package com.yj.im.project.util.contants;

public enum
ResultConstantsEnum {
    RESULT_OK(ResultConstants.RESULT_OK),
    /**
     * 业务成功，但没有查询到匹配的数据
     */
    WARNING_NO_DATA(ResultConstants.WARNING_NO_DATA),

    /**
     * 业务成功，但因为没有分页参数导致查询到的数据过多，已经被后端截断部分结果
     */
    WARNING_DATA_TRUNCATED(ResultConstants.WARNING_DATA_TRUNCATED),

    /**
     * 未知错误，比如Java框架中被最终异常所捕获时返回
     */
    ERROR_UNKNOWN(ResultConstants.ERROR_UNKNOWN),

    /**
     * 权限错误，比如未传递token或者token已经失效
     */
    ERROR_FORBIDDEN(ResultConstants.ERROR_FORBIDDEN),

    /**
     * 缺少参数，比如请求方传递的参数不够
     */
    ERROR_PARAMETER(ResultConstants.ERROR_PARAMETER),

    /**
     * 参数错误，比如参数超出范围或者格式不合法
     */
    ERROR_ILLEGALPARAM(ResultConstants.ERROR_ILLEGALPARAM),

    /**
     * 未登录
     */
    ERROR_UNAUTHENTICAED(ResultConstants.ERROR_UNAUTHENTICAED),

    /**
     * 未授权
     */
    ERROR_UNAUTHORIZED(ResultConstants.ERROR_UNAUTHORIZED),

    /**
     * 业务逻辑错误，比如流程错误或者接口被非法使用
     */
    ERROR_BUSINESSERROR(ResultConstants.ERROR_BUSINESSERROR),

    /**
     * 参数正确，返回的数据为空
     */
    ERROR_NO_DATA(ResultConstants.ERROR_NO_DATA),

    /**
     * 文件解析失败
     */
    ERROR_ANALYSISFILE(ResultConstants.ERROR_ANALYSISFILE),

    /**
     * 验证码错误
     */
    ERROR_VERIFY_CODE(ResultConstants.ERROR_VERIFY_CODE),

    /**
     * 未注册
     */
    ERROR_UNREGISTER(ResultConstants.ERROR_UNREGISTER),

    /**
     * 参数为空
     */
    PARAM_NULL(ResultConstants.PARAM_NULL),
    /**
     * 已存在
     */
    EXISTS_YES(ResultConstants.EXISTS_YES),

    /**
     * 手机号已注册
     */
    MOBILE_EXISTED(ResultConstants.CODE_304_MOBILE_EXISTED),

    /**
     * 手机号未注册
     */
    MOBILE_NOT_EXIST(ResultConstants.CODE_303_MOBILE_NOT_EXIST),
    /**
     * 不是自己的手机号码
     */
    MOBILENOTME(ResultConstants.CODE_305_MOBILE_NOTYOUR),

    /**
     * 旧密码错误
     */
    OLDPASSWORDNOTFOUNT(ResultConstants.CODE_306_PASSWORDERRRO),

    /**
     * 支付密码错误
     */
    PASSWORDNOTFOUNT(ResultConstants.PASSWORDNOTFOUNT),

    /**
     *
     */
    DATA_NOT_FOUNT(ResultConstants.NOT_FOUNT),

    /**
     * 微信已经存在
     */
    WX_NOT_BLANK(ResultConstants.WX_NOT_BLANK),

    /**
     * 禁止评论
     */
    ERROR_COMMENT_FORBIDDEN(ResultConstants.ERROR_COMMENT_FORBIDDEN),
    /**
     * 文件上传失败
     */
    ERROR_FILE_UPLOAD(ResultConstants.ERROR_FILE_UPLOAD),

    /**
     * 金额不足
     */
    ERROR_INSUFFICIENT_AMOUT(ResultConstants.ERROR_INSUFFICIENT_AMOUT),

    /**
     * 订单存在
     */
    ERROR_ORDER_EXISTS(ResultConstants.ERROR_ORDER_EXISTS),

    OPERATION_FREQUENTLY(ResultConstants.OPERATION_FREQUENTLY),
    /**
     * 存在广告
     */
    ERROR_ISAD(ResultConstants.ERROR_ISAD),

    ERROR_IS_SMALL(ResultConstants.ERROR_IS_SMALL),

    ERROR_IS_400(ResultConstants.ERROR_IS_400),

    ERROR_IS_404(ResultConstants.ERROR_IS_404),

    ERROR_IS_405(ResultConstants.ERROR_IS_405),

    ERROR_IS_406(ResultConstants.ERROR_IS_406),

    ERROR_IS_500(ResultConstants.ERROR_IS_500),

    ERROR_IS_501(ResultConstants.ERROR_IS_501),

    ERROR_IS_RunTimeException(ResultConstants.ERROR_IS_RunTimeException);


    private final int code;

    private ResultConstantsEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
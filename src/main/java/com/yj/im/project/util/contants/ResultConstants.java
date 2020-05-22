package com.yj.im.project.util.contants;


public class ResultConstants {

    //SUCCEED////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 执行成功，没用任何警告信息
     */
    public static final int RESULT_OK = 0;

    //WARNING////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 业务成功，但没有查询到匹配的数据
     */
    public static final int WARNING_NO_DATA = 1;

    /**
     * 业务成功，但因为没有分页参数导致查询到的数据过多，已经被后端截断部分结果
     */
    public static final int WARNING_DATA_TRUNCATED = 2;


    //ERROR////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 未知错误，比如Java框架中被最终异常所捕获时返回
     */
    public static final int ERROR_UNKNOWN = -1;

    /**
     * 权限错误，比如未传递token或者token已经失效
     */
    public static final int ERROR_FORBIDDEN = -2;

    /**
     * 缺少参数，比如请求方传递的参数不够
     */
    public static final int ERROR_PARAMETER = -3;

    /**
     * 参数错误，比如参数超出范围或者格式不合法
     */
    public static final int ERROR_ILLEGALPARAM = -4;

    /**
     * 未登录
     */
    public final static int ERROR_UNAUTHENTICAED = -5;

    /**
     * 未授权
     */
    public final static int ERROR_UNAUTHORIZED = -6;

    /**
     * 业务逻辑错误，比如流程错误或者接口被非法使用
     */
    public static final int ERROR_BUSINESSERROR = -7;

    /**
     * 文件解析失败
     */
    public static final int ERROR_ANALYSISFILE = -8;

    /**
     * 参数正确，但是是没有数据返回
     */
    public static final int ERROR_NO_DATA = -9;

    /**
     * 验证码错误
     */
    public static final int ERROR_VERIFY_CODE = -10;
    /**
     * token 失效/过期
     */
    public static final int TOKEN_HAS_EXPIRE = -11;

    /**
     * 未注册
     */
    public final static int ERROR_UNREGISTER = -12;

    /**
     * 参数为空
     */
    public static final int PARAM_NULL = -13;

    /**
     * 图片类型错误
     */
    public static final int TYPE_ERROR = -14;

    /**
     * 没有查询到任何数据
     */
    public static final int NOT_FOUNT = -15;

    /**
     * 微信已经存在
     */
    public static final int WX_NOT_BLANK = -16;

    /**
     * 禁止评论
     */

    public static final int ERROR_COMMENT_FORBIDDEN = -17;

    /**
     * 文件上传失败
     */
    public static final int ERROR_FILE_UPLOAD = -18;
    /**
     * 金额不足
     */
    public static final int ERROR_INSUFFICIENT_AMOUT = -19;

    /**
     * 当前还有未审核的订单
     */
    public static final int ERROR_ORDER_EXISTS = -20;

    /**
     * 存在广告
     */
    public static final int ERROR_ISAD = -21;

    public static final int ERROR_IS_SMALL = -22;

    //验证码目的
    public static class SmsType {

        public static final byte REGISTER = 11;
        //        public static final byte FIND_PASSWORD = 12;
        public static final byte CHANGE_MOBILE = 13;
        public static final byte LOGIN_MOBILE = 14;
        public static final byte CHANGE_PAYPASSWORD = 15;
        public static final byte CHANGE_PHONE = 16;
    }

    //手机号已存在
    public static final int CODE_304_MOBILE_EXISTED = 304;

    //手机号不存在
    public static final int CODE_303_MOBILE_NOT_EXIST = 303;

    //不是本账号的手机号码
    public static final int CODE_305_MOBILE_NOTYOUR = 305;

    public static final int CODE_306_PASSWORDERRRO = 306;


    /**
     * 已存在
     */
    public static final int EXISTS_YES = 307;

    /**
     * 操作频繁
     */

    public static final int OPERATION_FREQUENTLY = 308;


    public static final int PASSWORDNOTFOUNT = -15;


    public static final int ERROR_IS_400 = 400;

    public static final int ERROR_IS_404 = 404;
    public static final int ERROR_IS_405 = 405;
    public static final int ERROR_IS_406 = 406;
    public static final int ERROR_IS_500 = 500;
    public static final int ERROR_IS_501 = 501;
    public static final int ERROR_IS_RunTimeException = 505;


}

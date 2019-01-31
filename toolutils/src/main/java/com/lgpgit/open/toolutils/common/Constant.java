package com.lgpgit.open.toolutils.common;

/**
 * 共通常量
 * @author lugp
 * @date 2019/01/30
 */
public class Constant {

    /** handler常用常量 */
	//消息_成功标识
	public static final int MSG_SUCCESS = 1;
	
	//网络请求
	public static final int NETWORK_REQUEST_QUERY = 2;

	//网络请求IO异常
    public static final int NETWORK_REQUEST_IOEXCEPTION_CODE = 3;

    //网络请求返回空
    public static final int NETWORK_REQUEST_RETUN_NULL = 4;

    //返回解析错误
    public static final int NETWORK_REQUEST_RESULT_PARSE_ERROR = 5;
    
    //网络请求超时
    public static final int NETWORK_REQUEST_PASS_TIME = 6;
    
    //网络请求获取数据响应超时
    public static final int NETWORK_REQUEST_GERDATA_TIME = 7;
    
    //网络请求错误
    public static final int NETWORK_REQUEST_ERROR = 8;
    
    //网络服务请求超时时间
    public static final int NETWORK_REQUEST_TIME = 10000;
    
    //访问的rest服务接口没有是授权
    public static final int NETWORK_REQUEST_UNAUTHORIZED = 10;
    
    public static final int MSG_ERROR_INFO = 11;
    
    // 用户名或密码错误
    public static final String USERNAME_PASSWORD_ERROR = "-1";
    // 当前用户已在其它终端登录，请重新登录
    public static final String TOKEN_ERROR = "-2";

    /** 经纬度 */
    //地理位置经度
    public static double longitude = 0;
    //地理位置纬度
    public static double latitude = 0;

    /** 消息key */
	public static final String MESSAGE_KEY_LOGIN_FAILED   = "LOGIN_FAILED";  // 身份认证失败
	public static final String MESSAGE_KEY_INVALID_TOKEN  = "INVALID_TOKEN"; // 票据校验失败
	public static final String MESSAGE_KEY_UNAUTHORIZED   = "UNAUTHORIZED";  // 未授权
	public static final String MESSAGE_KEY_SERVICE_EX     = "SERVICE_EX";    // 服务异常

    /** 消息提示 */
	public static final String network_exception = "网络异常，请检查网络设置后重新尝试！";
	public static final String connect_timeout_exception = "服务器连接超时，请联系系统管理员！";
	public static final String socket_timeout_exception = "服务器响应超时，请联系系统管理员！";
	public static final String dataload_exception = "数据加载异常，请联系系统管理员！";

	//保留线程
    public static final String THREAD_PERSIST = "1";
	//删除线程
    public static final String THREAD_DELETE = "0";

	//数字0-4对应的字符
    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";

    //数字0-4
    public static final int INT_ZERO = 0;
    public static final int INT_ONE = 1;
    public static final int INT_TWO = 2;
    public static final int INT_THREE = 3;
    public static final int INT_FOUR = 4;

	//是/对...(yes)
    public static final String YES = "0";
    //不是/不对...(no)
    public static final String NO = "1";

}

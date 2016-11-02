package com.ctrl.android.property.staff.base;

/**
 * 全局静态变量类
 * 用于存放全局的静态变量, 如 服务地址, 支付宝微信等支付手段信息配置
 * Created by Eric on 2015/5/20.
 */
public class Constant {

    /**服务地址 根路径 的配置 - Eric - */
    //public static final String RAW_URL = "http://121.42.159.145:8088/pm/api?";//外网地址
    //public static final String RAW_URL = "http://121.42.159.145:8899/pm/api?";//外网地址 客户服务器
   // public static final String RAW_URL = "http://58.56.38.194:8082/pm/api?";//外网地址 客户服务器2
    public static final String RAW_URL = "http://xingfuaijia.net:8082/pm/api?";//外网地址 客户服务器2
    //public static final String RAW_URL = "http://192.168.1.36:8088/pm/api?";//李鑫测试地址
    //public static final String RAW_URL = "http://192.168.1.210:8088/pm/api?";//桑越测试地址
    //public static final String RAW_URL = "http://192.168.1.217:8088/pm/api?";//方文测试地址
  //  public static final String RAW_URL = "http://192.168.200.127:8888/pm/api?";//李鑫测试地址
    //public static final String RAW_URL = "http://115.28.243.3:8080/pm/api?";//内网测试地址

    /**请求dao时传递参数 - Eric - **/
    public static String APPKEY = "appKey";//用用程序的key
    public static String APPKEY_VALUE = "002";//用用程序的key
    public static String SECRET = "secret";//密匙
    public static String SECRET_VALUE = "abc";//密匙
    public static String VERSION = "v";//版本

    public static String VERSION_VALUE = "1.0";//版本
    public static String FORMAT = "format";//输出格式 JSON
    public static String FORMAT_VALUE = "JSON";//输出格式 JSON
    public static String TYPE = "type";//1:android  2:IOS  3:web
    public static String TYPE_VALUE = "1";//1:android  2:IOS  3:web
    public static String METHOD = "method";//方法名

    /**支付宝相关 支付信息的配置 - Eric - */
    /*支付宝回调接口*/
    public static final String ALIPLY_URL = RAW_URL + "/notify_url.jsp";
    /*合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。 例: 2088812260656255*/
    public static final String PARTNER = "2088812260656255";
    /*商户收款的支付宝账号 例: wushidadao77@163.com*/
    public static final String SELLER = "wushidadao77@163.com";//
    /*商户私钥 例: MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ+Q9tJIfK5PvZtC6eGgucND3cOEBbc6wUJVrsgEPKiYJmMFJFe0fya1hvPragz4scRw+ozi45e4eeWnpMUJmXg4pYyZcpozom1nsGlp293DE29nqJvXTv0qEzNqZcQgSCkoZzVBTnaREw6o2XBXKyVt11B4DrYWTBqFDWyhzlkHAgMBAAECgYEAiHreklgLxLBRtdYS47isitamfM+Ub/diS5Gr8Eqnc3DIDJPeVOH+i6Ziaoll6PhiXGph81UxY5kXMhYk+Z9PUsnOq6piLR6jajs7/PQbeUOrK/27lzKx97f2/zVacFadkx33P/ReXNe6sCY7xvVr8AiDL2Qyh0TiNhmfzx39CDECQQDThTngCjCVEO1AjCX9keA3E3isAaZeDGWXfkdO4JIsOnCyRh/V1QiUvCwbpgdvdxf+N9RsQxC2faR/p9/7UDffAkEAwR7kOzuq/dGTTb65xTSY314O3qe3oaK+G9euWzQPavj+DybxhClqc1HNcYoHteM47Mry1vOmeRY+iug4UESj2QJATGyDd7ZWzVU7U6oPg+m0CFJJtGQ4Nxzli/H9U7uCNOa8lz0M/ZamLg87JJY9c4GlMp37a05j+Hu29sSyAbx/IwJAfGKMN8aHrLGmgcWdW3I0IHIxe6FkufvbHI2/ZEjUwV6cLGA14JzYTmxauY1gx/sQ+BsDbAVErOrx34AQfUqoiQJBANNb9XhPjFNYsjMlqlV2ccYURzQNQkqSZZF2WuudoglxtgiK0w+RbdZcB8cRi/EkuNT6CODb3chlJCNpTpn/CXQ=*/
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ+Q9tJIfK5PvZtC6eGgucND3cOEBbc6wUJVrsgEPKiYJmMFJFe0fya1hvPragz4scRw+ozi45e4eeWnpMUJmXg4pYyZcpozom1nsGlp293DE29nqJvXTv0qEzNqZcQgSCkoZzVBTnaREw6o2XBXKyVt11B4DrYWTBqFDWyhzlkHAgMBAAECgYEAiHreklgLxLBRtdYS47isitamfM+Ub/diS5Gr8Eqnc3DIDJPeVOH+i6Ziaoll6PhiXGph81UxY5kXMhYk+Z9PUsnOq6piLR6jajs7/PQbeUOrK/27lzKx97f2/zVacFadkx33P/ReXNe6sCY7xvVr8AiDL2Qyh0TiNhmfzx39CDECQQDThTngCjCVEO1AjCX9keA3E3isAaZeDGWXfkdO4JIsOnCyRh/V1QiUvCwbpgdvdxf+N9RsQxC2faR/p9/7UDffAkEAwR7kOzuq/dGTTb65xTSY314O3qe3oaK+G9euWzQPavj+DybxhClqc1HNcYoHteM47Mry1vOmeRY+iug4UESj2QJATGyDd7ZWzVU7U6oPg+m0CFJJtGQ4Nxzli/H9U7uCNOa8lz0M/ZamLg87JJY9c4GlMp37a05j+Hu29sSyAbx/IwJAfGKMN8aHrLGmgcWdW3I0IHIxe6FkufvbHI2/ZEjUwV6cLGA14JzYTmxauY1gx/sQ+BsDbAVErOrx34AQfUqoiQJBANNb9XhPjFNYsjMlqlV2ccYURzQNQkqSZZF2WuudoglxtgiK0w+RbdZcB8cRi/EkuNT6CODb3chlJCNpTpn/CXQ=";

    /**微信相关 支付信息的配置 - Eric - */
    /*微信分配的公众账号ID 例: wx6848dc314d5a2b80*/
    public static final String APP_ID = "";
    /*微信支付分配的商户号 例: 1232506602*/
    public static final String MCH_ID = "";
    /*API密钥，在商户平台设置 例: q1w2e3r4t5y6u7i8o9p0asdfghjklzxc*/
    public static final  String API_KEY="";
    /*微信支付返回Code  固定值*/
    public static final int PAY_STATUS_SUCCESS = 0;//微信支付成功
    public static final int PAY_STATUS_FAILED = -1;//微信支付失败
    public static final int PAY_STATUS_CANCLE = -2;//微信支付 被取消

    /**列表数据每页显示的条数 - Eric - 20150929*/
    public static final int ROW_COUNT_PER_PAGE = 10;
    /**商铺中商品列表的类型 - Eric - 20150929*/
    /*全部*/
    public static final String MALL_PRO_LIST_ALL = "pro_all";
    /*最新*/
    public static final String MALL_PRO_LIST_NEW = "new";
    /*销量*/
    public static final String MALL_PRO_LIST_SALES = "sales";
    /*畅销*/
    public static final String MALL_PRO_LIST_BEST_SELLER = "best_seller";

    /**评价列表的类型 - Eric - 20150929*/
    /*全部*/
    public static final String MALL_COMMENT_LIST_ALL = "comment_all";
    /*好评*/
    public static final String MALL_COMMENT_LIST_GOOD = "good";
    /*中评*/
    public static final String MALL_COMMENT_LIST_NORMAL = "normal";
    /*差评*/
    public static final String MALL_COMMENT_LIST_BAD = "bad";

    /**设备养护 标识 - Eric - 20150929*/
    /*本月*/
    public static final String DEVICE_NOW_MONTH = "month_now";
    /*上月*/
    public static final String DEVICE_LAST_MONTH = "month_last";
    /*更多*/
    public static final String DEVICE_MORE_MONTH = "month_more";

    /**任务指派 标识 - Eric - 20150929*/
    /*待处理*/
    public static final String TASK_DEAL = "task_deal";
    /*已完成*/
    public static final String TASK_DONE = "task_done";


    /**下载地址*/
    public static final String SD_DOWNLOAD_PATH = "apt/download";


    /**时间 类型 - Eric - 20151013*/
    /*年-月-日*/
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /*年-月-日 时:分*/
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String HH_MM = "HH:mm:ss";

    /**每页容量- 每页有多少条记录*/
    public static final int PAGE_CAPACITY = 10;
    /**分页编号*/
    public static final int DEFAULT_PAGE_NUM = 0;

    /**用户名*/
    public static final String USERNAME = "username_staff";
    /**密码*/
    public static final String PASSWORD = "password_staff";

    /**图片上传类型*/
    /*报修*/
    public static final String IMG_TYPE_FIX = "FIX";
    /*报修处理结果*/
    public static final String IMG_TYPE_FIX_RST = "FIX_RST";
    /*投诉*/
    public static final String IMG_TYPE_CPT = "CPT";
    /*投诉处理结果*/
    public static final String IMG_TYPE_CPT_RST = "CPT_RST";
    /*二手物品交易*/
    public static final String IMG_TYPE_USD_GD = "USD_GD";
    /*房屋交易*/
    public static final String IMG_TYPE_HS_TS = "HS_TS";
    /*指派任务反馈*/
    public static final String IMG_TYPE_TSK_FB = "TSK_FB";
    /*新增指派任务反馈*/
    public static final String IMG_TYPE_NEW_TSK_FB = "NEW_TSK_FB";
    /*设备养护记录*/
    public static final String IMG_TYPE_MT = "MT";
    /*巡更结果*/
    public static final String IMG_TYPE_PT_RST = "PT_RST";
    /*快递图片*/
    public static final String IMG_TYPE_EX = "EX";
    /*活动图片*/
    public static final String IMG_TYPE_AC = "AC";





    /**房屋缴费列表的类型 标识 - Eric - 20150929*/
    /*最近一个月*/
    public static final String PAY_LIST_MONTH_ONE = "month_one";
    /*最近二个月*/
    public static final String PAY_LIST_MONTH_TWO = "month_two";
    /*最近三个月*/
    public static final String PAY_LIST_MONTH_THREE = "month_three";




}


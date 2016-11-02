package com.ctrl.android.property.base;

/**
 * 全局静态变量类
 * 用于存放全局的静态变量, 如 服务地址, 支付宝微信等支付手段信息配置
 * Created by Eric on 2015/5/20.
 */
public class Constant {

    /**服务地址 根路径 的配置 - Eric - */
  //  public static final String RAW_URL = "http://115.28.243.3:8080/pm/api?";//内网地址
    //public static final String RAW_URL = "http://121.42.159.145:8088/pm/api?";//外网地址
    //public static final String RAW_URL = "http://121.42.159.145:8899/pm/api?";//外网地址 客户服务器
   public static final String RAW_URL = "http://60.208.78.252:8082/pm/api?";//外网地址 客户服务器2
    //public static final String RAW_URL = "http://192.168.1.3:8088/pm/api?";//李鑫测试地址
    //public static final String RAW_URL = "http://192.168.1.210:8088/pm/api?";//桑越测试地址
    //public static final String RAW_URL = "http://192.168.0.158:8088/pm/api?";//方文测试地址

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
    public static final String ALIPLY_URL = RAW_URL + "http://121.42.159.145:8088/pm/alipayOrderNotify.do";
    public static final String ALIPLY_URL_PROPERTY = "http://121.42.159.145:8088/pm/alipayPaymentNotify.do";
    /*合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。 例: 2088812260656255*/
    public static final String PARTNER = "2088611541296867";
    /*商户收款的支付宝账号 例: wushidadao77@163.com*/
    public static final String SELLER = "cxhzijin01@163.com";//
    /*商户私钥 例: MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ+Q9tJIfK5PvZtC6eGgucND3cOEBbc6wUJVrsgEPKiYJmMFJFe0fya1hvPragz4scRw+ozi45e4eeWnpMUJmXg4pYyZcpozom1nsGlp293DE29nqJvXTv0qEzNqZcQgSCkoZzVBTnaREw6o2XBXKyVt11B4DrYWTBqFDWyhzlkHAgMBAAECgYEAiHreklgLxLBRtdYS47isitamfM+Ub/diS5Gr8Eqnc3DIDJPeVOH+i6Ziaoll6PhiXGph81UxY5kXMhYk+Z9PUsnOq6piLR6jajs7/PQbeUOrK/27lzKx97f2/zVacFadkx33P/ReXNe6sCY7xvVr8AiDL2Qyh0TiNhmfzx39CDECQQDThTngCjCVEO1AjCX9keA3E3isAaZeDGWXfkdO4JIsOnCyRh/V1QiUvCwbpgdvdxf+N9RsQxC2faR/p9/7UDffAkEAwR7kOzuq/dGTTb65xTSY314O3qe3oaK+G9euWzQPavj+DybxhClqc1HNcYoHteM47Mry1vOmeRY+iug4UESj2QJATGyDd7ZWzVU7U6oPg+m0CFJJtGQ4Nxzli/H9U7uCNOa8lz0M/ZamLg87JJY9c4GlMp37a05j+Hu29sSyAbx/IwJAfGKMN8aHrLGmgcWdW3I0IHIxe6FkufvbHI2/ZEjUwV6cLGA14JzYTmxauY1gx/sQ+BsDbAVErOrx34AQfUqoiQJBANNb9XhPjFNYsjMlqlV2ccYURzQNQkqSZZF2WuudoglxtgiK0w+RbdZcB8cRi/EkuNT6CODb3chlJCNpTpn/CXQ=*/
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMhfPjU63z1bdfZuyf0ZD47iQKvOR8ZpxOYEJ96yyKL7TMa8hfRcnMVWMDVl9icYKDAN7ykAQYvJCeWkvuYsRj3CBhJFBsShkOdzGZG+Zf/gzPbQ27BXdYjnmmklObZl9UiYM8HeQG/M9m576QWJhVa3gLCG4u9nhG7Oar7SropfAgMBAAECgYBTtU50yHKI3JPMbv5diUGxrJ3d6KCdqIREn8nURJaDWD7767h+nM41tssPE9ig/nuTj86xL1Uyw7spwAiseiShXQm4XDUTEyeo3S4M96gO/0510mEVx5lT9MV0lIqAM12PbyTxNWn3SOg9ds/Fu0TQqLip6SgQDqiEnfAn8VZ4oQJBAOf6aGxFrIQaDAn6gs0PPj29wi3vbVHCr3YtdO4WgCTuVWgN8bhw4QlGNuTohztL8NNbEIAmqn/igCdgEVHW6G8CQQDdHvoxpJLHVRpBSkYVcwG17uihQAUK4xKcBXsjpqHBP8Xvix6aJ3F9ccqhkMMrbUh6zeLOrlg7/oswmFgfKBURAkBHeeds3Pbv6RqmhHKC/lxeJ7bJ8ojLdWIc1pq9tV8cgfb8zbcZ7mXYNrM5StBIG/kDFn76DW/hYYe9GiOcBMyvAkAflldDVDErjHqtrQCJ+93YsYJF1rFhtsJny1il5R3iT0vlRkhe2RebRfAeWGGpCHl8IYEu6TTtjRUxIfIksUMhAkBQhhxe8NyNGZblel6ubCCnok6DQNBvJ+tzo80yh67S3kxuOO1rZFul1XTlFF08ME03nZ66aELUyWtW3kHxr4UU";

    /**微信相关 支付信息的配置 - Eric - */
    /*微信分配的公众账号ID 例: wx6848dc314d5a2b80*/
    public static final String APP_ID = "wxae1e8e0c9c574912";
    /*微信支付分配的商户号 例: 1232506602*/
    public static final String MCH_ID = "1298087001";
    /*API密钥，在商户平台设置 例: q1w2e3r4t5y6u7i8o9p0asdfghjklzxc*/
    public static final  String API_KEY="e7d814bbbf364f8ab06431d8efda5928";
    //异步通知地址
    public static final  String NOTICE_URL="http://121.42.159.145:8088/pm/wxOrderNotify.do";
    public static final  String NOTICE_URL_PROPERTY="http://121.42.159.145:8088/pm/wxPaymentNotify.do";
    /*微信支付返回Code  固定值*/
    public static final int PAY_STATUS_SUCCESS = 0;//微信支付成功
    public static final int PAY_STATUS_FAILED = -1;//微信支付失败
    public static final int PAY_STATUS_CANCLE = -2;//微信支付 被取消

    /**下载地址*/
    public static final String SD_DOWNLOAD_PATH = "apt/download";

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

    /**房屋缴费列表的类型 标识 - Eric - 20150929*/
    /*最近一个月*/
    public static final String PAY_LIST_MONTH_ONE = "month_one";
    /*最近二个月*/
    public static final String PAY_LIST_MONTH_TWO = "month_two";
    /*最近三个月*/
    public static final String PAY_LIST_MONTH_THREE = "month_three";

    /**活动列表的类型 - Eric - 20151026*/
    /*全部活动*/
    public static final String ACT_ALL = "act_all";
    /*我参与的*/
    public static final String ACT_I_TAKE_IN = "act_i_start_up";
    /*我发起的*/
    public static final String ACT_I_START_UP = "act_i_take_in";

    /**商品详细 类型 - Eric - 20150930*/
    /*图文详情*/
    public static final String MALL_PRO_DETAIL_TEXT = "pro_detail_text";
    /*产品参数*/
    public static final String MALL_PRO_DETAIL_ARGS = "pro_detail_args";

    /**社区调查的类型 标识 - Eric - 20151104*/
    /*社区调查*/
    public static final String COMMINITY_SURVEY = "survey";
    /*投票*/
    public static final String COMMINITY_VOTE = "vote";

    /**时间 类型 - Eric - 20151013*/
    /*年-月-日*/
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /*年-月-日 时:分*/
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**每页容量- 每页有多少条记录*/
    public static final int PAGE_CAPACITY = 10;
    /**分页编号*/
    public static final int DEFAULT_PAGE_NUM = 0;

    /**默认检索范围 单位: 米*/
    public static final int SEARCH_RADIUS = 2000;

    /**默认检索关键字*/
    public static final String SEARCH_KEY_WORD = "公交";

    /**当activity之间传递参数, 只有一个参数时 该变量为固定的key值*/
    public static final String ARG_FLG = "arg";

    /**跳转至周围商家时 固定参数*/
    public static final String ARG_HOTEL = "酒店";
    public static final String ARG_LIFE = "生活服务";
    public static final String ARG_LANDRY = "洗衣";
    public static final String ARG_ENTERTAIN = "休闲娱乐";
    public static final String ARG_MOVIE = "电影";
    public static final String ARG_FOOD = "美食";
    public static final String ARG_KTV = "KTV";
    public static final String ARG_COFFEE = "咖啡";
    public static final String ARG_GYM = "健身";
    public static final String ARG_PARKING = "停车";
    public static final String ARG_FACIAL = "美容";
    public static final String ARG_TRAVAL = "旅游";

    /**用户名*/
    public static final String USERNAME = "username";
    /**密码*/
    public static final String PASSWORD = "password";

    /**跳转至论坛列表时 固定参数*/
    public static final String ARG_EXP_AREA = "经验交流区";
    public static final String ARG_VER_UPDATE = "版本更新公告";
    public static final String ARG_GREEN_HAND = "新人通知";
    public static final String ARG_SSENCE_AREA = "精品区";
    public static final String ARG_COMMUNITY_SURVEY = "社区调查";
    public static final String ARG__COMMUNITY_ACT = "社区活动";

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

    /**调查问卷*/
    /*问题ID*/
    public static String MESSAGEID = "questionnaireMessageId";
    /*选项编号*/
    public static String OPTIONNUM = "optionNo";




}


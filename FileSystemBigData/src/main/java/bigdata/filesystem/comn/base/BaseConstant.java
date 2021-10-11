package bigdata.filesystem.comn.base;

/**
 * @InterfaceName BaseConstant
 * @Description 一些数据库定义默认值
 * @Author lirunyi
 * @Date 2020/12/25 16:32
 */

public interface BaseConstant {
    Integer STATUS_NORMAL = 1;
    Integer STATUS_FREEZE = 2;
    Integer STATUS_DELETE = 3;
    /**
     * 验证码键值
     */
    String CAPTCHA_SESSION_KEY = "CAPTCHA_SESSION_KEY";

    String LOG_TYPE_GET = "查询";
    String LOG_TYPE_CREATE = "创建";
    String LOG_TYPE_UPDATE = "修改";
    String LOG_TYPE_UPLOAD = "上传";
    String LOG_TYPE_DELETE = "删除";
    String LOG_TYPE_ERROR = "错误";

    String RIGHT_TYPE_USER_RIGHT = "USER_RIGHT";
//    String RIGHT_TYPE_ROLE_RIGHT = "ROLE_RIGHT";
    String RIGHT_TYPE_CATALOG_RIGHT = "CATALOG_RIGHT";
    String RIGHT_TYPE_FILE_RIGHT = "FILE_RIGHT";

    Integer IS_PARSE_YES = 1;
    Integer IS_PARSE_NO = 0;

    String CATALOG_OR_FILE_CATALOG = "catalog";
    String CATALOG_OR_FILE_FILE = "file";
}

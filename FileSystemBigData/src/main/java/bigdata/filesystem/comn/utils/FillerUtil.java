package bigdata.filesystem.comn.utils;

import bigdata.filesystem.comn.result.RespResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillerUtil {

    // 定义填充规则
    private static String FILLER_RULE = "\\{\\{(.*?)\\}\\}";

    public static boolean hasMatches(String regix, String str) {
        Pattern pattern = Pattern.compile(regix);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    /**
     *  * @description: 遍历list中的数据进行填充
     *  * @return
     */
    public static String fill(String str, List<String> fillList) {
        if (null == str) {
            str = "";
        }
        Pattern pattern = Pattern.compile(FILLER_RULE);

        Matcher matcher = pattern.matcher(str);
        int index = 0;
        while (matcher.find()) {
            str = str.replaceFirst(FILLER_RULE, getItem(fillList, index++));
        }
        return str;
    }

    /**
     *  * @description: 获取List中的符合index的数据，否则返回空字符串  
     *
     * @return
     */
    private static String getItem(List<String> list, int index) {
        if (null == list || list.isEmpty() || index < 0 || index >= list.size()) {
            return "";
        } else {
            return getObject(list.get(index));
        }
    }

    /**
     * 经前端协定： Object 为null时，则返回 js的空实体即："{}" String 为null时，则返回js的空字符串：""
     */
    private static Object getObject(Object obj) {
        if (null == obj) {
            return "{}";
        } else {
            return obj;
        }
    }

    private static String getObject(String obj) {
        if (null == obj) {
            return "";
        } else {
            return obj;
        }
    }

    /**
     *  * @description: 根据前端协定，重新填充未填字段
     */
    public static void fillResult(RespResult respResult) {
        if (null == respResult) {
            return;
        } else {
            respResult.setCode(getObject(respResult.getCode()));
            respResult.setMsg(getObject(respResult.getMsg()));
            respResult.setEntity(getObject(respResult.getEntity()));
        }
    }
}

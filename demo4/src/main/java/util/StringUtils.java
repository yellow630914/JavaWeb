package util;

public class StringUtils {//判断字符串是否为null或者""
    public static boolean isEmpty(String str){
        return str==null || "".equals(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}

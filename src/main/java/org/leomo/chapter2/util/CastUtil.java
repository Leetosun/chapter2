package org.leomo.chapter2.util;

/**
 * Created by LeeToSun on 2017/5/18
 * 转型操作工具类
 */
public final class CastUtil {

    /**
     * 转型为String
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转型为String，提供默认值
     */
    public static String castString(Object obj, String defaultVaule) {
        return null == obj ? defaultVaule : String.valueOf(obj);
    }

    /**
     * 转型为double
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转型为double，提供默认值
     */
    public static double castDouble(Object obj, double defalutVaule) {
        double doubleValue = defalutVaule;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defalutVaule;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转型为long
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转型为long，提供默认值
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转型为int
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转型为int，提供默认值
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转型为boolean
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转型为boolean，提供默认值
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

}

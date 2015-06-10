package andycheung.baseproject.util;

import android.util.Log;

import andycheung.baseproject.MyConstant;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class LogUtil {
    public static void log(String msg) {
        log(null, msg);
    }

    public static void vollyLog(String msg) {
        if (MyConstant.IS_DEBUG) {
            log("[Volley] " + msg);
        }
    }

    public static <T> void lifeLog(T type, String msg) {
        if (MyConstant.IS_DEBUG) {
            LogUtil.printMessageLog(type, "[AndroidLife] " + msg);
        }
    }

    public static <T> void log(T type, String msg) {
        if (MyConstant.IS_DEBUG) {
            LogUtil.printMessageLog(type, msg);
        }
    }

    public static void productionLog(String msg) {
        if (MyConstant.IS_PRODUCTION_LOG_ENABLE) {
            log(msg);
        }
    }

    private static <T> void printMessageLog(T type, String msg) {
        if (type == null) {
            Log.d(MyConstant.LOG_TAG, msg);
        } else {
            Log.d(MyConstant.LOG_TAG, "[" + type.getClass().getSimpleName()
                    + "] " + msg);
        }
    }
}

package andycheung.baseproject.util;

import android.content.Context;
import android.content.SharedPreferences;

import andycheung.baseproject.MyApplication;
import andycheung.baseproject.MyConstant;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class SharedPreferencesUtil {

    public static SharedPreferences getSharedPreferences() {
        return MyApplication.application.getSharedPreferences(
                MyConstant.SP_APPLICATION_KEY, Context.MODE_PRIVATE);
    }

    /**
     * Language
     *
     * @param languageId
     * @return
     */

    public static boolean saveLanguageId(int languageId) {
        return getSharedPreferences().edit()
                .putInt(MyConstant.SP_LANGUAGE_TYPE_ID_KEY, languageId)
                .commit();
    }

    public static int getLanguageId() {
        return getSharedPreferences().getInt(
                MyConstant.SP_LANGUAGE_TYPE_ID_KEY, 0);
    }

}

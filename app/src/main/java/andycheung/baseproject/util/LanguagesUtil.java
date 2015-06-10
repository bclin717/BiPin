package andycheung.baseproject.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;

import java.util.Locale;

import andycheung.baseproject.MyApplication;
import andycheung.baseproject.util.MyEnum.MyLanguage;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class LanguagesUtil {

    public static boolean updateLanguage(FragmentActivity activity) {
        Locale oldLocale = getCurrentLanguageLocale();
        Locale newLocale;
        // LogUtil.log("updateLanguage >>> [START] app lang: " + oldLocale);

       MyLanguage newLanguage = MyLanguage.EN;
        // restore save
        int savedLanguageId = SharedPreferencesUtil.getLanguageId();
        if (savedLanguageId == MyLanguage.EN.id) {
            // LogUtil.log("updateLanguage >>> saved: " + MyLanguage.EN);
            newLanguage = MyLanguage.EN;
        } else if (savedLanguageId == MyLanguage.TC.id) {
            // LogUtil.log("updateLanguage >>> saved: " + MyLanguage.TC);
            newLanguage = MyLanguage.TC;
        } else if (savedLanguageId == MyLanguage.SC.id) {
            // LogUtil.log("updateLanguage >>> saved: " + MyLanguage.SC);
            newLanguage = MyLanguage.SC;
        }
        newLocale = newLanguage.local[0];
        // LogUtil.log("updateLanguage >>> will change to: " + newLocale);

        // update
        Resources resources = activity.getBaseContext().getResources();
        Configuration config = resources.getConfiguration();
        config.locale = newLocale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // save
        SharedPreferencesUtil.saveLanguageId(newLanguage.id);
        // LogUtil.log("updateLanguage >>> [END] app lang: "
        // + getCurrentLanguageLocale());

        return oldLocale != newLocale;
    }

    public static Locale getCurrentLanguageLocale() {
        Locale currentLang = null;
        try {
            currentLang = MyApplication.application.getResources()
                    .getConfiguration().locale;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentLang;
    }

    public static MyLanguage getCurrentLanguageType() {
        Locale currentLang = getCurrentLanguageLocale();
        if (DataUtil.isArrayContains(MyLanguage.EN.local, currentLang)) {
            return MyLanguage.EN;
        } else if (DataUtil.isArrayContains(MyLanguage.TC.local, currentLang)) {
            return MyLanguage.TC;
        } else if (DataUtil.isArrayContains(MyLanguage.SC.local, currentLang)) {
            return MyLanguage.SC;
        } else {
            return MyLanguage.EN;
        }
    }

    public static String getTextByLanguage(String textEn, String textTc,
                                           String textSc) {
        MyLanguage currentLang = getCurrentLanguageType();
        String checkedtext;

        switch (currentLang) {
            case TC:
                checkedtext = textTc;
                break;
            case SC:
                checkedtext = textSc;
                break;
            case EN:
            default:
                checkedtext = textEn;
                break;
        }

        if (checkedtext == null) {
            checkedtext = textEn;
        }
        return checkedtext;
    }
}

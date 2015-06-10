package andycheung.baseproject;

import android.annotation.SuppressLint;
import android.os.Environment;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class MyConstant {

    // APP settings
    public static final boolean IS_PRODUCTION_LOG_ENABLE = true;
    public static final boolean IS_DEBUG = true;

    public static final String packageName =
            MyApplication.application
                    .getPackageName();

    public static final String LOG_TAG = packageName;

    /**
     * folder
     */
    @SuppressLint("SdCardPath")
    public static final String DATA_DATA_DIRECTORY = "/data/data/"
            + packageName + "/";
    public static final String EXTERNAL_STORAGE_DIRECTORY = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/";

    public static final String APP_ROOT_FOLDER_NAME = "BaseProject";

    public static final String APP_ROOT_DIR = (BuildConfig.buildType.equals("release"))
            ? DATA_DATA_DIRECTORY + APP_ROOT_FOLDER_NAME + "/"
            : EXTERNAL_STORAGE_DIRECTORY + APP_ROOT_FOLDER_NAME + "/";

    public static String VOLLEY_TEMP_FOLDER = APP_ROOT_DIR + "VolleyTemp/";


    // Retain Fragment
    public static final int RETAIN_FRAGMENT_REQUEST_CODE = 9999;
    public static final int RETAIN_FRAGMENT_CONTAINER_ID = android.R.id.content;
    public static final String RETAIN_FRAGMENT_RESPONSE_DATA_KEY = "RETAIN_FRAGMENT_RESPONSE_KEY";

    // Fragment id
    public static final int ALERT_ERROR_CLOSE_APP_REQUEST_CODE = 9990;
    public static final int MAIN_ACTIVITY_FRAGMENT_CONTAINER_ID = R.id.main_activity_fragment_container;


    /**
     * Share Preference Keys
     */
    public static final String SP_APPLICATION_KEY = "SP_APPLICATION_KEY";
    public static final String SP_LANGUAGE_TYPE_ID_KEY = "SP_LANGUAGE_TYPE_ID_KEY";

    /**
     * Others
     */
    public static final String DIALOG_FRAGMENT_CLICK_INDEX_KEY = "DIALOG_FRAGMENT_CLICK_INDEX_KEY";
    public static final int defaultInt = -Integer.MAX_VALUE;


}

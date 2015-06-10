package andycheung.baseproject;

import android.app.Application;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class MyApplication extends Application {

    /**
     * A static consistent class of application Make main control of application
     */
    public static Application application;
    public static RequestQueue requestQueue;


    public MyApplication() {
        MyApplication.application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}

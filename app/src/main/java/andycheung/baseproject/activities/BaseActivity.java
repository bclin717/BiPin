package andycheung.baseproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import andycheung.baseproject.helper.ActivityMonitorHelper;
import andycheung.baseproject.util.LanguagesUtil;
import andycheung.baseproject.util.LogUtil;

/**
 * Created by IGA on 4/6/15.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.lifeLog(this, "onCreate");
        LanguagesUtil.updateLanguage(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.lifeLog(this, "onActivityResult >>> requestCode="
                + requestCode + ", resultCode=" + resultCode + ", data="
                + data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.lifeLog(this, "onResume");
        ActivityMonitorHelper.getInstance().activityResumed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.lifeLog(this, "onPause");
        ActivityMonitorHelper.getInstance().activityPaused();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.lifeLog(this, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.lifeLog(this, "onDestroy");
        System.gc();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.lifeLog(this, "onSaveInstanceState Bundle="
                + (outState == null ? "" : "NOT ") + "null");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.lifeLog(this, "onRestoreInstanceState Bundle="
                + (savedInstanceState == null ? "" : "NOT ") + "null");
    }
}

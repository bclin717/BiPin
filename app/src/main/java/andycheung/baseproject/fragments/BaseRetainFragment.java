package andycheung.baseproject.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import andycheung.baseproject.util.LogUtil;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class BaseRetainFragment extends Fragment {

    private boolean isRetainAlive;

    protected boolean isAllowCallbackTargetFragment() {
        return isRetainAlive;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.lifeLog(this, "Retain - onAttach");
        isRetainAlive = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.lifeLog(this, "Retain - onCreate");
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.lifeLog(this, "Retain - onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.lifeLog(this, "Retain - onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.lifeLog(this, "Retain - onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.lifeLog(this, "Retain - onDetach");
        isRetainAlive = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.lifeLog(this, "Retain - onSaveInstanceState Bundle="
                + (outState == null ? "" : "NOT ") + "null");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.lifeLog(this, "Retain - onActivityCreated Bundle="
                + (savedInstanceState == null ? "" : "NOT ") + "null");
    }
}

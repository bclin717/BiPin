package andycheung.baseproject.fragments.launch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andycheung.baseproject.MyApplication;
import andycheung.baseproject.R;
import andycheung.baseproject.activities.MainActivity;
import andycheung.baseproject.fragments.BaseFragment;
import andycheung.baseproject.util.LogUtil;

/**
 * Created by IGA on 8/6/15.
 */
@SuppressLint("InflateParams")
public class SplashScreenFragment extends BaseFragment {

    private MyApplication application;
    private static final int TIME_LIMIT = 2500;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen_page, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (MyApplication) MyApplication.application;
        displayTimer();
    }

    private void displayTimer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectActivity();
            }
        }, TIME_LIMIT);
    }

    private void redirectActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            LogUtil.log(this, "redirect To Activity");
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            activity.finish();
        }
    }

}

package andycheung.baseproject.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import andycheung.baseproject.util.DataUtil;
import andycheung.baseproject.util.LogUtil;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class BaseFragment extends Fragment {

    //    protected BaseActivityHelper baseActivityHelper;
    protected View baseView;

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener hideSoftKeyboardOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            DataUtil.hideSoftKeyboard(getActivity());
            return false;
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.lifeLog(this, "onAttach");
//        try {
//            baseActivityHelper = (BaseActivityHelper) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement ActivityCommunicationHelper");
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.lifeLog(this, "onCreate");
        //baseActivityHelper.setTopFragment(this);
        onCreateRetainFragment(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.lifeLog(this, "onViewCreated");

        view.setFocusableInTouchMode(true);
        baseView = view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.lifeLog(this, "onActivityResult >>> requestCode="
                + requestCode + ", resultCode=" + resultCode + ", data="
                + data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.lifeLog(this, "onActivityCreated Bundle="
                + (savedInstanceState == null ? "" : "NOT ") + "null");
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.lifeLog(this, "onResume");
//        baseActivityHelper.setTopFragment(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.lifeLog(this, "onPause");
    }

    @Override
    public void onDestroyView() {
        baseView = null;
        super.onDestroyView();
        LogUtil.lifeLog(this, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.lifeLog(this, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.lifeLog(this, "onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.lifeLog(this, "onSaveInstanceState Bundle="
                + (outState == null ? "" : "NOT ") + "null");
    }

    /**
     * OncreateRetainFragment
     *
     * @param savedInstanceState
     */

    protected void onCreateRetainFragment(Bundle savedInstanceState) {
        LogUtil.lifeLog(this, "onCreateRetainFragment");
    }
}

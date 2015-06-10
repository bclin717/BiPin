package andycheung.baseproject.util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import andycheung.baseproject.MyConstant;
import andycheung.baseproject.R;
import andycheung.baseproject.fragments.dialog.AlertDialogFragment;
import andycheung.baseproject.fragments.dialog.CustomeMessageLoadingDialogFragment;
import andycheung.baseproject.helper.ActivityMonitorHelper;

/**
 * Created by Andy Cheung on 11/5/15.
 */
public class DialogUtil {
    /**
     * Loading>>>
     *
     * @param fragmentActivity
     * @param cancelable
     * @return LoadingDialogFragment
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    private static ProgressDialog loadingDialog;

//	public static LoadingDialogFragment showLoadingDialog(//
//			FragmentActivity fragmentActivity,//
//			boolean cancelable) throws InstantiationException,
//			IllegalAccessException {
//		LogUtil.log("Loading === start");
//		return showFragmentDialog(fragmentActivity,
//				LoadingDialogFragment.class, null, 0, null, cancelable);
//	}

    public static CustomeMessageLoadingDialogFragment showCustomeMessageLoadingDialog(
            FragmentActivity fragmentActivity, Bundle bundle, boolean cancelable)
            throws InstantiationException, IllegalAccessException {
        LogUtil.log("Loading === start");
        return showFragmentDialog(fragmentActivity,
                CustomeMessageLoadingDialogFragment.class, null, 0, bundle,
                cancelable);
    }


    public static CustomeMessageLoadingDialogFragment dismissCustomeMessageLoadingDialog(
            FragmentActivity fragmentActivity) {
        LogUtil.log("Loading === XXX");
        return dismissFragmentDialog(fragmentActivity,
                CustomeMessageLoadingDialogFragment.class);
    }

    // <<<Loading

    /**
     * Error>>>
     */
    public static AlertDialogFragment showSimpleErrorDialog(//
                                                            FragmentActivity fragmentActivity, Boolean haveTitle, String errorMsg)
            throws InstantiationException, IllegalAccessException {
        Bundle bundle = AlertDialogFragment.createBundle(
                haveTitle ? fragmentActivity.getString(R.string.error) : null,
                errorMsg);
        return showFragmentDialog(fragmentActivity, AlertDialogFragment.class,
                null, 0, bundle, true);
    }

    public static AlertDialogFragment showErrorAndExitDialog(//
                                                             FragmentActivity fragmentActivity, String errorMsg)
            throws InstantiationException, IllegalAccessException {
        Bundle bundle = AlertDialogFragment.createBundle(
                fragmentActivity.getString(R.string.error),
                errorMsg, fragmentActivity.getString(android.R.string.ok));
        return showFragmentDialog(fragmentActivity, AlertDialogFragment.class,
                null, MyConstant.ALERT_ERROR_CLOSE_APP_REQUEST_CODE, bundle,
                false);
    }

    // <<<VolleyError

    /**
     * Normal dialog>>>
     *
     * @param fragmentActivity
     * @param newFragmentClass
     * @param tagetFragment
     * @param requestCode
     * @param bundle
     * @param cancelable
     * @return <T extends DialogFragment> T
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <T extends DialogFragment> T showFragmentDialog(
            FragmentActivity fragmentActivity, Class<T> newFragmentClass,
            Fragment tagetFragment, int requestCode, Bundle bundle,
            boolean cancelable) throws InstantiationException,
            IllegalAccessException {

        if (fragmentActivity == null)
            return null;

        LogUtil.log("isActivityDestroy dialogloop showFragmentDialog"
                + newFragmentClass);
        dismissFragmentDialog(fragmentActivity, newFragmentClass);

        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        DialogFragment dialogFragment = (DialogFragment) FragmentFlowUtil
                .findOrCreateFragment(fm, newFragmentClass, bundle);
        if (dialogFragment.isAdded()) {
            // FragmentTransaction ft = fm.beginTransaction();
            // ft.remove(dialogFragment);
            // if (ActivityMonitorHelper.getInstance().isActivityVisible()) {
            // ft.commit();
            // } else {
            // ft.commitAllowingStateLoss();
            // }
        } else {
            dialogFragment.setTargetFragment(tagetFragment, requestCode);
            dialogFragment.setCancelable(cancelable);
            try {
                dialogFragment.show(fm, newFragmentClass.getName());
            } catch (Exception e) {
                //TO-DO
                //felix.tam: Temporary solution, need to find out the root cause
                e.printStackTrace();
            }
        }
        return (T) dialogFragment;
    }

    @SuppressWarnings("unchecked")
    public static <T extends DialogFragment> T dismissFragmentDialog(
            FragmentActivity fragmentActivity, Class<T> newFragmentClass) {

        if (fragmentActivity == null)
            return null;

        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        if (fm == null)
            return null;

        DialogFragment dialogFragment = (DialogFragment) fm
                .findFragmentByTag(newFragmentClass.getName());

        if (dialogFragment == null)
            return null;

        LogUtil.log("isActivityDestroy dialogloop dismissFragmentDialog "
                + dialogFragment);

        try {
            if (ActivityMonitorHelper.getInstance().isActivityVisible()) {
                dialogFragment.dismiss();
            } else {
                dialogFragment.dismissAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) dialogFragment;
    }

    public static int getDialogClickedBtnIndex(Intent data) {
        if (data != null) {
            return data.getIntExtra(MyConstant.DIALOG_FRAGMENT_CLICK_INDEX_KEY,
                    MyConstant.defaultInt);
        }
        return MyConstant.defaultInt;
    }
    // <<<Normal dialog

    public static void showLoadingProgressDialog(FragmentActivity fragmentActivity) {
        if (loadingDialog != null)
            dismissLoadingProgressDialog();
        loadingDialog = ProgressDialog.show(fragmentActivity, "",
                "Loading", true);
        LogUtil.log("LoadingDialog === start");
    }

    public static void dismissLoadingProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
        LogUtil.log("LoadingDialog === dismiss");
    }

}

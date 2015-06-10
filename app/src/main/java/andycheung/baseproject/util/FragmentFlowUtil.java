package andycheung.baseproject.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import andycheung.baseproject.MyConstant;
import andycheung.baseproject.fragments.BaseRetainFragment;
import andycheung.baseproject.helper.ActivityMonitorHelper;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class FragmentFlowUtil {
        /**
         * normal
         */

        @SuppressWarnings("unchecked")
        public static <T extends Fragment> T findOrCreateFragment(
                FragmentManager fm, Class<T> newFragmentClass, Bundle bundle)
                throws InstantiationException, IllegalAccessException {
                String fragmentTag = newFragmentClass.getName();
                // try to find from fm
                Fragment newFragment = fm.findFragmentByTag(fragmentTag);
                if (newFragment == null) {
                        newFragment = newFragmentClass.newInstance();
                        if (bundle != null) {
                                newFragment.setArguments(bundle);
                        }
                } else {
                        if (newFragment.getArguments() == null) {
                                if (bundle != null) {
                                        newFragment.setArguments(bundle);
                                }
                        } else {
                                newFragment.getArguments().clear();
                                if (bundle != null) {
                                        newFragment.getArguments().putAll(bundle);
                                }
                        }
                }
                return (T) newFragment;
        }

        @SuppressWarnings("unchecked")
        public static <T extends Fragment> T commitFragment(FragmentManager fm,
                                                            Class<T> newFragmentClass, Bundle bundle, int containerId,
                                                            boolean toBackStack, Fragment targetFragment, int requestCode)
                throws InstantiationException, IllegalAccessException {

                // try to find from fm
                Fragment newFragment = findOrCreateFragment(fm, newFragmentClass,
                        bundle);
                newFragment.setTargetFragment(targetFragment, requestCode);

                String fragmentTag = newFragment.getClass().getName();

                // Tran
                FragmentTransaction ft = removeAllRetainFragment(fm,
                        fm.beginTransaction());
                ft.replace(containerId, newFragment, fragmentTag);
                if (toBackStack) {
                        ft.addToBackStack(fragmentTag);
                }
                if (ActivityMonitorHelper.getInstance().isActivityVisible()) {
                        ft.commit();
                } else {
                        ft.commitAllowingStateLoss();
                }

                return (T) newFragment;
        }

        /**
         * Retain
         *
         * @return
         */
        public static FragmentTransaction removeAllRetainFragment(
                FragmentManager fm, FragmentTransaction ft) {//
                if (!DataUtil.isEmpty(fm.getFragments())) {
                        for (Fragment fragment : fm.getFragments()) {
                                if (fragment instanceof BaseRetainFragment) {
                                        ft.remove(fragment);
                                }
                        }
                }
                return ft;
        }

        @SuppressWarnings("unchecked")
        public static <T extends Fragment> T commitRetainFragment(
                FragmentManager fm, Class<T> newFragmentClass,
                Fragment targetFragment) throws InstantiationException,
                IllegalAccessException {//

                // try to find from fm
                Fragment newFragment = findOrCreateFragment(fm, newFragmentClass, null);
                newFragment.setTargetFragment(targetFragment,
                        MyConstant.RETAIN_FRAGMENT_REQUEST_CODE);

                String fragmentTag = newFragment.getClass().getName();

                // Tran
                FragmentTransaction ft = removeAllRetainFragment(fm,
                        fm.beginTransaction());
                // ft.remove(newFragment);
                ft.replace(MyConstant.RETAIN_FRAGMENT_CONTAINER_ID, newFragment,
                        fragmentTag);
                if (ActivityMonitorHelper.getInstance().isActivityVisible()) {
                        ft.commit();
                } else {
                        ft.commitAllowingStateLoss();
                }
                return (T) newFragment;
        }

        /**
         * pop
         */

        public static void popBackStackToTargetFragment(
                FragmentManager fragmentManager,
                Class<? extends Fragment> targetClazz) {
                fragmentManager.popBackStack(targetClazz.getSimpleName(), 0);
        }

        public static void popBackStack(FragmentManager fragmentManager,
                                        Activity activity) {
                // reference
                // http://grepcode.com/search/usages?type=method&id=repository.grepcode.com%24java%24ext@com.google.android%24android@4.0.1_r1@android%24support%24v4%24app@FragmentManager@popBackStackImmediate%28%29&k=u
                if (!fragmentManager.popBackStackImmediate() && activity != null) {
                        // TODO finish?
                        activity.finish();
                }
        }

        public static void popBackToFirstFragment(FragmentManager fragmentManager) {
                fragmentManager.popBackStack(null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }


}

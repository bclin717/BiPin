package andycheung.baseproject.fragments.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import andycheung.baseproject.MyConstant;
import andycheung.baseproject.activities.BaseActivity;


/**
 * Created by IGA on 5/6/15.
 */
public class AlertDialogFragment extends BaseDialogFragment {

    private final int resultCode = Activity.RESULT_OK;

    private final static String ICON_RES_ID = "ICON_RES_ID";
    private final static String TITLE_STRING = "TITLE_STRING";
    private final static String MESSAGE_STRING = "MESSAGE_STRING";
    private final static String BTNS_LABEL = "BTNS_LABEL";

    private DialogInterface.OnClickListener btnClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int requestCode = getTargetRequestCode();
            Intent data = new Intent();
            data.putExtra(MyConstant.DIALOG_FRAGMENT_CLICK_INDEX_KEY, which);
            //
            Fragment targetFragment = getTargetFragment();
            if (targetFragment != null) {
                targetFragment.onActivityResult(requestCode, resultCode, data);
            } else {
                ((BaseActivity) getActivity()).onActivityResult(requestCode,
                        resultCode, data);
            }
        }
    };

    public static Bundle createBundle(String titleString, String messageString,
                                      String... btnLabels) {
        return createBundle(MyConstant.defaultInt, titleString, messageString,
                btnLabels);
    }

    public static Bundle createBundle(int iconResId, String titleString,
                                      String messageString, String... btnLabels) {
        Bundle bundle = new Bundle();
        bundle.putInt(ICON_RES_ID, iconResId);
        bundle.putString(TITLE_STRING, titleString);
        bundle.putString(MESSAGE_STRING, messageString);
        bundle.putStringArray(BTNS_LABEL, btnLabels);
        return bundle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return new Dialog(getActivity());
        }

        int iconId = bundle.getInt(ICON_RES_ID, MyConstant.defaultInt);
        String title = bundle.getString(TITLE_STRING);
        String message = bundle.getString(MESSAGE_STRING);
        String[] btns = bundle.getStringArray(BTNS_LABEL);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());

        if (MyConstant.defaultInt != iconId) {
            dialogBuilder.setIcon(iconId);
        }

        if (title != null) {
            final TextView titleView = new TextView(getActivity());
            titleView.setText(title);
            dialogBuilder.setCustomTitle(titleView);
        }

        if (message != null) {
            dialogBuilder.setMessage(message);
        }

        if (btns != null) {
            switch (btns.length) {
                case 1:
                    dialogBuilder.setPositiveButton(btns[0], btnClick);
                    break;
                case 2:
                    dialogBuilder.setPositiveButton(btns[0], btnClick);
                    dialogBuilder.setNegativeButton(btns[1], btnClick);
                    break;
                case 3:
                    dialogBuilder.setPositiveButton(btns[0], btnClick);
                    dialogBuilder.setNeutralButton(btns[1], btnClick);
                    dialogBuilder.setNegativeButton(btns[2], btnClick);
                    break;
                default:
                    dialogBuilder.setPositiveButton(android.R.string.ok, null);
                    break;
            }
        }

        AlertDialog dialog = dialogBuilder.create();

        //JIRA DPIAS-857 (Attempt to fix, not guarantee and fixed)
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        dialog.show();



        return dialog;
    }
}

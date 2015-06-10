package andycheung.baseproject.fragments.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import andycheung.baseproject.util.DataUtil;


/**
 * Created by Andy Cheung on 11/5/15.
 */
public class CustomeMessageLoadingDialogFragment extends BaseDialogFragment {

    private final static String TITLE_STRING = "TITLE_STRING";
    private final static String MESSAGE_STRING = "MESSAGE_STRING";

    public static Bundle createBundle(String title, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_STRING, title);
        bundle.putString(MESSAGE_STRING, message);
        return bundle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        String title = bundle.getString(TITLE_STRING);
        String message = bundle.getString(MESSAGE_STRING);
        if (!DataUtil.isEmpty(title)) {
            progressDialog.setTitle(title);
        }
        progressDialog.setMessage(message);

        //JIRA DPIAS-857 (Attempt to fix, not guarantee and fixed)
        Window window = progressDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        progressDialog.show();
        return progressDialog;
    }
}

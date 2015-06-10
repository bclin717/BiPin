package andycheung.baseproject.fragments.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by IGA on 5/6/15.
 */
public class BaseDialogFragment  extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    };

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

}

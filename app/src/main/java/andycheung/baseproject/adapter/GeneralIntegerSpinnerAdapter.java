package andycheung.baseproject.adapter;

import android.content.Context;
import android.widget.CheckedTextView;

import andycheung.baseproject.pojo.ListData;


/**
 * Created by IGA on 4/6/15.
 */
public class GeneralIntegerSpinnerAdapter extends
        SuperSpinnerAdapter<ListData<Integer>> {

    public GeneralIntegerSpinnerAdapter(Context context) {
        super(context);
    }

    @Override
    protected void handleTextviewContent(int position,
                                         CheckedTextView checkedTextView) {
        checkedTextView.setText(getItem(position).data + "");
    }
}

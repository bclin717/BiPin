package andycheung.baseproject.adapter;

import android.content.Context;
import android.widget.CheckedTextView;

import andycheung.baseproject.pojo.ListData;


/**
 * Created by IGA on 4/6/15.
 */
public class GeneralStringSpinnerAdapter extends
        SuperSpinnerAdapter<ListData<String>> {

    public GeneralStringSpinnerAdapter(Context context) {
        this(context, 0);
    }

    public GeneralStringSpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    protected void handleTextviewContent(int position,
                                         CheckedTextView checkedTextView) {
        checkedTextView.setText(getItem(position).data);
    }
}


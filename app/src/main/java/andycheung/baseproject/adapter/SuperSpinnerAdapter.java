package andycheung.baseproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import andycheung.baseproject.R;
import andycheung.baseproject.pojo.ListData;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public abstract class SuperSpinnerAdapter<T extends ListData<?>> extends
        SuperAdapter<T> {
    public static final int TYPE_HINTS_ROW = 101;
    public static final int TYPE_TITLE_ROW = 102;

    protected abstract void handleTextviewContent(int position,
                                                  CheckedTextView checkedTextView);

    public SuperSpinnerAdapter(Context context) {
        this(context, 0);
    }

    public SuperSpinnerAdapter(Context context, int resource) {
        super(context, resource);
        addItemType(TYPE_HINTS_ROW);
        addItemType(TYPE_TITLE_ROW);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return handleGetView(false, position, convertView);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return handleGetView(true, position, convertView);
    }

    @SuppressWarnings("unchecked")
    private View handleGetView(boolean isDropDownView, int position,
                               View convertView) {
        int typeId = typeIdList.get(getItemViewType(position));

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder(isDropDownView ? SUPER_TYPE_GENERAL
                    : typeId);
            convertView = holder.checkedTextView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (typeId) {
            case TYPE_HINTS_ROW:
                holder.checkedTextView.setText(R.string.global_spinner_please_select);
                break;
            default:
                handleTextviewContent(position, holder.checkedTextView);
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        CheckedTextView checkedTextView;

        public ViewHolder(int typeId) {
            checkedTextView = (CheckedTextView) LayoutInflater.from(
                    getContext()).inflate(
                    R.layout.item_spinner_dropdown_checkedtextview, null);

            switch (typeId) {
                case TYPE_HINTS_ROW:
                    checkedTextView.setTextColor(getContext().getResources()
                            .getColor(R.color.gray_text_hints_color));
                    break;
                case TYPE_TITLE_ROW:

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        int typeId = typeIdList.get(getItemViewType(position));
        if (typeId == TYPE_TITLE_ROW) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

}

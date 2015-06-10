package andycheung.baseproject.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import andycheung.baseproject.pojo.ListData;


/**
 * Created by Andy Cheung on 4/5/15.
 */
public class SuperAdapter<T extends ListData<?>> extends ArrayAdapter<T> {

    public static final int SUPER_TYPE_GENERAL = 0;

    protected List<Integer> typeIdList = new ArrayList<Integer>();

    public SuperAdapter(Context context) {
        this(context, 0);
    }

    public SuperAdapter(Context context, int resource) {
        super(context, resource);
        addItemType(SUPER_TYPE_GENERAL);
    }

    @SuppressWarnings("unchecked")
    public <I> void addListData(int type, I item) {
        super.add((T) new ListData<I>(type, item));
    }

    @SuppressWarnings("unchecked")
    public <I> void addAllListDatas(I... items) {
        for (I item : items) {
            super.add((T) new ListData<I>(SUPER_TYPE_GENERAL, item));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return typeIdList.indexOf(getItem(position).typeId);
    }

    @Override
    public int getViewTypeCount() {
        return typeIdList.size();
    }

    protected void addItemType(int... types) {
        if (types != null) {
            for (int i : types) {
                typeIdList.add(i);
            }
        }
    }
}

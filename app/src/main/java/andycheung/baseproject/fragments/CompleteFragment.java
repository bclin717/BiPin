package andycheung.baseproject.fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android4devs.navigationdrawer.MyAdapter;

import java.util.LinkedList;
import java.util.List;

import andycheung.baseproject.R;
/**
 * Created by IGA on 10/6/15.
 */
public class CompleteFragment extends BaseFragment {
    private static final String[] mStrings = new String[] {
            "大餅包小餅", "蚵仔煎"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_complete, container, false);

        ListView complete = (ListView) view.findViewById(R.id.fragemnt_complete_listView);
        complete.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, mStrings));
        return view;
    }
}
package edu.fst.m2.ipii.outonight.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.ui.adapter.DrawerListAdapter;


/**
 * Created by dlevel on 04/02/2015.
 */
public class DrawerFragment extends Fragment {

    @InjectView(R.id.drawer_listview) ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        ButterKnife.inject(this, view);

        DrawerListAdapter adapter = new DrawerListAdapter(getActivity());
        listView.setAdapter(adapter);

        View header = inflater.inflate(R.layout.headerview, listView, false);
        listView.addHeaderView(header, null, false);

        return view;
    }


    @OnItemClick(R.id.drawer_listview) void onItemClick(int position) {
        Toast.makeText(getActivity(), "Clicked position " + position + "!", Toast.LENGTH_SHORT).show();
    }
}

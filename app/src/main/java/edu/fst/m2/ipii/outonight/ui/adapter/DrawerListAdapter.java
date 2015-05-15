package edu.fst.m2.ipii.outonight.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.ui.DrawerItem;
import edu.fst.m2.ipii.outonight.ui.adapter.cell.DrawerItemViewHolder;

public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<DrawerItem> dataSource = new ArrayList<>();

    public DrawerListAdapter(Context context) {
        this.context = context;

        dataSource.add(new DrawerItem(R.drawable.ic_profile, "Profil"));
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView = convertView;
        DrawerItemViewHolder viewHolder;
        DrawerItem drawerItem = dataSource.get(position);
        if (cellView == null) {
            viewHolder = new DrawerItemViewHolder(context);
            cellView = viewHolder.getView();
            cellView.setTag(viewHolder);
        }
        viewHolder = (DrawerItemViewHolder) cellView.getTag();
        viewHolder.updateView(drawerItem);
        return cellView;
    }
}

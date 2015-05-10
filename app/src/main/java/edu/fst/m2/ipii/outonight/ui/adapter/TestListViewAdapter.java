package edu.fst.m2.ipii.outonight.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.ui.adapter.cell.EstablishmentItemViewHolder;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestListViewAdapter extends ArrayAdapter<Establishment> {

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    private Context context;
    private List<Establishment> dataSource;

    public TestListViewAdapter(Context context, List<Establishment> objects) {
        super(context, 0, objects);
        this.context = context;
        this.dataSource = objects;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View cellView;
        Establishment establishment;
        EstablishmentItemViewHolder viewHolder;

        cellView = convertView;
        establishment = dataSource.get(position);
        if (cellView == null) {
            viewHolder = new EstablishmentItemViewHolder(context);
            cellView = viewHolder.getView();
            cellView.setTag(viewHolder);
        }
        viewHolder = (EstablishmentItemViewHolder) cellView.getTag();
        viewHolder.updateView(establishment);
        return cellView;*/

        if(convertView == null) {
            switch (getItemViewType(position)) {
                case TYPE_HEADER: {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.tools_list_item_card_big, parent, false);
                }
                break;
                case TYPE_CELL: {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.tools_list_item_card_small, parent, false);
                }
                break;
            }
        }
        return convertView;
    }
}
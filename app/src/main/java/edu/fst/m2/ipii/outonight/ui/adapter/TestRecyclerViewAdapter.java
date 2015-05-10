package edu.fst.m2.ipii.outonight.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Contact;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.ui.adapter.cell.EstablishmentItemViewHolder;


public class TestRecyclerViewAdapter extends RecyclerView.Adapter<EstablishmentItemViewHolder> {

    private List<? extends Establishment> datasource;
    private Context context;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;



    public TestRecyclerViewAdapter(List<Establishment> dataSource, Context context) {
        this.datasource = dataSource;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (datasource.get(position).isFeatured()) {
            return TYPE_HEADER;
        }
        else {
            return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    @Override
    public EstablishmentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        /*Establishment establishment = new Nightclub();
        establishment.setName("Nightclub factice");
        EstablishmentItemViewHolder establishmentItemViewHolder = new EstablishmentItemViewHolder(context);
        establishmentItemViewHolder.updateView(establishment);

        return establishmentItemViewHolder;*/

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new EstablishmentItemViewHolder(view, context) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new EstablishmentItemViewHolder(view, context) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(EstablishmentItemViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }

        holder.updateView(datasource.get(position));
    }
}
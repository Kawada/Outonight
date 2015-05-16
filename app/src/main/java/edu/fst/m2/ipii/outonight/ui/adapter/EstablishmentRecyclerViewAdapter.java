package edu.fst.m2.ipii.outonight.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.dto.type.AmbienceType;
import edu.fst.m2.ipii.outonight.dto.type.CookingType;
import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.dto.type.MusicType;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;
import edu.fst.m2.ipii.outonight.ui.activity.MainActivity;
import edu.fst.m2.ipii.outonight.ui.adapter.cell.EstablishmentItemViewHolder;
import edu.fst.m2.ipii.outonight.ui.fragment.RecyclerViewFragment;
import edu.fst.m2.ipii.outonight.ws.EstablishmentApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EstablishmentRecyclerViewAdapter extends RecyclerView.Adapter<EstablishmentItemViewHolder> {



    private List<Establishment> datasource;
    private Context context;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public EstablishmentRecyclerViewAdapter(List<Establishment> datasource, final Context context) {
        this.datasource = datasource;
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

    public List<Establishment> getDatasource() {
        return datasource;
    }
}
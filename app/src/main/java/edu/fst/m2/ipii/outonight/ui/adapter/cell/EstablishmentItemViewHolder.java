package edu.fst.m2.ipii.outonight.ui.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.ui.activity.MapsActivity;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class EstablishmentItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @InjectView(R.id.name_textview) TextView nameTextView;
    //@InjectView(R.id.type_textview) TextView typeTextView;
    //@InjectView(R.id.photo_imageview) ImageView photoView;

    private Context context;

    public EstablishmentItemViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        view.setOnClickListener(this);
        createView();
    }

    private void createView() {
        //establishmentView = LayoutInflater.from(context).inflate(R.layout.establishment_item, null);
        ButterKnife.inject(this, itemView);
    }

    public void updateView(Establishment establishment) {
        nameTextView.setText(establishment.getName());

        // Type, photo...
    }

    public View getView() {
        return itemView;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(context, MapsActivity.class);
        //context.startActivity(intent);
    }
}

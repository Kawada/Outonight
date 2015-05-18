package edu.fst.m2.ipii.outonight.ui.adapter.cell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.utils.BitmapUtils;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class EstablishmentItemViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.name_textview) TextView nameTextView;
    @InjectView(R.id.id_textview) TextView idTextView;
    @InjectView(R.id.star_imageview) ImageView starImageView;
    @InjectView(R.id.photo) ImageView photoView;

    private Map<Integer, Bitmap> bitmapCache;

    private Context context;

    public EstablishmentItemViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        bitmapCache = new HashMap<>();
        createView();
    }

    private void createView() {
        ButterKnife.inject(this, itemView);
    }

    public void updateView(final Establishment establishment) {
        nameTextView.setText(establishment.getName());
        idTextView.setText(Integer.toString(establishment.getEstablishmentId()));

        if (establishment.isStared()) {
            starImageView.setVisibility(View.VISIBLE);
        }
        else {
            starImageView.setVisibility(View.INVISIBLE);
        }

        Log.d(toString(), "photo de " + establishment.getName() + " : " + establishment.getPhoto());

        // Chargement de l'image et mise en cache RAM
        Picasso.with(context).load(WebserviceConstants.WS_IMG_URL + "/" + establishment.getPhoto()).into(photoView, new Callback() {
            @Override
            public void onSuccess() {
                BitmapUtils.sPhotoCache.put(establishment.getEstablishmentId(), ((BitmapDrawable) photoView.getDrawable()).getBitmap());
            }

            @Override
            public void onError() {
                Log.e(toString(), "Erreur Picasso pour l'image " + establishment.getPhoto());
            }
        });

    }

    public View getView() {
        return itemView;
    }
}

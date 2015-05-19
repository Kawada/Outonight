package edu.fst.m2.ipii.outonight.ui.adapter.cell;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.constants.BundleArguments;
import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.activity.DetailActivity;
import edu.fst.m2.ipii.outonight.utils.BitmapUtils;
import edu.fst.m2.ipii.outonight.utils.CroutonUtils;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class EstablishmentItemViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.name_textview) TextView nameTextView;
    @InjectView(R.id.id_textview) TextView idTextView;
    @InjectView(R.id.star_imageview) ImageView starImageView;
    @InjectView(R.id.photo) ImageView photoView;

    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    private Context context;

    public EstablishmentItemViewHolder(View view, Context context) {
        super(view);
        this.context = context;
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

    /**
     * Evénement lancé lors du click sur un élement de la RecyclerView
     * @param view
     */
    @OnClick(R.id.name_textview)
    public void showDetail(View view) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);

        View parent = (View) view.getParent();

        TextView textView = (TextView) parent.findViewById(R.id.id_textview);
        int establishmentId = Integer.valueOf(textView.getText().toString());

        Establishment establishment = establishmentCacheService.getCached(establishmentId);

        if (establishment == null) {
            Log.e(toString(), "Erreur lors de la récupération d'un établissement.");
            CroutonUtils.displayErrorMessage((Activity) context, R.string.msg_err_detail_access);
            return;
        }


        intent.putExtra(BundleArguments.BUNDLE_ESTABLISHMENT_ID, establishment.getEstablishmentId());

        ImageView hero = (ImageView) parent.findViewById(R.id.photo);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation((Activity) context, hero, "photo_hero");
        ((Activity) context).startActivityForResult(intent, DetailActivity.REQUEST_ESTABLISHMENT_ID, options.toBundle());
    }
}

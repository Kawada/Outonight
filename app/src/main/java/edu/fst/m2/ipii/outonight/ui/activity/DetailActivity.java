/**
 * Copyright 2014 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.fst.m2.ipii.outonight.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.view.WindowInsets;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.view.AnimatedPathView;
import edu.fst.m2.ipii.outonight.ui.view.TransitionAdapter;
import edu.fst.m2.ipii.outonight.utils.BitmapUtils;
import edu.fst.m2.ipii.outonight.utils.CroutonUtils;
import edu.fst.m2.ipii.outonight.utils.FeaturesUtils;
import icepick.Icepick;

public class DetailActivity extends Activity {

    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    @InjectView(R.id.title)
    TextView titleView;
    @InjectView(R.id.description)
    TextView descriptionView;
    @InjectView(R.id.star_container)
    AnimatedPathView starContainer ;

    protected Establishment establishment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        int establishmentId = getIntent().getIntExtra("establishmentId", 0);
        establishment = establishmentCacheService.getCached(establishmentId);

        Bitmap photo = setupPhoto(getIntent().getIntExtra("photo", R.drawable.nightclub_header_thumb));

        colorize(photo);

        setupMap();
        setupText();

        setOutlines(R.id.star, R.id.info, R.id.call);
        applySystemWindowsBottomInset(R.id.container);

        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                ImageView hero = (ImageView) findViewById(R.id.photo);
                ObjectAnimator color = ObjectAnimator.ofArgb(hero.getDrawable(), "tint",
                        getResources().getColor(R.color.photo_tint), 0);
                color.start();

                findViewById(R.id.info).animate().alpha(1.0f);
                findViewById(R.id.star).animate().alpha(1.0f);
                findViewById(R.id.call).animate().alpha(1.0f);

                getWindow().getEnterTransition().removeListener(this);

                if (establishment.isStared()) {
                    toggleStarView(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        ImageView hero = (ImageView) findViewById(R.id.photo);
        ObjectAnimator color = ObjectAnimator.ofArgb(hero.getDrawable(), "tint",
                0, getResources().getColor(R.color.photo_tint));
        color.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finishAfterTransition();
            }
        });
        color.start();

        findViewById(R.id.info).animate().alpha(0.0f);
        findViewById(R.id.star).animate().alpha(0.0f);
        findViewById(R.id.call).animate().alpha(0.0f);
    }

    private void setupText() {
        titleView.setText(establishment.getName());
        descriptionView.setText(establishment.getDescription());
    }

    private void setupMap() {
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        double lat = establishment.getAddress().getLat();
        double lng = establishment.getAddress().getLng();
        float zoom = 14.0f;

        LatLng position = new LatLng(lat, lng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
        map.addMarker(new MarkerOptions().position(position));
    }

    private void setOutlines(int star, int info, int call) {
        final int size = getResources().getDimensionPixelSize(R.dimen.floating_button_size);

        final ViewOutlineProvider vop = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, size, size);
            }
        };

        findViewById(star).setOutlineProvider(vop);
        findViewById(info).setOutlineProvider(vop);
        findViewById(call).setOutlineProvider(vop);
    }

    private void applySystemWindowsBottomInset(int container) {
        View containerView = findViewById(container);
        containerView.setFitsSystemWindows(true);
        containerView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                if (metrics.widthPixels < metrics.heightPixels) {
                    view.setPadding(0, 0, 0, windowInsets.getSystemWindowInsetBottom());
                } else {
                    view.setPadding(0, 0, windowInsets.getSystemWindowInsetRight(), 0);
                }
                return windowInsets.consumeSystemWindowInsets();
            }
        });
    }

    private void colorize(Bitmap photo) {
        try {
            Palette palette = Palette.generate(photo);
            applyPalette(palette);
        }
        catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }

    }

    private void applyPalette(Palette palette) {

        int defaultTextColorId = R.color.red;
        int defaultBackgroundColorId = R.color.red;

        getWindow().setBackgroundDrawable(new ColorDrawable(palette.getDarkMutedColor(defaultBackgroundColorId)));

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setTextColor(palette.getVibrantColor(defaultTextColorId));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setTextColor(palette.getLightVibrantColor(defaultTextColorId));

        /*colorRipple(R.id.info, palette.getDarkMutedColor(0),
                palette.getDarkVibrantColor(0));*/
        colorRipple(R.id.info, palette.getMutedColor(defaultTextColorId),
                palette.getVibrantColor(defaultTextColorId));
        colorRipple(R.id.star, palette.getMutedColor(defaultTextColorId),
                palette.getVibrantColor(defaultTextColorId));
        colorRipple(R.id.call, palette.getMutedColor(defaultTextColorId),
                palette.getVibrantColor(defaultTextColorId));

        View infoView = findViewById(R.id.information_container);
        infoView.setBackgroundColor(palette.getLightMutedColor(defaultBackgroundColorId));

        AnimatedPathView star = (AnimatedPathView) findViewById(R.id.star_container);
        star.setFillColor(palette.getVibrantColor(defaultBackgroundColorId));
        star.setStrokeColor(palette.getLightVibrantColor(defaultTextColorId));
    }

    private void colorRipple(int id, int bgColor, int tintColor) {
        View buttonView = findViewById(id);

        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(bgColor);

        ripple.setColor(ColorStateList.valueOf(tintColor));
    }

    private Bitmap setupPhoto(int resource) {
        Bitmap bitmap = BitmapUtils.sPhotoCache.get(resource);
        ((ImageView) findViewById(R.id.photo)).setImageBitmap(bitmap);
        return bitmap;
    }

    public void showStar(View view) {
        toggleStarView(true);
    }

    public void callEstablishment(View view) {
        if (FeaturesUtils.isFeatureAvailable(this, PackageManager.FEATURE_TELEPHONY)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + establishment.getContact().getPhone()));
            startActivity(intent);
        }
        else {
            CroutonUtils.displayErrorMessage(this, R.string.msg_err_cant_call);
        }
    }

    private void toggleStarView(final boolean update) {

        if (starContainer.getVisibility() == View.INVISIBLE) {
            findViewById(R.id.photo).animate().alpha(0.2f);
            starContainer.setAlpha(1.0f);
            starContainer.setVisibility(View.VISIBLE);
            starContainer.reveal();

            if (update) {
                // Si etoile visible : true
                establishment.setStared(true);
                establishment.save();
            }

        } else {
            findViewById(R.id.photo).animate().alpha(1.0f);
            starContainer.animate().alpha(0.0f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    starContainer.setVisibility(View.INVISIBLE);
                    if (update) {
                        // Si etoile visible : true
                        establishment.setStared(false);
                        establishment.save();
                    }
                }
            });
        }
        
    }

    public void showInformation(View view) {
        toggleInformationView(view);
    }

    private void toggleInformationView(View view) {

        final View infoContainer = findViewById(R.id.information_container);

        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        float radius = Math.max(infoContainer.getWidth(), infoContainer.getHeight()) * 2.0f;

        Animator reveal;
        final String titleText;
        final String descText;

        if (infoContainer.getVisibility() == View.INVISIBLE) {
            infoContainer.setVisibility(View.VISIBLE);
            reveal = ViewAnimationUtils.createCircularReveal(
                    infoContainer, cx, cy, 0, radius);
            reveal.setInterpolator(new AccelerateInterpolator(2.0f));

            titleText = establishment.getAddress().getLine1();
            descText = establishment.getAddress().getCity();

            if (starContainer.getVisibility() == View.VISIBLE) {
                toggleStarView(false);
            }
        } else {
            reveal = ViewAnimationUtils.createCircularReveal(
                    infoContainer, cx, cy, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    infoContainer.setVisibility(View.INVISIBLE);
                }
            });
            reveal.setInterpolator(new DecelerateInterpolator(2.0f));
            if (establishment.isStared()) {
                toggleStarView(false);
            }

            titleText = establishment.getName();
            descText = establishment.getDescription();
        }
        reveal.setDuration(600);
        reveal.start();

        switchText(titleText, descText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void switchText(final String titleText, final String descText) {
        // durée de fondu
        int fadeDuration = 300;

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(fadeDuration);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(fadeDuration);

        out.setAnimationListener(new Animation.AnimationListener() {

            /**
             * <p>Notifies the start of the animation.</p>
             *
             * @param animation The started animation.
             */
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                titleView.setText(titleText);
                titleView.startAnimation(in);

                descriptionView.setText(descText);
                descriptionView.startAnimation(in);

            }

            /**
             * <p>Notifies the repetition of the animation.</p>
             *
             * @param animation The animation which was repeated.
             */
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        titleView.startAnimation(out);
        descriptionView.startAnimation(out);
    }
}

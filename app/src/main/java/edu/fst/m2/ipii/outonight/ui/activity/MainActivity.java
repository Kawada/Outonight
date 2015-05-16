package edu.fst.m2.ipii.outonight.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.adapter.TabPagerAdapter;
import edu.fst.m2.ipii.outonight.utils.CroutonUtils;
import icepick.Icepick;


public class MainActivity extends ActionBarActivity {


    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    @InjectView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    @InjectView(R.id.drawer_layout) DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);

        setTitle("");

        if (!isNetworkAvailable()) {
            CroutonUtils.displayErrorMessage(this, R.string.msg_err_no_connection);
        }

        toolbar = mViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mViewPager.getViewPager().setAdapter(new TabPagerAdapter(this, getSupportFragmentManager()));
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void showDetail(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DetailActivity.class);

        View parent = (View) view.getParent();

        TextView textView = (TextView) parent.findViewById(R.id.name_textview);
        String establishmentName = textView.getText().toString();

        Establishment establishment;

        try {
            establishment = establishmentCacheService.getCachedByName(establishmentName).get(0);
        }
        catch (IndexOutOfBoundsException exception) {
            Log.e(toString(), "Erreur lors de la récupération d'un établissement : " + exception.getMessage(), exception);
            CroutonUtils.displayErrorMessage(this, R.string.msg_err_detail_access);
            return;
        }


        intent.putExtra("establishmentId", establishment.getEstablishmentId());

        intent.putExtra("photo", establishment.getEstablishmentId());

        ImageView hero = (ImageView) parent.findViewById(R.id.photo);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, hero, "photo_hero");
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_maps) {
            Toast.makeText(this, "Transfert vers le cartes...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapsActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    public MaterialViewPager getmViewPager() {
        return mViewPager;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

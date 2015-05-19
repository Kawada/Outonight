package edu.fst.m2.ipii.outonight.ui.activity;

import android.app.Activity;
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

import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.constants.BundleArguments;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.adapter.TabPagerAdapter;
import edu.fst.m2.ipii.outonight.ui.fragment.RecyclerViewFragment;
import edu.fst.m2.ipii.outonight.utils.CroutonUtils;


public class MainActivity extends ActionBarActivity {


    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    @InjectView(R.id.material_view_pager)
    MaterialViewPager mViewPager;

    @InjectView(R.id.drawer_layout) DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setTitle("");

        if (!isNetworkAvailable()) {
            // Si aucun réseau dispo, notification
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

        // Click sur l'icone carte
        if (item.getItemId() == R.id.action_maps) {
            Intent intent = new Intent(this, MapsActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (DetailActivity.REQUEST_ESTABLISHMENT_ID) : {
                if (resultCode == Activity.RESULT_OK) {

                    try {
                        Establishment establishment = establishmentCacheService.getCached(data.getIntExtra(BundleArguments.BUNDLE_ESTABLISHMENT_ID, 1));

                        int i = 0;

                        for (RecyclerViewFragment recyclerViewFragment : ((TabPagerAdapter) mViewPager.getViewPager().getAdapter()).getFragments()) {

                                // On essaie de mettre à jour la liste des éléments sélectionnés
                                // Première condition : Si on est à l'onglet "Select.", alors si l'établissement est stared ou featured, on l'ajoute ou le met à jour
                                // Deuxième condition : Dans les autres listes, si l'objet est présent, on met à jour
                                if ((i == 0 && (establishment.isStared() || establishment.isFeatured())) || (i != 0 && recyclerViewFragment.getDataSource().contains(establishment))) {

                                    recyclerViewFragment.addOrUpdate(establishment, true);

                                    // int anciennePosition = recyclerViewFragment.getDataSource().indexOf(establishment);

                                    // tri de la liste
                                    Collections.sort(recyclerViewFragment.getDataSource(), new Comparator<Establishment>() {
                                        @Override
                                        public int compare(Establishment lhs, Establishment rhs) {

                                            int sComp = ((Boolean) rhs.isStared()).compareTo(lhs.isStared());

                                            if (sComp != 0) {
                                                return sComp;
                                            } else {
                                                return lhs.getName().compareToIgnoreCase(rhs.getName());
                                            }
                                        }
                                    });

                                    // int nouvellePosition = recyclerViewFragment.getDataSource().indexOf(establishment);

                                    // Log.d(toString(), "Ancienne pos : " + anciennePosition + "; nouvelle pos : " + nouvellePosition);
                                    // Décommenter et commenter le notifyDataSetChanged pour avoir des animation (mécanisme buggé --> doublons)
                                    // recyclerViewFragment.getmAdapter().notifyItemRemoved(anciennePosition + 1);
                                    // recyclerViewFragment.getmAdapter().notifyItemInserted(nouvellePosition + 1);

                                    recyclerViewFragment.getmAdapter().notifyDataSetChanged();
                                }
                                // Cas où on supprime un élément de la liste "Select."
                                else if (i == 0 && recyclerViewFragment.getDataSource().contains(establishment)) {
                                    recyclerViewFragment.getDataSource().remove(establishment);
                                    recyclerViewFragment.getmAdapter().notifyDataSetChanged();
                                }

                            i++;

                        }

                    }
                    catch (IndexOutOfBoundsException|NullPointerException exception) {
                        Log.e(toString(), "Impossible de mettre à jour la liste sélection", exception);
                    }
                }
                break;
            }
        }
    }

    public MaterialViewPager getmViewPager() {
        return mViewPager;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

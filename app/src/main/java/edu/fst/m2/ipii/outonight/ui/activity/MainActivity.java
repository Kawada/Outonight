package edu.fst.m2.ipii.outonight.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;
import edu.fst.m2.ipii.outonight.ui.adapter.TabPagerAdapter;
import edu.fst.m2.ipii.outonight.ui.adapter.cell.EstablishmentItemViewHolder;
import edu.fst.m2.ipii.outonight.ui.fragment.RecyclerViewFragment;
import edu.fst.m2.ipii.outonight.ui.fragment.WebViewFragment;
import edu.fst.m2.ipii.outonight.utils.BitmapUtils;
import icepick.Icepick;
import icepick.Icicle;


public class MainActivity extends ActionBarActivity {


    @Inject
    EstablishmentService establishmentService = EstablishmentServiceImpl.getInstance();

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

        // DualCacheContextUtils.setContext(getApplicationContext());

        setTitle("");

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

        Establishment establishment = establishmentService.getCachedByName(establishmentName).get(0);

        intent.putExtra("lat", 37.6329946);
        intent.putExtra("lng", -122.4938344);
        intent.putExtra("zoom", 14.0f);
        intent.putExtra("title", establishment.getName());
        intent.putExtra("description", establishment.getDescription());
        intent.putExtra("photo", R.drawable.nightclub_header_thumb);

        ImageView hero = (ImageView) parent.findViewById(R.id.photo);

        BitmapUtils.sPhotoCache.put(intent.getIntExtra("photo", -1),
                ((BitmapDrawable) hero.getDrawable()).getBitmap());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, hero, "photo_hero");
        startActivity(intent, options.toBundle());

        // startActivity(intent);

        /*
        intent.putExtra("lat", 37.6329946);
        intent.putExtra("lng", -122.4938344);
        intent.putExtra("zoom", 14.0f);
        intent.putExtra("title", establishment.getName());
        intent.putExtra("description", establishment.getDescription());

        /Bitmap bitmap = drawableToBitmap(((ImageView) parent.findViewById(R.id.photo_imageview)).getDrawable());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        intent.putExtra("photo", R.drawable.bar_header);

        ImageView hero = (ImageView) parent.findViewById(R.id.photo);

        sPhotoCache.put(intent.getIntExtra("photo", -1),
                ((BitmapDrawable) hero.getDrawable()).getBitmap());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, hero, "photo_hero");
        startActivity(intent, options.toBundle());
         */
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
}

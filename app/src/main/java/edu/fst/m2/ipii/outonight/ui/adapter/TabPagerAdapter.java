package edu.fst.m2.ipii.outonight.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.ui.activity.MainActivity;
import edu.fst.m2.ipii.outonight.ui.fragment.RecyclerViewFragment;
import edu.fst.m2.ipii.outonight.utils.PerformanceUtils;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private MainActivity activity;
    int oldPosition = -1;
    List<RecyclerViewFragment> fragments = new ArrayList<>();

    public TabPagerAdapter(MainActivity activity, FragmentManager fm) {
        super(fm);
        this.activity = activity;

        // Selection
        fragments.add(RecyclerViewFragment.newInstance(null));
        // Restos
        fragments.add(RecyclerViewFragment.newInstance(EstablishmentType.RESTAURANTS));
        // Bars
        fragments.add(RecyclerViewFragment.newInstance(EstablishmentType.BARS));
        // Boites
        fragments.add(RecyclerViewFragment.newInstance(EstablishmentType.NIGHTCLUBS));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        //only if position changed
        if(position == oldPosition) {
            return;
        }

        oldPosition = position;

        int color = 0;
        Drawable drawable = activity.getDrawable(R.drawable.selection_header);
        switch (position){
            case 0:
                color = activity.getResources().getColor(R.color.blue);
                break;
            case 1:
                drawable = activity.getDrawable(R.drawable.restaurant_header);
                color = activity.getResources().getColor(R.color.lime);
                break;
            case 2:
                drawable = activity.getDrawable(R.drawable.bar_header);
                color = activity.getResources().getColor(R.color.cyan);
                break;
            case 3:
                drawable = activity.getDrawable(R.drawable.nightclub_header);
                color = activity.getResources().getColor(R.color.purple);
                break;
        }

        final int fadeDuration = 400;

        // On réserve l'affichage des photos aux terminaux haut de gamme
        if (PerformanceUtils.getPerformanceLevel(activity) == PerformanceUtils.HIGH_PERFORMANCE_DEVICE) {
            activity.getmViewPager().setImageDrawable(drawable, fadeDuration);
        }

        activity.getmViewPager().setColor(color,fadeDuration);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return activity.getString(R.string.title_section_selec);
            case 1:
                return activity.getString(R.string.title_section_restos);
            case 2:
                return activity.getString(R.string.title_section_bars);
            case 3:
                return activity.getString(R.string.title_section_nightclubs);
        }

        return "";
    }

    public List<RecyclerViewFragment> getFragments() {
        return fragments;
    }
}

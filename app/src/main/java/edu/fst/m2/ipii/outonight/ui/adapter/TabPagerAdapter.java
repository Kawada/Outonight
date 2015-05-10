package edu.fst.m2.ipii.outonight.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.model.Bar;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.model.Restaurant;
import edu.fst.m2.ipii.outonight.ui.activity.MainActivity;
import edu.fst.m2.ipii.outonight.ui.fragment.RecyclerViewFragment;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private MainActivity activity;
    private LayoutInflater inflater;
    int oldPosition = -1;

    public TabPagerAdapter(MainActivity activity, FragmentManager fm) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return RecyclerViewFragment.newInstance(Restaurant.class);
            case 2:
                return RecyclerViewFragment.newInstance(Bar.class);
            case 3:
                return RecyclerViewFragment.newInstance(Nightclub.class);
            default:
                return RecyclerViewFragment.newInstance(Establishment.class);
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        //only if position changed
        if(position == oldPosition)
            return;
        oldPosition = position;

        int color = 0;
        Drawable drawable = activity.getResources().getDrawable(R.drawable.selection_header);
        switch (position){
            case 0:
                color = activity.getResources().getColor(R.color.blue);
                break;
            case 1:
                drawable = activity.getResources().getDrawable(R.drawable.restaurant_header);
                color = activity.getResources().getColor(R.color.lime);
                break;
            case 2:
                drawable = activity.getResources().getDrawable(R.drawable.bar_header);
                color = activity.getResources().getColor(R.color.cyan);
                break;
            case 3:
                drawable = activity.getResources().getDrawable(R.drawable.nightclub_header);
                color = activity.getResources().getColor(R.color.purple);
                break;
        }

        final int fadeDuration = 400;
        activity.getmViewPager().setImageDrawable(drawable, fadeDuration);
        activity.getmViewPager().setColor(color,fadeDuration);

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Select.";
            case 1:
                return "Restos";
            case 2:
                return "Bars";
            case 3:
                return "Boites";
        }
        return "";
    }
}

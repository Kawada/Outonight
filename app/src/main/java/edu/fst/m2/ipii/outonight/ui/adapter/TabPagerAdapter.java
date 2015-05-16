package edu.fst.m2.ipii.outonight.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
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
    List<Fragment> fragments = new ArrayList<>();

    public TabPagerAdapter(MainActivity activity, FragmentManager fm) {
        super(fm);
        this.activity = activity;

        fragments.add(RecyclerViewFragment.newInstance(null));
        fragments.add(RecyclerViewFragment.newInstance(EstablishmentType.RESTAURANTS));
        fragments.add(RecyclerViewFragment.newInstance(EstablishmentType.BARS));
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
        return fragments.size();
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

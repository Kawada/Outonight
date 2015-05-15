package edu.fst.m2.ipii.outonight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.model.Bar;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.model.Restaurant;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;
import edu.fst.m2.ipii.outonight.ui.adapter.EstablishmentRecyclerViewAdapter;

/**
 * Created by dleguis on 10/05/2015.
 */
public class RecyclerViewFragment extends Fragment {

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static RecyclerView.Adapter mAdapter;

    public static RecyclerViewFragment newInstance(Class clazz) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();

        Bundle bundle = new Bundle();

        if (Restaurant.class.equals(clazz)) {
            bundle.putString("Type", EstablishmentType.RESTAURANTS);
        }
        else if (Bar.class.equals(clazz)) {
            bundle.putString("Type", EstablishmentType.BARS);
        }
        else if (Nightclub.class.equals(clazz)) {
            bundle.putString("Type", EstablishmentType.NIGHTCLUBS);
        }

        recyclerViewFragment.setArguments(bundle);

        return recyclerViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        String type = getArguments().getString("Type");

        mAdapter = new RecyclerViewMaterialAdapter(new EstablishmentRecyclerViewAdapter(type, getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    public static void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }
}

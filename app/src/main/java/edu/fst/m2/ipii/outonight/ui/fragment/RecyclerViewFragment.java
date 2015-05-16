package edu.fst.m2.ipii.outonight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.fst.m2.ipii.outonight.R;
import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.adapter.EstablishmentRecyclerViewAdapter;
import edu.fst.m2.ipii.outonight.ws.EstablishmentApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dleguis on 10/05/2015.
 */
public class RecyclerViewFragment extends Fragment {

    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    public static RecyclerViewFragment newInstance(String type) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();

        Bundle bundle = new Bundle();

        bundle.putString("Type", type);

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

        List<Establishment> establishments = establishmentCacheService.getCachedByType(type);

        EstablishmentRecyclerViewAdapter establishmentRecyclerViewAdapter = new EstablishmentRecyclerViewAdapter(establishments, getActivity());

        loadEstablishments(type, establishmentRecyclerViewAdapter);

        mAdapter = new RecyclerViewMaterialAdapter(establishmentRecyclerViewAdapter);
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    private boolean datasourceContains(Establishment establishment, List<Establishment> datasource) {
        for (Establishment est : datasource) {
            if (est.getEstablishmentId() == establishment.getEstablishmentId()) {
                return true;
            }
        }

        return false;
    }

    private void loadEstablishments(String type, final EstablishmentRecyclerViewAdapter establishmentRecyclerViewAdapter) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WebserviceConstants.WS_URL)
                .build();

        EstablishmentApi establishmentApi = restAdapter.create(EstablishmentApi.class);

        if (type != null) {
            establishmentApi.fetchByType(type, new Callback<List<Establishment>>() {
                @Override
                public void success(List<Establishment> establishments, Response response) {

                    for (Establishment cursor : establishments) {
                        // Toast.makeText(context, "Passage a l'etablissement " + cursor.getName() + " (id no " + cursor.getEstablishmentId() + ")", Toast.LENGTH_SHORT).show();
                        if (!datasourceContains(cursor, establishmentRecyclerViewAdapter.getDatasource())) {
                            // Le save en cascade ne se fait pas bizarrement...
                            cursor.getAddress().save();
                            cursor.getContact().save();
                            cursor.save();

                            Log.d("RecyclerViewAdapter", "Sauvegarde : " + cursor.toString());
                            // Toast.makeText(context, "Sauvegarde de l'etablissement " + cursor.getName(), Toast.LENGTH_SHORT).show();
                            establishmentRecyclerViewAdapter.getDatasource().add(cursor);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("establishmentService", error.getMessage(), error);
                }
            });
        }
    }
}

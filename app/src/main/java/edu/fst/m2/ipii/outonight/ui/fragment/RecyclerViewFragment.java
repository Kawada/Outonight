package edu.fst.m2.ipii.outonight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import edu.fst.m2.ipii.outonight.constants.BundleArguments;
import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;
import edu.fst.m2.ipii.outonight.ui.activity.MainActivity;
import edu.fst.m2.ipii.outonight.ui.adapter.EstablishmentRecyclerViewAdapter;
import edu.fst.m2.ipii.outonight.utils.CroutonUtils;
import edu.fst.m2.ipii.outonight.ws.EstablishmentApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dleguis on 10/05/2015.
 */
public class RecyclerViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static boolean NEW_ESTABLISHMENT = true;
    private static boolean OLD_ESTABLISHMENT = false;

    private RecyclerView.Adapter mAdapter;

    private EstablishmentRecyclerViewAdapter establishmentRecyclerViewAdapter;

    @Inject
    EstablishmentCacheService establishmentCacheService = EstablishmentCacheServiceImpl.getInstance();

    public static RecyclerViewFragment newInstance(String type) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();

        Bundle bundle = new Bundle();

        bundle.putString(BundleArguments.BUNDLE_ESTABLISHMENT_TYPE, type);

        recyclerViewFragment.setArguments(bundle);

        return recyclerViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.inject(this, view);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        String type = getArguments().getString(BundleArguments.BUNDLE_ESTABLISHMENT_TYPE);

        List<Establishment> establishments = establishmentCacheService.getCachedByType(type);

        establishmentRecyclerViewAdapter = new EstablishmentRecyclerViewAdapter(establishments, getActivity());

        loadEstablishments(type);

        mAdapter = new RecyclerViewMaterialAdapter(establishmentRecyclerViewAdapter);
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    public void updateDataSource() {

        // On fait le ménage dans la liste...
        establishmentRecyclerViewAdapter.getDatasource().clear();

        String type = getArguments().getString(BundleArguments.BUNDLE_ESTABLISHMENT_TYPE);
        List<Establishment> establishments = establishmentCacheService.getCachedByType(type);

        for (Establishment establishment : establishments) {
            addOrUpdate(establishment, true);
        }

        loadEstablishments(type);

        mAdapter.notifyDataSetChanged();
    }

    private Establishment addOrUpdate(Establishment establishment, boolean updateStared) {

        for (Establishment est : establishmentRecyclerViewAdapter.getDatasource()) {
            if (est.getEstablishmentId() == establishment.getEstablishmentId()) {
                est.setName(establishment.getName());
                est.setDescription(establishment.getDescription());
                est.setFeatured(establishment.isFeatured());
                est.setContact(establishment.getContact());
                est.setAddress(establishment.getAddress());
                est.setEstablishmentId(establishment.getEstablishmentId());
                if (updateStared) {
                    est.setStared(establishment.isStared());
                }
                est.setPhoto(establishment.getPhoto());
                est.setType(establishment.getType());

                return est;
            }
        }

        establishmentRecyclerViewAdapter.getDatasource().add(establishment);

        return establishment;
    }

    private void loadEstablishments(String type) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WebserviceConstants.WS_URL)
                .build();

        EstablishmentApi establishmentApi = restAdapter.create(EstablishmentApi.class);

        // On évite d'envoyer un appel avec un type null...
        if (type != null) {
            establishmentApi.fetchByType(type, new Callback<List<Establishment>>() {
                @Override
                public void success(List<Establishment> establishments, Response response) {

                    for (Establishment cursor : establishments) {
                        cursor = addOrUpdate(cursor, false);
                        // Le save en cascade ne se fait pas bizarrement...
                        cursor.getAddress().save();
                        cursor.getContact().save();
                        cursor.save();

                        Log.d("RecyclerViewAdapter", "Sauvegarde : " + cursor.toString());

                    }

                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("establishmentService", error.getMessage(), error);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public void onRefresh() {
        String type = getArguments().getString(BundleArguments.BUNDLE_ESTABLISHMENT_TYPE);
        loadEstablishments(type);

        if (!((MainActivity) getActivity()).isNetworkAvailable()) {
            // Si aucun réseau dispo, notification
            CroutonUtils.displayErrorMessage(getActivity(), R.string.msg_err_no_connection);
        }
    }
}

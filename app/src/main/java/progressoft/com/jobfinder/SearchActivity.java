package progressoft.com.jobfinder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import progressoft.com.jobfinder.Adapter.Result;
import progressoft.com.jobfinder.Adapter.ResultAdapter;
import progressoft.com.jobfinder.main.MainPresenter;
import progressoft.com.jobfinder.main.MainView;


public class SearchActivity extends AppCompatActivity implements MainView {
    private static final String TAG = SearchActivity.class.getName();

    private android.support.v7.widget.SearchView searchView;

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private List<Result> itemList = new ArrayList<>();
    private Toolbar toolbar;

    private MainPresenter presenter;
    private ProgressDialog progressDialog;
    private String location;
    private String query;
    private AutocompleteSupportFragment autocompleteFragment;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter = new MainPresenter(this);
        Places.initialize(getApplicationContext(), "AIzaSyAN5zSVPq1BNxHmb3lZ-iM_N7PlEwxO1xM");
        PlacesClient placesClient = Places.createClient(this);

        presenter.getJobList("", "");
        searchView = findViewById(R.id.searchView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);

        recyclerView = findViewById(R.id.recyclerView);


        resultAdapter = new ResultAdapter(this, itemList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(resultAdapter);


        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        init();
    }

    private void init() {
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                location=place.getName();
                setQuery(query, place.getName());

            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        autocompleteFragment.setText("");
                        location = null;
                        view.setVisibility(View.GONE);
                        setQuery(query, location);

                    }
                });

        searchView.setOnClickListener(v -> {
            searchView.onActionViewExpanded();

        });
        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                query = null;
                setQuery(query, location);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                setQuery(s, location);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    public void setQuery(String jobName, String location) {
        if(jobName==null) jobName="";
        if(location==null) location="";
        presenter.getJobList(jobName, location);
    }

    @Override
    public void startLoading() {
        stopLoading();
        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog = null;
        }

    }

    @Override
    public void onSuccess(List<Result> dataResponse) {

        itemList.clear();
        itemList.addAll(dataResponse);
        resultAdapter.notifyDataSetChanged();


    }


}
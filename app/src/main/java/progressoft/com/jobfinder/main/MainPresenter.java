package progressoft.com.jobfinder.main;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import progressoft.com.jobfinder.Adapter.Result;
import progressoft.com.jobfinder.AppController;
import progressoft.com.jobfinder.Model.GithubResponse;
import progressoft.com.jobfinder.Model.SearchGovResponse;
import progressoft.com.jobfinder.converters.ResponseConverters;
import progressoft.com.jobfinder.utils.Utils;

public class MainPresenter {

    private MainView view;
    private Gson gson = new Gson();
    private List<Result> resultList = new ArrayList<>();

    public MainPresenter(MainView view) {

        this.view = view;
    }

    public void getJobList(String positions, String location) {
        view.startLoading();
        resultList.clear();


        String url = "https://jobs.github.com/positions.json";
        HashMap<String, String> parameters = new HashMap<>();

        parameters.put("description", positions);
        parameters.put("location", location);

        String finalLink = null;
        try {
            finalLink = Utils.appendToUrl(url, parameters);
            Log.e("onResponse: ", finalLink.toString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                finalLink, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("onResponse: ", response.toString());
                Type listType = new TypeToken<ArrayList<GithubResponse>>() {
                }.getType();
                List<GithubResponse> yourClassList = new Gson().fromJson(response.toString(), listType);

                for (int i = 0; i < yourClassList.size(); i++) {
                    Result result = ResponseConverters.convertGitHubResponseToSearchResult(yourClassList.get(i));
                    resultList.add(result);
                }

                view.stopLoading();
                searchJobsList(positions, location);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.stopLoading();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    ///////////////////////
    private void searchJobsList(String positions, String location) {
        view.startLoading();
        String url = "https://jobs.search.gov/jobs/search.json";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("query", positions);
        parameters.put("lat_lon", location);

        String finalLink = null;
        try {
            finalLink = Utils.appendToUrl(url, parameters);
            Log.e("onResponse: ", finalLink.toString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                finalLink, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("onResponse: ", response.toString());
                Type listType = new TypeToken<ArrayList<SearchGovResponse>>() {
                }.getType();
                List<SearchGovResponse> yourClassList = new Gson().fromJson(response.toString(), listType);

                for (int i = 0; i < yourClassList.size(); i++) {
                    Result result = ResponseConverters.convertSearchGovResponseToSearchResult(yourClassList.get(i));
                    resultList.add(result);
                }


                view.onSuccess(resultList);
                view.stopLoading();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.stopLoading();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}

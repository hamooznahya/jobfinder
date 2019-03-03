package progressoft.com.jobfinder.converters;


import java.util.Arrays;

import progressoft.com.jobfinder.Adapter.Result;
import progressoft.com.jobfinder.Model.GithubResponse;
import progressoft.com.jobfinder.Model.SearchGovResponse;


public class ResponseConverters {


    public static Result convertGitHubResponseToSearchResult(GithubResponse response) {


        return new Result() {
            @Override
            public String getCompanyName() {
                return response.getCompany();
            }

            @Override
            public String getJobTitle() {
                return response.getTitle();
            }

            @Override
            public String getLogo() {
                return response.getCompanyLogo();
            }

            @Override
            public String getLocation() {
                return response.getLocation();
            }

            @Override
            public String getPostDate() {
                return response.getCreatedAt();
            }

            @Override
            public String getProvider() {
                return "GitHub";
            }

            @Override
            public String getUrl() {
                return response.getUrl();
            }
        };
    }


    public static Result convertSearchGovResponseToSearchResult(SearchGovResponse response) {


        return new Result() {
            @Override
            public String getCompanyName() {
                return response.getOrganizationName();
            }

            @Override
            public String getJobTitle() {
                return response.getPositionTitle();
            }

            @Override
            public String getLogo() {
                return null;
            }

            @Override
            public String getLocation() {
                return Arrays.toString(response.getLocations().toArray());
            }

            @Override
            public String getPostDate() {
                return response.getStartDate();
            }

            @Override
            public String getProvider() {
                return "Search Gov";
            }

            @Override
            public String getUrl() {
                return response.getUrl();
            }
        };
    }


}

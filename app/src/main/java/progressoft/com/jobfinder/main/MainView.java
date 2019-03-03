package progressoft.com.jobfinder.main;


import java.util.List;

import progressoft.com.jobfinder.Adapter.Result;

public interface MainView {

    void startLoading();

    void stopLoading();

    void onSuccess(List<Result> result);
}

package mobiledev.testvolohovmobiledev;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Repositories.DataRepositories;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by root on 17.08.17.
 */

public interface GetDataRepositories {
    @GET("users/{user}/repos")
    Observable<ArrayList<DataRepositories>> getRepositoriesList(@Path("user") String user);
}

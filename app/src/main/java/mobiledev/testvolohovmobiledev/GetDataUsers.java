package mobiledev.testvolohovmobiledev;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Users.DataUsers;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by root on 17.08.17.
 */

public interface GetDataUsers {

        @GET("users")
        Observable<ArrayList<DataUsers>> getUsersList(@Query("since") String page);

}

package mobiledev.testvolohovmobiledev.Models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.GetDataRepositories;
import mobiledev.testvolohovmobiledev.GetDataUsers;
import mobiledev.testvolohovmobiledev.Repositories.DataRepositories;
import mobiledev.testvolohovmobiledev.Users.DataUsers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by root on 17.08.17.
 */

public class ModelImpl implements Model {
//TODO: создать конструктор и геттеры
    private RxJavaCallAdapterFactory mRxAdapter;
    private Gson mGson ;
    private Retrofit mRetrofit;
    private GetDataUsers mApiInterfaceUsers;
    private GetDataRepositories mApiInterfaceRepos;

    public ModelImpl(){
        mRxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        mGson = new GsonBuilder().create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(mRxAdapter)
                .build();
        mApiInterfaceUsers = mRetrofit.create(GetDataUsers.class);
        mApiInterfaceRepos = mRetrofit.create(GetDataRepositories.class);
    }

    @Override
    public Observable<ArrayList<DataUsers>> getDataUsers(int latestID) {
        return mApiInterfaceUsers.getUsersList(""+latestID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ArrayList<DataRepositories>> getDataRepositories(String name) {
        return mApiInterfaceRepos.getRepositoriesList(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
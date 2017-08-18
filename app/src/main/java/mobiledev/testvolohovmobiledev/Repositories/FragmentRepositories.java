package mobiledev.testvolohovmobiledev.Repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Presenter.Presenter;
import mobiledev.testvolohovmobiledev.Presenter.PresenterImplUsers;
import mobiledev.testvolohovmobiledev.R;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by root on 17.08.17.
 */

public class FragmentRepositories extends Fragment {
    public static final String KEY_REPOS = "KEY_REPOS";
    public static final String KEY_IS_LOADING = "KEY_IS_LOADING";

    private static final String TAG = "REPOS_FRAGMENT";
    private static final String KEY_SCROLL = "KEY_SCROLL" ;
    private Subscription mSubscription;
    private ProgressBar mLoading;
    private RecyclerView mRecyclerView;
    private ArrayList<DataRepositories> mDataRepositories = new ArrayList<>();
    private boolean mIsLoading;
    private RecyclerAdapterRepositories mRecyclerAdapterRepositories;
    private String mName = "Unnamed";
    private PresenterImplUsers mPresenter;

    public static FragmentRepositories newInstance(String name, Presenter presenter) {
        FragmentRepositories fragment = new FragmentRepositories();
        fragment.mName = name;
        fragment.mPresenter = (PresenterImplUsers) presenter;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_repositories, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_repositories);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerRepos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            mDataRepositories = savedInstanceState.getParcelableArrayList(KEY_REPOS);
            mIsLoading = savedInstanceState.getBoolean(KEY_IS_LOADING);
            mRecyclerView.setScrollY(savedInstanceState.getInt(KEY_SCROLL,0));
        }

        mLoading = (ProgressBar) v.findViewById(R.id.loadingRepos);

        mRecyclerAdapterRepositories = new RecyclerAdapterRepositories(mDataRepositories);
        mRecyclerView.setAdapter(mRecyclerAdapterRepositories);
        mLoading.setAlpha(0);
        if (mDataRepositories.size() == 0 || mIsLoading) {
            mRecyclerView.setAlpha(0);
            showLoadingIndicator(true);
            getModelsList();
        }
        fab.attachToRecyclerView(mRecyclerView);
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_REPOS, mDataRepositories);
        outState.putBoolean(KEY_IS_LOADING, mIsLoading);
        outState.putInt(KEY_SCROLL,mRecyclerView.getScrollY());
    }

    public void showLoadingIndicator(boolean show) {
        mIsLoading = show;
        if (mIsLoading) {
            mRecyclerView.animate().alpha(0).setDuration(200);
            mLoading.animate().alpha(1).setDuration(100);
        }
        else {
            mRecyclerView.animate().alpha(1).setDuration(700);
            mLoading.animate().alpha(0).setDuration(800);
        }
    }

    private void getModelsList() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSubscription = mPresenter.mModel.getDataRepositories(mName).
                subscribe(new Subscriber<ArrayList<DataRepositories>>() {
                    @Override
                    public void onCompleted() {
                        showLoadingIndicator(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mIsLoading = false;
                        showLoadingIndicator(false);
                        Toast toast = Toast.makeText(getContext(),"Ошибка при получении данных", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    @Override
                    public void onNext(ArrayList<DataRepositories> newModels) {
                        mIsLoading = false;
                        mDataRepositories.addAll(newModels);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                        showLoadingIndicator(false);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}

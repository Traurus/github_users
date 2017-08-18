package mobiledev.testvolohovmobiledev.Users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Presenter.Presenter;
import mobiledev.testvolohovmobiledev.Presenter.PresenterImplUsers;
import mobiledev.testvolohovmobiledev.R;
import mobiledev.testvolohovmobiledev.Repositories.FragmentRepositories;
import mobiledev.testvolohovmobiledev.Views.ViewUsers;

/**
 * Created by root on 17.08.17.
 */

public class FragmentUsers extends Fragment implements ViewUsers {

    public static final String KEY_USERS = "KEY_USERS";
    public static final String KEY_LATESTID= "KEY_LATESTID";
    public static final String KEY_IS_LOADING = "KEY_IS_LOADING";

    public boolean isOpenRepository = false;

    private static final String KEY_IS_REPOSITORY_OPEN = "KEY_IS_REPOSITORY_OPEN" ;
    private static final String KEY_REPOSITORY_OPEN = "KEY_REPOSITORY_OPEN";

    private boolean isLoading;
    private RecyclerView mRecyclerView;
    private Presenter mPresenter;
    private RecyclerAdapterUsers mRecyclerAdapterUsers;
    private int mLatestID = 0;
    private ProgressBar loading;
    private LinearLayoutManager mLayoutManager;
    private String mOpenRepositoryName="";
    private ArrayList<DataUsers> mDataUsers = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);
        getActivity().setTitle("Пользователи GitHub");
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_users);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        FloatingActionButton  fab2 = getActivity().findViewById(R.id.fab_repositories);
        fab2.setVisibility(View.INVISIBLE);
        if (savedInstanceState != null){
            isOpenRepository = savedInstanceState.getBoolean(KEY_IS_REPOSITORY_OPEN);
            mDataUsers = savedInstanceState.getParcelableArrayList(KEY_USERS);
            mLatestID = savedInstanceState.getInt(KEY_LATESTID,0);
            isLoading = savedInstanceState.getBoolean(KEY_IS_LOADING);
            mOpenRepositoryName = savedInstanceState.getString(KEY_REPOSITORY_OPEN);
            if(isOpenRepository)
                showBackArrow();
        }
        mPresenter = new PresenterImplUsers(this);
        loading = (ProgressBar) v.findViewById(R.id.loading);
        mRecyclerView =  v.findViewById(R.id.recycler_users);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerAdapterUsers = new RecyclerAdapterUsers(mDataUsers,s->userClick(s));
        mRecyclerView.setAdapter(mRecyclerAdapterUsers);
        changeAdapter(""+mLatestID);
        loading.setAlpha(0);
        if(mDataUsers.size()==0) {
            mRecyclerView.setAlpha(0);
            mPresenter.onCreate();
            showLoadingIndicator(true);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(isLoading)
                    return;
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                if(pastVisibleItems+visibleItemCount>=totalItemCount){
                    mRecyclerView.setAlpha(0);
                    mLatestID = Integer.parseInt(mDataUsers.get(mDataUsers.size()-1).getId());
                    mPresenter.onChangePage(mLatestID);
                    showLoadingIndicator(true);
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        fab.attachToRecyclerView(mRecyclerView);
        return v;
    }

    private void showBackArrow(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_repositories);
        floatingActionButton.setVisibility(View.VISIBLE);
        (getActivity()).setTitle(mOpenRepositoryName.substring(0,1).toUpperCase()+mOpenRepositoryName.substring(1));
        isOpenRepository = true;
    }

    @Override
    public void showData(ArrayList<DataUsers> users) {
        isLoading = false;
        mDataUsers.addAll(users);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mLatestID = Integer.parseInt(mRecyclerAdapterUsers.getLatestID());
        showLoadingIndicator(false);
    }

    @Override
    public void showError() {
        isLoading = false;
        showLoadingIndicator(false);
        Toast toast = Toast.makeText(getContext(),"Ошибка при получении данных", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void changeAdapter(String _latestId) {
        mRecyclerAdapterUsers.setLatestID( _latestId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_USERS, mDataUsers);
        outState.putBoolean(KEY_IS_LOADING, isLoading);
        outState.putBoolean(KEY_IS_REPOSITORY_OPEN, isOpenRepository);
        outState.putString(KEY_REPOSITORY_OPEN, mOpenRepositoryName);
        outState.putInt(KEY_LATESTID, Integer.parseInt(mRecyclerAdapterUsers.getLatestID()));
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        isLoading = show;
        if (isLoading) {
            mRecyclerView.animate().alpha(0).setDuration(200);
            loading.animate().alpha(1).setDuration(100);
        }
        else {
            mRecyclerView.animate().alpha(1).setDuration(700);
            loading.animate().alpha(0).setDuration(800);
        }
    }

    private  void userClick(String name){
        mOpenRepositoryName = name;
        showBackArrow();
        getFragmentManager().beginTransaction().replace(R.id.container1, FragmentRepositories.newInstance(name, mPresenter))
                .commit();
    }
}

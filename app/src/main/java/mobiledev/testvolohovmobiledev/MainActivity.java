package mobiledev.testvolohovmobiledev;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

import mobiledev.testvolohovmobiledev.Users.FragmentUsers;

public class MainActivity extends AppCompatActivity {
    FragmentUsers mFragmentUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentUsers = (FragmentUsers) getSupportFragmentManager().findFragmentById(R.id.container);
        if(mFragmentUsers==null){
            mFragmentUsers = new FragmentUsers();
            getSupportFragmentManager().beginTransaction().add(R.id.container,mFragmentUsers).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mFragmentUsers!=null && mFragmentUsers.isOpenRepository){
            getSupportFragmentManager().beginTransaction().replace(R.id.container1, new Fragment()).commit();
            setTitle("Пользователи GitHub");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_repositories);
            floatingActionButton.setVisibility(View.INVISIBLE);
            mFragmentUsers.isOpenRepository = false;
        }
        else
            super.onBackPressed();
    }

}

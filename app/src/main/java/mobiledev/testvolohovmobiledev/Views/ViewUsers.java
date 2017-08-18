package mobiledev.testvolohovmobiledev.Views;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Users.DataUsers;

/**
 * Created by root on 17.08.17.
 */

public interface ViewUsers {
    void showData(ArrayList<DataUsers> users);
    void showError();
    void changeAdapter(String _latestID);
    void showLoadingIndicator( boolean show);
}

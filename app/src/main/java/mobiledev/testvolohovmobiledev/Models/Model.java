package mobiledev.testvolohovmobiledev.Models;


import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Users.DataUsers;
import mobiledev.testvolohovmobiledev.Repositories.DataRepositories;
import rx.Observable;

/**
 * Created by root on 17.08.17.
 */

public interface Model {
    Observable<ArrayList<DataUsers>> getDataUsers(int page);
    Observable<ArrayList<DataRepositories>> getDataRepositories(String name);
}

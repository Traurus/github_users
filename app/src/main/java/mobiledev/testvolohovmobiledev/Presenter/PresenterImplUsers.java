package mobiledev.testvolohovmobiledev.Presenter;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.Models.Model;
import mobiledev.testvolohovmobiledev.Models.ModelImpl;
import mobiledev.testvolohovmobiledev.Users.DataUsers;
import mobiledev.testvolohovmobiledev.Views.ViewUsers;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.Subscriptions;

/**
 * Created by root on 17.08.17.
 */

public class PresenterImplUsers implements Presenter{
    public Model mModel = new ModelImpl();
    private ViewUsers view;
    private Subscription subscription = Subscriptions.empty();
    private static BehaviorSubject<ArrayList<DataUsers>> observableUsersList;
    private int page = 0;

    public PresenterImplUsers(ViewUsers _view){
        this.view = _view;

    }

    @Override
    public void onCreate() {
        observableUsersList = BehaviorSubject.create();
        if(subscription!=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        subscription = mModel.getDataUsers(page)
                .subscribe(
                        s -> view.showData(s),
                        s -> view.showError()
                );
    }

    @Override
    public void onChangePage(int latestID){
        this.page = latestID;
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = mModel.getDataUsers(page).
                subscribe(s -> view.showData(s), s -> view.showError());
    }

    @Override
    public void onDestroy() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}

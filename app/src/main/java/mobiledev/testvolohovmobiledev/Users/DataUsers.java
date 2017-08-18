package mobiledev.testvolohovmobiledev.Users;

/**
 * Created by root on 17.08.17.
 */


import android.os.Parcel;
import android.os.Parcelable;

public class DataUsers  implements Parcelable{

    private String login;
    private String id;
    private String avatar_url;

    protected DataUsers(Parcel in) {
        login = in.readString();
        id = in.readString();
        avatar_url = in.readString();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    /*****************************Parceble imports**********************************/
    public static final Creator<DataUsers> CREATOR = new Creator<DataUsers>() {
        @Override
        public DataUsers createFromParcel(Parcel in) {
            return new DataUsers(in);
        }

        @Override
        public DataUsers[] newArray(int size) {
            return new DataUsers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(id);
        parcel.writeString(avatar_url);
    }

}




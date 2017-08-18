package mobiledev.testvolohovmobiledev.Repositories;

/**
 * Created by root on 17.08.17.
 */

import android.os.Parcel;
import android.os.Parcelable;
public class DataRepositories implements Parcelable {
    private String name;
    private String description;
    private String created_at;

    private DataRepositories(Parcel in){
        this.name = in.readString();
        this.description = in.readString();
        this.created_at = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    /*****************************Parceble imports**********************************/
    public static final Creator<DataRepositories> CREATOR = new Creator<DataRepositories>() {
        @Override
        public DataRepositories createFromParcel(Parcel in) {
            return new DataRepositories(in);
        }

        @Override
        public DataRepositories[] newArray(int size) {
            return new DataRepositories[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(created_at);
    }
}

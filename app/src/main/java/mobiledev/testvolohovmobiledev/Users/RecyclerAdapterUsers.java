package mobiledev.testvolohovmobiledev.Users;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.R;

/**
 * Created by root on 17.08.17.
 */

public class RecyclerAdapterUsers  extends RecyclerView.Adapter<RecyclerAdapterUsers.ViewHolderUser>{

    private ArrayList<DataUsers> mDataUsers;
    private InterfaceClickUser mInterfaceClickUser;
    private String mLatestID="";

    public RecyclerAdapterUsers(ArrayList<DataUsers> _dataUsers,InterfaceClickUser _interfaceClickUser){
        this.mDataUsers = _dataUsers;
        mInterfaceClickUser = _interfaceClickUser;
    }

    @Override
    public RecyclerAdapterUsers.ViewHolderUser onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolderUser(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterUsers.ViewHolderUser holder, int position) {
        ViewHolderUser holderUser= (ViewHolderUser) holder;
        DataUsers dataUsers = mDataUsers.get(position);
        mLatestID = mDataUsers.get(mDataUsers.size()-1).getId();
        holderUser.mLoginTextView.setText(dataUsers.getLogin().substring(0,1).toUpperCase()+dataUsers.getLogin().substring(1));
        holderUser.mIdTextView.setText("ID пользователя:"+dataUsers.getId());
        Picasso.with(holderUser.mCircularImageView.getContext()).load(dataUsers.getAvatarUrl()).into(holderUser.mCircularImageView);
        holderUser.rootView.setOnClickListener(s->mInterfaceClickUser.userClick(dataUsers.getLogin()));
    }

    @Override
    public int getItemCount() {
        return mDataUsers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    void setLatestID(String i){
        mLatestID = i;
        notifyDataSetChanged();
    }

    String getLatestID(){
        return mLatestID;
    }

    class ViewHolderUser extends RecyclerView.ViewHolder {

        public TextView mLoginTextView, mIdTextView;
        public CircularImageView mCircularImageView;
        public View rootView;

        public ViewHolderUser(View itemView) {
            super(itemView);
            mLoginTextView = itemView.findViewById(R.id.user_login);
            mIdTextView = itemView.findViewById(R.id.user_id);
            mCircularImageView = itemView.findViewById(R.id.user_image);
            rootView = itemView.findViewById(R.id.root);
            mCircularImageView.setShadowColor(Color.parseColor("#646464"));
            mCircularImageView.setBorderWidth(1);
            mCircularImageView.setShadowRadius(6);
        }
    }

}

package mobiledev.testvolohovmobiledev.Repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;


import java.util.ArrayList;

import mobiledev.testvolohovmobiledev.R;


/**
 * Created by root on 18.08.17.
 */

public class RecyclerAdapterRepositories extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
        private ArrayList<DataRepositories> dataRepositorys;

        public RecyclerAdapterRepositories(ArrayList<DataRepositories> dataRepositorys) {
            this.dataRepositorys = dataRepositorys;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            return new ViewHolderRepositories(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolderRepositories holderRepo = (ViewHolderRepositories) holder;
            DataRepositories dataRepository = dataRepositorys.get(position);
            holderRepo.name.setText(dataRepository.getName().substring(0, 1).toUpperCase() + dataRepository.getName().substring(1));
            holderRepo.description.setText(dataRepository.getDescription());
            ZonedDateTime time = ZonedDateTime.parse(dataRepository.getCreatedAt());
            holderRepo.created.setText("Создано: "+time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        @Override
        public int getItemCount() {
            return dataRepositorys.size();
        }

        public static class ViewHolderRepositories extends RecyclerView.ViewHolder {
            
            public TextView name, description, created;
            
            public ViewHolderRepositories(View v) {
                super(v);
                name = (TextView) v.findViewById(R.id.repository_name);
                description = (TextView) v.findViewById(R.id.repository_description);
                created = (TextView) v.findViewById(R.id.repository_created_at);
            }
        }


    
}

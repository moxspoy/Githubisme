package id.moxspoy.githubisme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.moxspoy.githubisme.R;
import id.moxspoy.githubisme.model.Repository;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.MyViewHolder> {

    private Context context;
    private List<Repository> repositories;

    public RepositoryListAdapter(Context context, List<Repository> repositories) {
        this.context = context;
        this.repositories = repositories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.name.setText(repository.getName());
        holder.created.setText(repository.getCreated_at());

        if (repository.getDescription() == null) {
            holder.desc.setText("Description not available");
        } else {
            holder.desc.setText(repository.getDescription());
        }

        if(repository.isPrivate()){
            holder.image.setImageResource(R.drawable.ic_lock_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView desc;
        TextView created;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_is_privat_repo);
            name = itemView.findViewById(R.id.tv_repo_name);
            desc = itemView.findViewById(R.id.tv_repo_description);
            created = itemView.findViewById(R.id.tv_repo_createdat);
        }
    }
}

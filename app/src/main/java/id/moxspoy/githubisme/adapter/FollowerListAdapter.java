package id.moxspoy.githubisme.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import id.moxspoy.githubisme.R;
import id.moxspoy.githubisme.model.User;
import timber.log.Timber;

public class FollowerListAdapter extends RecyclerView.Adapter<FollowerListAdapter.MyViewHolder> {

    private Context context;
    private List<User> users;

    public FollowerListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvUsername.setText(users.get(position).getLogin());

        String userId = String.valueOf(users.get(position).getId());
        holder.tvBio.setText(userId);

        Glide.with(context)
                .load(
                        users.get(position).getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(
                        holder.imageUser
                );
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //Initview
        ImageView imageUser;
        TextView tvUsername;
        TextView tvBio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUser = itemView.findViewById(R.id.img_item_follower_list);
            tvUsername = itemView.findViewById(R.id.tv_usernamer_follower_list);
            tvBio = itemView.findViewById(R.id.tv_location_follower_list);
        }
    }
}

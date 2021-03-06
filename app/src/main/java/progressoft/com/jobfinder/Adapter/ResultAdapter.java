package progressoft.com.jobfinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import progressoft.com.jobfinder.JobDetails;
import progressoft.com.jobfinder.R;

import static progressoft.com.jobfinder.JobDetails.KEY_LINK;
import static progressoft.com.jobfinder.JobDetails.KEY_NAME;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private Context context;
    private List<Result> itemList;
    private ArrayList<Result> arraylist;
    public ResultAdapter(Context context, List<Result> itemList) {
        this.context = context;
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Result result = itemList.get(i);
        if (result != null && result.getLogo() != null)
            Picasso.get().load(result.getLogo()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.ic_launcher_background).into(holder.imgCompany);
        else
            holder.imgCompany.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_foreground));

        if (result != null &&result.getCompanyName() != null)
            holder.tvCompanyName.setText(result.getCompanyName()+"");
        holder.tvJob.setText(result.getJobTitle()+"5");
        holder.tvLocation.setText(result.getLocation()+"");
        holder.tvPostDate.setText(result.getPostDate()+"");
        holder.tvProvider.setText(result.getProvider()+"");


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, JobDetails.class);
            intent.putExtra(KEY_LINK, result.getUrl());
            intent.putExtra(KEY_NAME, result.getCompanyName());

            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCompany;
        TextView tvCompanyName;
        TextView tvJob;
        TextView tvLocation;
        TextView tvPostDate;
        TextView tvProvider;

        public ViewHolder(View view) {
            super(view);
            imgCompany = view.findViewById(R.id.imgCompany);
            tvCompanyName = view.findViewById(R.id.tvCompanyName);
            tvJob = view.findViewById(R.id.tvJob);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvPostDate = view.findViewById(R.id.tvPostDate);
            tvProvider = view.findViewById(R.id.tvProvider);
        }
    }
}


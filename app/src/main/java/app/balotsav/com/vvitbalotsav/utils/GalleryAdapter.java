package app.balotsav.com.vvitbalotsav.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.OnClickAdapter;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    public GalleryAdapter(Context context) {
        this.context = context;
    }

    public void setGalleryList(List<String> galleryList) {
        this.galleryList = galleryList;
        notifyDataSetChanged();
    }

    private List<String> galleryList;
    private Context context;
    int is=0;

    private OnClickAdapter clickAdapter;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(galleryList.get(i),clickAdapter,i);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public void setClickAdapter(OnClickAdapter clickAdapter) {
        this.clickAdapter = clickAdapter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.id_photo_image);
        }
        public void bindView(final String url, final OnClickAdapter clickAdapter, final int i){
            is=i;
            Picasso.with(context).load(url).into(imageView);
            imageView.setImageURI(Uri.parse(galleryList.get(i)));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickAdapter.onClickAdapter(imageView,url,i);
                }
            });
        }
    }
}

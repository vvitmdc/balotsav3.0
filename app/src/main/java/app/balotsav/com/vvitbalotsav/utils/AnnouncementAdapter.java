package app.balotsav.com.vvitbalotsav.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.balotsav.com.vvitbalotsav.R;
import app.balotsav.com.vvitbalotsav.model.Announcements;


public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.Holder> {
    public void setArrayList(ArrayList<Announcements> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    private ArrayList<Announcements> arrayList;

    public AnnouncementAdapter() {

    }

    @Override
    public AnnouncementAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View AnnounceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement, parent, false);
        return new Holder(AnnounceView);
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.Holder holder, int position) {

        holder.announcements.setText(arrayList.get(position).getNotice());
        holder.date.setText(arrayList.get(position).getDate());
        holder.time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView announcements, time, date;

        public Holder(View itemView) {
            super(itemView);
            announcements = itemView.findViewById(R.id.announcement_text);
            date = itemView.findViewById(R.id.id_date_stamp);
            time = itemView.findViewById(R.id.id_time_stamp);
        }
    }
}
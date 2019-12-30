package com.jubumam.SureFin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    public Service service;

    interface ServiceClickListener {
        void setServiceClick(String serviceDate);
    }

    private ServiceClickListener mListener;

    private List<Service> mItems = new ArrayList<>();

    public ServiceAdapter() {
    }

    public ServiceAdapter(ServiceClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<Service> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicelist, parent, false);
        final ServiceHolder viewHolder = new ServiceHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.setServiceClick(mItems.get(viewHolder.getAdapterPosition()));
//                }
//            }
//        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        service = mItems.get(position);
        holder.tv_servicedate.setText(service.getDate());


        if(service.getSumUsingTime1() == 0){
            holder.tv_careDayTime.setText("0");
        }else{
            holder.tv_careDayTime.setText(service.getSumUsingTime1()/60000+"");
        }
        if (service.getCount() != null && service.getCount().equals("TRUE")){
            holder.tv_bathDayCount.setText("1");
        }else {
            holder.tv_bathDayCount.setText("0");
        }
        if(service.getNursingTotal()!=null){
            holder.tv_nursingDayCount.setText("1회");
        }else{
            holder.tv_nursingDayCount.setText("0회");
        }
        holder.tv_nursingDayTime.setText(service.getNursingTotal());
            if(service.getNonPay()!=null && service.getNonPay().equals("제공")) {
            holder.tv_tenObjectDayCount.setText("1건");
        }else{
            holder.tv_tenObjectDayCount.setText("0건");
        }

    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ServiceHolder extends RecyclerView.ViewHolder {
        //        ItemServicelistBinding binding;
        TextView tv_servicedate;
        TextView tv_careDayTime;
        TextView tv_bathDayCount;
        TextView tv_nursingDayCount;
        TextView tv_nursingDayTime;
        TextView tv_tenObjectDayCount;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            tv_servicedate = itemView.findViewById(R.id.tv_servicedate);
            tv_careDayTime = itemView.findViewById(R.id.tv_careDayTime);
            tv_bathDayCount = itemView.findViewById(R.id.tv_bathDayCount);
            tv_nursingDayCount = itemView.findViewById(R.id.tv_nursingDayCount);
            tv_nursingDayTime = itemView.findViewById(R.id.tv_nursingDayTime);
            tv_tenObjectDayCount = itemView.findViewById(R.id.tv_tenObjectDayCount);
        }
    }
}
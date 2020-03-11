package com.jubumam.SureFin.ProtectorPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jubumam.SureFin.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.DepositViewHolder> {


    interface depositClidk {
        void dpClicked(Deposit model);
    }

    private depositClidk mListener;

    private List<Deposit> mItems = new ArrayList<>();

    public DepositAdapter() {
    }

    public DepositAdapter(depositClidk listener) {
        mListener = listener;
    }

    public void setItems(List<Deposit> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DepositViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deposit, parent, false);
        final DepositViewHolder viewHolder = new DepositViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final Deposit item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.dpClicked(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DepositViewHolder holder, int position) {
        DecimalFormat moneyfm = new DecimalFormat("###,###");
        Deposit item = mItems.get(position);
        holder.tv_date.setText(item.getDate());
        holder.tv_settlement.setText(moneyfm.format(item.getSettlement()) + " 원");
        holder.tv_charge.setText(moneyfm.format(item.getCharge()) + " 원");
        holder.tv_deposit.setText(moneyfm.format(item.getDeposit()) + " 원");
        holder.tv_balance.setText(moneyfm.format(item.getBalance()) + " 원");

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class DepositViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_settlement;
        private TextView tv_charge;
        private TextView tv_deposit;
        private TextView tv_balance;

        public DepositViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_settlement = itemView.findViewById(R.id.tv_settlement);
            tv_charge = itemView.findViewById(R.id.tv_charge);
            tv_deposit = itemView.findViewById(R.id.tv_deposit);
            tv_balance = itemView.findViewById(R.id.tv_balance);
        }
    }
}

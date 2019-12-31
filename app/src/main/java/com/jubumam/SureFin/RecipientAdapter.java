package com.jubumam.SureFin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.RecipientHolder> {
    private List<Recipient> mList = new ArrayList<>();
    private List<Recipient> mListFull;


    public RecipientAdapter(RecipientAdapter.OnRecipientClickListener listener) {
        mListener = listener;
    }

    public void setitems(List<Recipient> list) {
        mList = list;
        mListFull = new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipientAdapter.RecipientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipient, parent, false);
        RecipientAdapter.RecipientHolder recipientHolder = new RecipientAdapter.RecipientHolder(view);

        return recipientHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipientAdapter.RecipientHolder holder, int position) {
        final Recipient recipient = mList.get(position);
        holder.img_person.setImageBitmap(recipient.getBitmap());
        holder.tv_name.setText(recipient.getName());
        holder.tv_number.setText(recipient.getNumber());
        holder.tv_adress.setText(recipient.getAdress());
        holder.tv_gender.setText("("+recipient.getGender()+")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRecipientClick(mList.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPhoneClick(recipient.getNumber());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecipientHolder extends RecyclerView.ViewHolder {
        ImageView img_person;
        TextView tv_name;
        TextView tv_number;
        TextView tv_adress;
        TextView tv_gender;
        ImageView img_phone;

        public RecipientHolder(@NonNull View itemView) {
            super(itemView);
            img_person = itemView.findViewById(R.id.img_person);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_adress = itemView.findViewById(R.id.tv_adress);
            tv_number = itemView.findViewById(R.id.tv_number);
            img_phone = itemView.findViewById(R.id.img_phone);
            tv_gender = itemView.findViewById(R.id.tv_gender);

        }
    }

    public interface OnRecipientClickListener {
        void onRecipientClick(Recipient recipient);
        void onPhoneClick(String phone);
    }

    private RecipientAdapter.OnRecipientClickListener mListener;

    public void setOnRecipientClickListener(RecipientAdapter.OnRecipientClickListener listener) {
        mListener = listener;
    }


}
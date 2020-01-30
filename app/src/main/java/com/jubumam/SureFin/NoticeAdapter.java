package com.jubumam.SureFin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;

    interface NoticeListener {
        void setNoticeListener(Notice model);
    }

    private NoticeListener mListener;

    private void setOnRecipientClickListener(NoticeListener listener) {
        mListener = listener;
    }

    private List<Notice> mItems = new ArrayList<>();

    public NoticeAdapter() {
    }

    public NoticeAdapter(NoticeListener listener) {
        mListener = listener;
    }

    public void setItems(List<Notice> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notice, parent, false);
        final NoticeHolder viewHolder = new NoticeHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    final Notice item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.setNoticeListener(item);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeHolder holder, int position) {
        holder.onBind(mItems.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class NoticeHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_answerContents;
        private ImageView img_down;
        private int position;
        private Notice data;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_answerContents = itemView.findViewById(R.id.tv_answerContents);
            img_down = itemView.findViewById(R.id.img_down);
        }

        void onBind(Notice data, final int position) {
            this.data = data;
            this.position = position;
            tv_title.setText(data.getTitle());
            tv_date.setText(data.getDate());
            tv_answerContents.setText(data.getContents());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                }
            });

        }


        /**
         * 클릭된 Item의 상태 변경
         *
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView가 실제로 사라지게하는 부분
                    tv_answerContents.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    img_down.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }

}
package com.jubumam.surefin;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    private Context context;

    interface setAnswerClicked {
        void AnswerClick(Answer model);
    }

    private AnswerAdapter.setAnswerClicked mListener;

    private List<Answer> mItems = new ArrayList<>();

    public AnswerAdapter() {
    }

    public AnswerAdapter(AnswerAdapter.setAnswerClicked listener) {
        mListener = listener;
    }

    public void setItems(List<Answer> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer, parent, false);
        final AnswerAdapter.AnswerHolder viewHolder = new AnswerAdapter.AnswerHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    final Answer item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.AnswerClick(item);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AnswerHolder holder, int position) {
//        Answer item = mItems.get(position);
//        holder.tv_date.setText(item.getDate());
//        holder.tv_title.setText(item.getTitle());
//        holder.tv_contents.setText(item.getContents());
//        holder.tv_answerDate.setText(item.getAnswerDate());
//        holder.tv_answerContents.setText(item.getAnswer());
//        holder.position = position;
        holder.onBind(mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class AnswerHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_title;
        private TextView tv_contents;
        private TextView tv_answerDate;
        private TextView tv_answerContents;
        private ImageView img_down;
        private LinearLayout lin_answer;
        private int position;
        private Answer data;

        public AnswerHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_contents = itemView.findViewById(R.id.tv_contents);
            tv_answerDate = itemView.findViewById(R.id.tv_answerDate);
            tv_answerContents = itemView.findViewById(R.id.tv_answerContents);
            lin_answer = itemView.findViewById(R.id.lin_answer);
            img_down = itemView.findViewById(R.id.img_down);

        }


        void onBind(Answer data, final int position) {
            this.data = data;
            this.position = position;

            tv_date.setText(data.getDate());
            tv_title.setText(data.getTitle());
            tv_contents.setText(data.getContents());
            tv_answerDate.setText(data.getAnswerDate());
            tv_answerContents.setText(data.getAnswer());


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
                    lin_answer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    img_down.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
}

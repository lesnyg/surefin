package com.jubumam.surefin;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AfterMealFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String name = "";
    private String phoneNumber;
    private String address;
    private String address1;
    private String address2;
    private String quantity;
    private String orderPrice;
    private String paymentmethod = "";
    private String complete = "";
    private String orderTime;
    private String cpTime;

    private RecyclerView syRecyclerView;
    private SYOrderAdapter adapter;


    private AsyncTask<String, String, String> orderTask;
    private AsyncTask<String, String, String> completeTask;
    private Connection connection = null;
    private String strToday;

    private List<SongyeongOrder> list;
    private String responsibility;
    private String responsibilityID;
    private String area;
    private List<String> areaList;
    private String completeTime;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Timer timer;
    private Toolbar mToolbar;
    private int count = 0;
    private String ordermethod;
    private String firstOrder;

    private final static int TAKE_PICTURE = 1;
    private byte[] imageBytes;
    private ImageView dialog_imageview;
    private int s1, s2;
    private String s3, s4;
    private String imageString;
    private String ymd1, hms1;
    final String TAG = getClass().getSimpleName();
    private String orderTimeCheck1;
    private Boolean pic;
    private Bitmap bitmap;


    public AfterMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_after_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Login login = Login.getInstance();
        responsibility = login.getResponsibility();
        responsibilityID = login.getPersonId();
        Date today = new Date();
        strToday = new SimpleDateFormat("yyyy-MM-dd").format(today);

        syRecyclerView = view.findViewById(R.id.rv_aftermeal);

        adapter = new SYOrderAdapter(new SYOrderAdapter.syOrderClickListener() {


            @Override
            public void syClicked(SongyeongOrder model) {

            }

            @Override
            public void syCompleteClicked(SongyeongOrder model) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                    orderTimeCheck1 = model.getOrderTime();

            }

            @Override
            public void onPhoneClick(String phone) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

            @Override
            public void ImageClick(Bitmap bitmap) {
                Intent intent = new Intent(requireActivity(), SYGalleryDetailActivity.class);
                intent.putExtra("pic",bitmap);
                startActivity(intent);
            }
        });

        timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {

                orderTask = new OrderSyncTask().execute();
            }
        };
        timer.schedule(TT, 0, 60000); //Timer 실행

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();//타이머 종료
    }

    @Override
    public void onRefresh() {
        orderTask = new OrderSyncTask().execute();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class OrderSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            orderQuery();
            return null;

        }
        protected void onPostExecute(String result) {

        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }

    private void orderQuery() {

        strToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            Statement statement = connection.createStatement();
            list = new ArrayList<>();
            areaList = new ArrayList<>();
            byte bt[];
            ResultSet areaRS = statement.executeQuery("select * from Su_담당지역 where 담당자코드='" + responsibilityID + "'");
            while (areaRS.next()){
                String area = areaRS.getString("지역명");
                areaList.add(area);
            }

            for (int i = 0; i < areaList.size(); i++) {
                String area = areaList.get(i).toString();
                ResultSet resultSetlist = statement.executeQuery("select * from Su_주문리스트 where 일자='" + strToday + "' AND 지역 = '"+area+"' AND 주문형태 = '요양' order by 주소 DESC");
                while (resultSetlist.next()) {
                    name = resultSetlist.getString("주문자");
                    phoneNumber = resultSetlist.getString("전화번호");
                    address1 = resultSetlist.getString("주소1");
                    address2 = resultSetlist.getString("주소2");
                    quantity = resultSetlist.getString("수량");
                    orderPrice = resultSetlist.getString("금액");
                    paymentmethod = resultSetlist.getString("계산방법");
                    complete = resultSetlist.getString("배달확인");
                    orderTime = resultSetlist.getString("주문시간");
                    ordermethod = resultSetlist.getString("주문형태");
                    firstOrder = resultSetlist.getString("첫주문");


                    Blob bloblist = resultSetlist.getBlob("식사후사진");
                    if (bloblist != null) {
                        bt = bloblist.getBytes(1, (int) bloblist.length());
                        bitmap = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    } else {
                        bitmap = null;
                    }


                    count++;

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setItems(list);
                            syRecyclerView.setAdapter(adapter);
                        }
                    });
                    address = address1 +", " + address2;
                        list.add(new SongyeongOrder(name, phoneNumber, address, quantity, orderPrice, paymentmethod, complete, orderTime, ordermethod, firstOrder,bitmap));


                }

            }


            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:

                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
//                    final View view = inflater.inflate(R.layout.camera_image, null);
//                    dialog_imageview = view.findViewById(R.id.dialog_imageview);
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");

//                    dialog_imageview.setImageBitmap(bitmap);
//                    dialog_imageview.setScaleType(ImageView.ScaleType.FIT_XY);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageBytes = baos.toByteArray();
//                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //s1 = 8;

                    s2 = 32;
                    s4 = imageString;


                    try {

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);

                        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss");
                        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                        String nowDate = time.format(date);
                        SimpleDateFormat hm = new SimpleDateFormat("HH:mm");

                        s3 = nowDate;
                        ymd1 = ymd.format(date);
                        hms1 = hms.format(date);



                    } catch (Exception e) {

                    }
                    completeTask = new CompleteSyncTask().execute();

                }

                break;
    }

    }

    public class CompleteSyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isCancelled())
                return null;

            completeQuery();
            return null;

        }
        protected void onPostExecute(String result) {

        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    private void completeQuery() {

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://sql16ssd-005.localnet.kr/surefin1_db2020", "surefin1_db2020", "mam3535@@");
            PreparedStatement ps = connection.prepareStatement("UPDATE Su_주문리스트 SET 식사후사진 = ? where 일자 = '" + strToday + "' and 주문시간 = '" + orderTimeCheck1 + "'");
            byte[] s5 = imageBytes;
            ps.setBytes(1, s5);

            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static class SYOrderAdapter extends RecyclerView.Adapter<SYOrderAdapter.SYOrderViewHolder> {



        interface syOrderClickListener {
            void syClicked(SongyeongOrder model);
            void syCompleteClicked(SongyeongOrder model);
            void onPhoneClick(String phone);
            void ImageClick(Bitmap bitmap);
        }

        private SYOrderAdapter.syOrderClickListener mListener;

        private List<SongyeongOrder> mItems = new ArrayList<>();

        public SYOrderAdapter() {
        }

        public SYOrderAdapter(SYOrderAdapter.syOrderClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<SongyeongOrder> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SYOrderAdapter.SYOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_aftermeal, parent, false);
            final SYOrderAdapter.SYOrderViewHolder viewHolder = new SYOrderAdapter.SYOrderViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final SongyeongOrder item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.syClicked(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final SYOrderAdapter.SYOrderViewHolder holder, int position) {
            SongyeongOrder item = mItems.get(position);
            holder.tv_phoneNumber.setText(item.getPhoneNumber());
            holder.tv_address.setText( item.getAddress());
            holder.tv_quantity.setText("도시락 "+item.getQuantity() + " 개");
//            holder.tv_complete.setText(item.getComplete());
            if(item.getBitmap() != null) {
                holder.img_aftermeal.setImageBitmap(item.getBitmap());
                holder.lin_pic.setVisibility(View.VISIBLE);
                holder.btn_complete.setVisibility(View.GONE);
            }
            if(item.getFirstOrder().equals("True")){
                holder.tv_firstorder.setText("첫 구매 고객 입니다.");

            }else{
                holder.tv_firstorder.setVisibility(View.GONE);
            }
            holder.tv_name.setText(item.getName());
            holder.btn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.syCompleteClicked(mItems.get(holder.getAdapterPosition()));

                    }
                }
            });
            holder.img_aftermeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.ImageClick(mItems.get(holder.getAdapterPosition()).getBitmap());

                    }
                }
            });

            holder.img_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onPhoneClick(mItems.get(holder.getAdapterPosition()).getPhoneNumber());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class SYOrderViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_address;
            private TextView tv_phoneNumber;
            private TextView tv_quantity;
            private TextView tv_name;
            private TextView tv_firstorder;
            private Button btn_complete;
            private ImageView img_phone;
            private ImageView img_aftermeal;
            private LinearLayout lin_pic;

            public SYOrderViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
                tv_address = itemView.findViewById(R.id.tv_address);
                tv_quantity = itemView.findViewById(R.id.tv_quantity);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_firstorder = itemView.findViewById(R.id.tv_firstorder);
                btn_complete = itemView.findViewById(R.id.btn_complete);
                img_phone = itemView.findViewById(R.id.img_phone);
                img_aftermeal = itemView.findViewById(R.id.img_aftermeal);
                lin_pic = itemView.findViewById(R.id.lin_pic);
            }
        }
    }
}

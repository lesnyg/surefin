package com.jubumam.SureFin;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class Schedule_dialog {

    private Context context;

    public Schedule_dialog(Context context) {
        this.context = context;
    }


    public void callFunction(String date,String time,String schedule) {


        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.schedule_dialog);
        dlg.show();


        final TextView title = (TextView) dlg.findViewById(R.id.title);
        final TextView schedule_time = (TextView) dlg.findViewById(R.id.schedule_time);
        final TextView schedule_name = (TextView) dlg.findViewById(R.id.schedule_name);
        title.setText(date);
        schedule_time.setText(time);
        schedule_name.setText(schedule);

        schedule_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dlg.dismiss();


            }
        });

    }

}

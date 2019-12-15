package com.lemy.telpoo2lib.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lemy.telpoo2lib.R;
import com.lemy.telpoo2lib.utils.ListenBack;


public class DialogSupport {

    public interface YesNoListener{
        void onClick(boolean status);
    }

    public static void dialogYesNo(String title, final Context context, final YesNoListener ListenClick) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(title)
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    ListenClick.onClick(true);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Hủy", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ListenClick.onClick(false);
                });
        builder.show();
    }

    public static void dialogThongBao(String title,final Context context, final YesNoListener action) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle("Thông báo")
                .setMessage(title)
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    if (action!=null)
                        action.onClick(true);
                    dialogInterface.dismiss();
                });

        builder.show();
    }

    public static void spinner(Spinner spinner, Context context, String[] hmData, ListenBack listenBack){
        spinner( spinner,context, R.layout.telpoo_spinner_item, hmData,listenBack);
    }

    public static void spinner(Spinner spinner, Context context, int resource, String[] hmData, ListenBack listenBack) {

        if (spinner==null||context==null||hmData==null) return;
        spinner.setAdapter(new ArrayAdapter<>(context, resource, hmData));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = spinner.getSelectedItem().toString();
                if (listenBack != null) listenBack.OnListenBack(type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



}

package com.hinodesoftworks.kanagt.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hinodesoftworks.kanagt.R;

public class KanaChartDisplayDialog extends DialogFragment implements View.OnClickListener {

    //factory method for creating the dialog
    public static KanaChartDisplayDialog newInstance(String kana, String roman, int correct,
                                                        int incorrect, int prof, String diagramName){
        KanaChartDisplayDialog holder = new KanaChartDisplayDialog();

        Bundle args = new Bundle();
        args.putString("kana", kana);
        args.putString("roman", roman);
        args.putInt("correct", correct);
        args.putInt("incorrect", incorrect);
        args.putInt("prof", prof);
        args.putString("name", diagramName);

        holder.setArguments(args);
        return holder;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.dialog_kana_display, container, false);

        //get ui handles
        Button closeButton = (Button)parent.findViewById(R.id.kana_dialog_close_button);
        closeButton.setOnClickListener(this);



        return parent;
    }

    @Override
    public void onClick(View v) {
        this.dismissAllowingStateLoss();
    }

}

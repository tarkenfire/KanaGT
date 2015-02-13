package com.hinodesoftworks.kanagt.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView kanaDisplay, romanDisplay, correctDisplay, incorrectDisplay, profDisplay;

        kanaDisplay = (TextView)parent.findViewById(R.id.kana_dialog_kana_display);
        romanDisplay = (TextView)parent.findViewById(R.id.kana_dialog_roman_display);
        correctDisplay = (TextView)parent.findViewById(R.id.kana_dialog_correct_display);
        incorrectDisplay = (TextView)parent.findViewById(R.id.kana_dialog_incorrect_display);
        profDisplay = (TextView)parent.findViewById(R.id.kana_dialog_prof_display);

        //set data
        Bundle args = getArguments();

        kanaDisplay.setText(args.getString("kana"));
        romanDisplay.setText(args.getString("roman"));

        StringBuilder sb = new StringBuilder();
        int correct = args.getInt("correct", 0);
        int incorrect = args.getInt("incorrect", 0);
        int pCorrect = 0, pIncorrect = 0;

        int total = correct + incorrect;

        //prevent divide by zero exceptions
        if (total != 0){
            pCorrect = (correct  * 100) /total;
            pIncorrect = (incorrect * 100) / total;
        }

        sb.append("Times Correct: ");
        sb.append(correct);
        sb.append("(");
        sb.append(pCorrect);
        sb.append("%)");
        correctDisplay.setText(sb.toString());

        //reset stringbuilder
        sb.setLength(0);

        sb.append("Times Incorrect: ");
        sb.append(incorrect);
        sb.append("(");
        sb.append(pIncorrect);
        sb.append("%)");
        incorrectDisplay.setText(sb.toString());

        switch (args.getInt("prof", 0)){
            case 0:
                //0 is not a valid option for prof, therefore 0 == error
                //blank the textview in this case
                profDisplay.setText("");
                break;
            case 1: //unknown
                profDisplay.setText("Unknown");
                profDisplay.setTextColor(Color.RED);
                break;
            case 2: //known
                profDisplay.setText("Known");
                profDisplay.setTextColor(Color.parseColor("#B2B22B"));
                break;
            case 3: //well known
                profDisplay.setText("Well Known");
                profDisplay.setTextColor(Color.parseColor("#2983A8"));
                break;
            case 4: //mastered
                profDisplay.setText("Mastered");
                profDisplay.setTextColor(Color.parseColor("#2D9C2E"));
                break;
        }

        return parent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        this.dismissAllowingStateLoss();
    }

}

package com.hinodesoftworks.kanagt.dialogs;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.hinodesoftworks.kanagt.R;

public class ClearDatabasePrefDialog extends DialogPreference {

    private ClearDatabaseDialogListener mListener = null;

    public ClearDatabasePrefDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setDialogLayoutResource(R.layout.pref_clear_database);
    }

    public void setListener(ClearDatabaseDialogListener listener){
        this.mListener = listener;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult && mListener != null){
            mListener.onPositiveResult();
        }

    }


    public interface ClearDatabaseDialogListener{
        public void onPositiveResult();
    }
}

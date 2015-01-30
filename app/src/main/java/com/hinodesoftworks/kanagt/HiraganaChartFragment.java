package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HiraganaChartFragment extends Fragment {

    private OnHiraganaChartFragmentListener mListener;

    public HiraganaChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hiragana_chart, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHiraganaChartFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHiraganaChartFragmentListener {
        // TODO: Update argument type and name
        public void onHiraganaChartLoaded(HiraganaChartFragment sender);
        public void onHiraganaChartItemSelected();
    }

}

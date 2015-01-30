package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hinodesoftworks.kanagt.util.KanaChartViewAdapter;

public class HiraganaChartFragment extends Fragment {

    private OnHiraganaChartFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private Context ctx;

    public HiraganaChartFragment() {

    }

    public void setContext(Context context){
        this.ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View holder = inflater.inflate(R.layout.fragment_hiragana_chart, container, false);
        mRecyclerView = (RecyclerView) holder.findViewById(R.id.hira_chart);
        return holder;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 5));
        mListener.onHiraganaChartLoaded(this);

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

    public void showHiraChart(Cursor itemsToDisplay){
        KanaChartViewAdapter adapter = new KanaChartViewAdapter(itemsToDisplay);
        mRecyclerView.setAdapter(adapter);
    }

    public interface OnHiraganaChartFragmentListener {
        // TODO: Update argument type and name
        public void onHiraganaChartLoaded(HiraganaChartFragment sender);
        public void onHiraganaChartItemSelected();
    }

}
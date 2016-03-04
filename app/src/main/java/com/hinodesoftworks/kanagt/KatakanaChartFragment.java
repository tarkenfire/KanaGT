package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hinodesoftworks.kanagt.dialogs.KanaChartDisplayDialog;
import com.hinodesoftworks.kanagt.util.KanaChartViewAdapter;

public class KatakanaChartFragment extends Fragment
                implements KanaChartViewAdapter.OnChartItemClickListener{

    private OnKatakanaChartFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private Context ctx;

    public KatakanaChartFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){
        this.ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View holder = inflater.inflate(R.layout.fragment_katakana_chart, container, false);
        mRecyclerView = (RecyclerView) holder.findViewById(R.id.kata_chart);
        return holder;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 5));
        mListener.onKatakanaChartLoaded(this);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnKatakanaChartFragmentListener) activity;
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

    public void showKataChart(Cursor itemsToDisplay){
        KanaChartViewAdapter adapter = new KanaChartViewAdapter(itemsToDisplay, this);
        mRecyclerView.setAdapter(adapter);
    }

    public void showDetailDialog(String kana, String roman, int correct, int incorrect,
                                 int prof, String diagramLoc ){
        KanaChartDisplayDialog d = KanaChartDisplayDialog.newInstance(kana, roman, correct,
                incorrect, prof, diagramLoc);
        d.show(getActivity().getFragmentManager(), "detailsFragment");
    }

    @Override
    public void onItemClicked(String selectedCharacter) {
        mListener.onKatakanaChartItemSelected(this, selectedCharacter);
    }

    public interface OnKatakanaChartFragmentListener {
        void onKatakanaChartLoaded(KatakanaChartFragment sender);
        void onKatakanaChartItemSelected(KatakanaChartFragment sender, String kana);
    }

}

package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hinodesoftworks.kanagt.util.KanaListViewAdapter;


public class HiraganaListFragment extends Fragment {

    private OnHiraListInteractionListener mListener;
    private RecyclerView mRecyclerView;

    public HiraganaListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View holder = inflater.inflate(R.layout.fragment_hiragana_list, container, false);
        mRecyclerView = (RecyclerView) holder.findViewById(R.id.hira_list);
        return holder;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListener.onHiraListFragmentLoaded(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHiraListInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    public void displayHiraList(Cursor itemsToDisplay){
        KanaListViewAdapter adapter = new KanaListViewAdapter(itemsToDisplay);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHiraListInteractionListener {
        public void onHiraListFragmentLoaded(HiraganaListFragment sender);
    }

}

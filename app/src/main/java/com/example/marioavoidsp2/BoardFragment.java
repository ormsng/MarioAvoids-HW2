package com.example.marioavoidsp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class BoardFragment extends Fragment {
    private ListView board_li;
    private MyDB allRecords;
    private CallBack_userProtocol callback;

    public void setCallback(CallBack_userProtocol callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        findViews(view);
        initViews();
        return view;
    }

    public void initViews() {
        allRecords = new Gson().fromJson(MSPv3.getInstance().getStringSP("MY_DB", ""), MyDB.class);
        if (allRecords != null) {
            ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(getActivity(), android.R.layout.simple_expandable_list_item_1, allRecords.getRecords());
            board_li.setAdapter(adapter);
        }

        board_li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = allRecords.getRecords().get(i).getName();
                double lat = allRecords.getRecords().get(i).getLat();
                double lon = allRecords.getRecords().get(i).getLon();
                callback.setLocation(name, lat, lon);
            }
        });


    }

    private void findViews(View view) {
        board_li = view.findViewById(R.id.board_li);
    }
}
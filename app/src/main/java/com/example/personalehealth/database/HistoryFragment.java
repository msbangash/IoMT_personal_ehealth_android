package com.example.personalehealth.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.personalehealth.R;
import com.example.personalehealth.ui.adapter.HistoryAdapter;

import java.util.List;


public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<History> list;
    HistoryAdapter adapter;
    MyDatabase myDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        myDatabase = Room.databaseBuilder(getContext(), MyDatabase.class, "MY_History").
                allowMainThreadQueries().build();

        initializeViews(view);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        getData();
        return view;
    }

    private void getData() {
        list = myDatabase.historyDao().getData();
        if (list.isEmpty()) {
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new HistoryAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initializeViews(View view) {

        recyclerView = view.findViewById(R.id.rv_history);

    }
}
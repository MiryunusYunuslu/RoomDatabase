package com.example.architecturecomp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.architecturecomp.R;
import com.example.architecturecomp.View.Adapter.MainAdapter;
import com.example.architecturecomp.View.Database.MainData;
import com.example.architecturecomp.View.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button buttonAdd, buttonReset;
    private RecyclerView recyclerView;
    private List<MainData> dataList = new ArrayList<>();
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        database = RoomDB.getInstance(this);
        dataList = database.maindao().getData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainAdapter adapter = new MainAdapter(getApplicationContext(), dataList, MainActivity.this);
        recyclerView.setAdapter(adapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    MainData data = new MainData();
                    data.setText(text);
                    database.maindao().insert(data);
                    editText.setText("");
                    dataList.clear();
                    dataList.addAll(database.maindao().getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.maindao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.maindao().getData());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initialize() {
        editText = findViewById(R.id.enterText);
        buttonAdd = findViewById(R.id.AddText);
        buttonReset = findViewById(R.id.Reset);
        recyclerView = findViewById(R.id.recyclrView);
    }
}
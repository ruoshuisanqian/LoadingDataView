package com.tangram.loadingdatalayout.sample;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tangram.loadingdatalayout.R;
import com.tangram.loadingdataview.LoadingDataBaseLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getBaseContext());
                textView.setBackgroundColor(Color.GREEN);
                textView.setText("item " + position);
                return textView;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        final LoadingDataBaseLayout layout = (LoadingDataBaseLayout)findViewById(R.id.progressBar2);
        Button loading = (Button) findViewById(R.id.text1);
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.loading();
            }
        });

        Button error = (Button) findViewById(R.id.text2);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.error();
            }
        });
        Button finish = (Button) findViewById(R.id.text3);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.finish();
            }
        });

        Button empty = (Button) findViewById(R.id.text4);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.empty();
            }
        });

        layout.setReloadDataListener(new LoadingDataBaseLayout.ReloadDataListener() {
            @Override
            public void reloadData() {
                Toast.makeText(MainActivity.this, "重新加载数据", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

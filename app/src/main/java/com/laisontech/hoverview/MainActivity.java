package com.laisontech.hoverview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.laisontech.hoverviewlibrary.HoverViewLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HoverViewLayout hoverView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hoverView = (HoverViewLayout) findViewById(R.id.hoverView);
        btn = (Button) findViewById(R.id.btn);
        initEvent();
    }


    private void initEvent() {
        hoverView.addHoverLayout(R.layout.both_view)
                .addTopLayout(R.layout.top_view)
                .addBottomLayout(R.layout.bottom_view)
                .showHoverView();

        ListView listView = (ListView) hoverView.getBottomView().findViewById(R.id.lv);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("第" + i + "个项目");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        hoverView.setListViewHeightBasedOnChildren(listView, adapter);

        hoverView.setViewMoveGone(btn);
    }

}

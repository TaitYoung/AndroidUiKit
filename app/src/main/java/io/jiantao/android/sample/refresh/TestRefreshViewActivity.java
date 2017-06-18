package io.jiantao.android.sample.refresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.jiantao.android.sample.R;
import io.jiantao.android.uikit.refresh.ClassicIRefreshHeaderView;
import io.jiantao.android.uikit.refresh.ISwipeRefreshLayout;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by jiantao on 2017/6/15.
 */

public class TestRefreshViewActivity extends Activity {

    ISwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        refreshLayout = (ISwipeRefreshLayout) findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new ISwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        });

        refreshLayout.setRefreshView(new ClassicIRefreshHeaderView(this));
        handler.sendEmptyMessageDelayed(0, 3000);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(TextItem.class, new TextItemViewBinder());
        recyclerView.setAdapter(adapter);

        /* Mock the data */
//        TextItem textItem = new TextItem("world");

        Items items = new Items();
        for (int i = 0; i < 50; i++) {
            TextItem textItem = new TextItem("world no."+i);
            items.add(textItem);
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:// refreshing ture
                    refreshLayout.setRefreshing(true);
                    handler.sendEmptyMessageDelayed(1, 3000);
                    break;

                case 1:// refreshing false
                    refreshLayout.setRefreshing(false);
                    break;
            }
            return false;
        }
    });

}

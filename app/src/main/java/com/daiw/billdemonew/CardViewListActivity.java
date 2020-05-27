package com.daiw.billdemonew;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardViewListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_list);

        List<String> items = new ArrayList<>();
        items.add("Credit Account");
        items.add("Freight Charge");

        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new CardViewAdapter(this,items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>{

        private Context mContext;
        private List<String> items = new ArrayList<>();

        public CardViewAdapter(Context mContext,List<String> items){
            this.mContext = mContext;
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_card_view,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mContentIv.setText(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private CardView mCardView;
            private ImageView mImageIv;
            private TextView mContentIv;

            public ViewHolder(View itemView) {
                super(itemView);
                mCardView = itemView.findViewById(R.id.card_view);
                mImageIv = itemView.findViewById(R.id.image);
                mContentIv = itemView.findViewById(R.id.content);


            }
        }
    }

}

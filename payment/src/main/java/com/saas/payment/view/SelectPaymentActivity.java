package com.saas.payment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.saas.payment.R;
import com.saas.payment.adapter.SelectPaymentAdapter;
import com.saas.payment.bean.SelectPaymentBean;

import java.util.ArrayList;
import java.util.List;

public class SelectPaymentActivity extends AppCompatActivity implements SelectPaymentAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<SelectPaymentBean> mList = new ArrayList<>();

    private SelectPaymentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0;i<5;i++){
            SelectPaymentBean bean = new SelectPaymentBean();
            mList.add(bean);
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.ac_select_payment_rlv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SelectPaymentAdapter(this,mList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClickListener(int position) {

    }
}

package com.saas.payment.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.saas.payment.R;
import com.saas.payment.adapter.SelectPaymentAdapter;
import com.saas.payment.bean.CollectChargesBean;
import com.saas.payment.bean.SelectPaymentBean;

import java.util.ArrayList;
import java.util.List;

public class SelectPaymentActivity extends AppCompatActivity implements SelectPaymentAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private List<SelectPaymentBean> mList = new ArrayList<>();

    private SelectPaymentAdapter mAdapter;

    private List<CollectChargesBean> chargesBeanList = new ArrayList<>();

    private double totalAmount = 0.0;
    private String currency = "";

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
        chargesBeanList = getIntent().getParcelableArrayListExtra("chargesBeanList");
        totalAmount = getIntent().getDoubleExtra("totalAmount",0.0);
        currency = getIntent().getStringExtra("currency");
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.ac_select_payment_rlv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SelectPaymentAdapter(this,mList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        if(chargesBeanList != null && chargesBeanList.size() > 0 && Double.compare(totalAmount,0) == 1 && !TextUtils.isEmpty(currency)){
            Intent intent = new Intent(SelectPaymentActivity.this, PaymentActivity.class);
            intent.putParcelableArrayListExtra("chargesBeanList", (ArrayList<? extends Parcelable>) chargesBeanList);
            intent.putExtra("totalAmount", totalAmount);
            intent.putExtra("currency", currency);
            intent.putExtra("paymentProvider", "");
            startActivity(intent);
        }
    }
}

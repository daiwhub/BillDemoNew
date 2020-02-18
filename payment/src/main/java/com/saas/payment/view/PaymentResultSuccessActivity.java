package com.saas.payment.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.saas.payment.R;

import org.w3c.dom.Text;

import java.time.chrono.ThaiBuddhistEra;

public class PaymentResultSuccessActivity extends AppCompatActivity {

    private TextView mPayAmountTv;
    private TextView mPaymentTimeTv;
    private TextView mPaymentProviderTv;

    private double mTotalAmount = 0.0;
    private String mPaymentProvider = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result_success);

        initData();
        initView();
    }

    private void initData() {
        mTotalAmount = getIntent().getDoubleExtra("totalAmount",0.0);
        mPaymentProvider = getIntent().getStringExtra("paymentProvider");
    }

    private void initView() {
        mPayAmountTv = findViewById(R.id.ac_payment_result_success_amount_tv);
        mPaymentTimeTv = findViewById(R.id.ac_payment_result_success_time_tv);
        mPaymentProviderTv = findViewById(R.id.ac_payment_result_success_provider_tv);
    }
}

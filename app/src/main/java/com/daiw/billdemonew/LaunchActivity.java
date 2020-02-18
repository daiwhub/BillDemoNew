package com.daiw.billdemonew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.saas.payment.view.CollectChargesActivity;
import com.saas.payment.view.PaymentMethodActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.start_bill_page_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, BillListActivity.class));
            }
        });
        findViewById(R.id.start_payment_page_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, PaymentMethodActivity.class));
            }
        });
        findViewById(R.id.start_collect_charges_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LaunchActivity.this, CollectChargesActivity.class));
            }
        });
    }
}

package com.daiw.billdemonew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LaunchActivity.this,Main2Activity.class),100);
            }
        });

        findViewById(R.id.start_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LaunchActivity.this,ImageActivity.class),100);
            }
        });

        findViewById(R.id.start_card_view_list_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LaunchActivity.this,CardViewListActivity.class),100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Log.e("LaunchActivity" , "==============requestCode==========");
        }
    }
}

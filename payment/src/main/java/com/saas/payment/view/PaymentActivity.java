package com.saas.payment.view;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.saas.payment.R;
import com.saas.payment.bean.CollectChargesBean;
import com.saas.payment.utils.SpannableStringUtils;
import com.saas.payment.view.dialog.QueryPaymentResultsDialog;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements QueryPaymentResultsDialog.OnClickListener{

    private TextView mNeedToPayTv;
    private ImageView mQrCodeIv;
    private Button mResultBtn;

    private CollectChargesBean chargesBean;

    private List<CollectChargesBean> chargesBeanList = new ArrayList<>();

    private double totalAmount = 0.0;
    private String currency = "";
    private String paymentProvider = "";

    private QueryPaymentResultsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dialog = new QueryPaymentResultsDialog(this,this);

        initData();
        initView();
        setListener();
    }

    private void initData() {
        chargesBeanList = getIntent().getParcelableArrayListExtra("chargesBeanList");
        totalAmount = getIntent().getDoubleExtra("totalAmount",0.0);
        currency = getIntent().getStringExtra("currency");
        paymentProvider = getIntent().getStringExtra("paymentProvider");
    }

    private void initView() {
        mNeedToPayTv = findViewById(R.id.ac_payment_need_to_pay_tv);
        mQrCodeIv = findViewById(R.id.ac_payment_qr_code_iv);
        mResultBtn = findViewById(R.id.ac_payment_bottom_query_result_btn);

        if(Double.compare(totalAmount,0) == 1 && !TextUtils.isEmpty(currency)){
            String text = "Need to pay " + totalAmount + " " + currency;
            SpannableString spannableString = SpannableStringUtils.spannablePaymentNeedToPayString(
                    text,
                    12,
                    text.length(),
                    ContextCompat.getColor(getApplicationContext(),R.color.color_b9683f),
                    ContextCompat.getColor(getApplicationContext(),R.color.color_red));
            mNeedToPayTv.setText(spannableString);
        }
    }

    private void setListener() {
        mResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null){
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog != null){
            dialog.dismiss();
        }
    }

    @Override
    public void onWait() {
        //等待支付结果

        Intent intent = new Intent(this,PaymentResultSuccessActivity.class);
        intent.putExtra("totalAmount",totalAmount);
        intent.putExtra("paymentProvider",paymentProvider);
        startActivity(intent);
    }

    @Override
    public void onCancel() {
        //取消支付

    }
}

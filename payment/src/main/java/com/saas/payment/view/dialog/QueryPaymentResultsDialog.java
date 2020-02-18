package com.saas.payment.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.saas.payment.R;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-18  20:39
 *
 *         ***************************
 */
public class QueryPaymentResultsDialog extends Dialog {

    public QueryPaymentResultsDialog(@NonNull Context context,OnClickListener listener) {
        super(context);
        setContentView(R.layout.dialog_query_payment_results);

        initView(listener);
    }

    private void initView(final OnClickListener listener) {
        Button mWaitBtn = findViewById(R.id.dialog_query_payment_results_wait_btn);
        Button mCancelBtn = findViewById(R.id.dialog_query_payment_results_cancel_btn);

        mWaitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener != null){
                    listener.onWait();
                }
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener != null){
                    listener.onCancel();
                }
            }
        });
    }

    public interface OnClickListener{
        void onWait();
        void onCancel();
    }
}

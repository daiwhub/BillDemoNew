package com.saas.payment.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.saas.payment.R;
import com.saas.payment.adapter.SelectPaymentMethodAdapter;
import com.saas.payment.bean.ItemPaymentMethodBean;
import com.saas.payment.utils.SoftKeyboardUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-12  14:00
 *
 *         ***************************
 */
public class PaymentMethodActivity extends AppCompatActivity implements SelectPaymentMethodAdapter.OnItemClickListener {

    private static final int CHECK_FREIGHT_STATE = 0x001;
    private static final int CHECK_TAX_STATE = 0x002;

    private LinearLayout mSenderLl;
    private RecyclerView mSenderRlv;
    private LinearLayout mSenderInputLl;
    private EditText mSenderInputEdt;
    private RadioButton mSenderInputBtn;

    private LinearLayout mDelivarLl;
    private RadioButton mDelivarRbtn;

    private LinearLayout mOtherLl;
    private RadioButton mOtherRbtn;
    private LinearLayout mOtherInputLl;
    private EditText mOtherInputEdt;
    private RadioButton mOtherInputBtn;

    private LinearLayout mTaxLl;
    private RadioButton mTaxAccountRbtn;
    private RadioButton mTaxDeliverRbtn;
    private LinearLayout mTaxInputLl;
    private EditText mTaxInputEdt;
    private RadioButton mTaxInputBtn;

    private RadioButton mCompleteBtn;

    private SelectPaymentMethodAdapter mMethodAdapter;

    private List<ItemPaymentMethodBean> methodBeanList = new ArrayList<>();

    /**
     * 是否国际件（true是，false否）
     */
    private boolean isInternational = true;

    /**
     * 是否校验过了运费月结账号校验
     */
    private int freightCheckState = -1;
    /**
     * 是否校验过了税金月结账号校验
     */
    private int taxCheckState = -1;

    /**
     * 选择的运费支付方式
     */
    private int mPaymentMethodFreight = 7;
    /**
     * 选择的税金支付方式
     */
    private int mPaymentMethodTax = 2;

    private MyHandler myHandler;

    private class MyHandler extends Handler {
        private WeakReference reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PaymentMethodActivity activity = (PaymentMethodActivity) reference.get();
            if (activity != null) {
                switch (msg.what) {
                    case CHECK_FREIGHT_STATE:
                        activity.handlerCheckFreightSuccess();
                        break;
                    case CHECK_TAX_STATE:
                        activity.handlerCheckTaxSuccess();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        myHandler = new MyHandler(this);

        initData();
        initView();
        setListeners();
    }

    private void initData() {
        methodBeanList.add(new ItemPaymentMethodBean("Cash", 7, true));
        methodBeanList.add(new ItemPaymentMethodBean("Online", 17, false));
        methodBeanList.add(new ItemPaymentMethodBean("POS", 18, false));
        methodBeanList.add(new ItemPaymentMethodBean("Cheque", 19, false));
        methodBeanList.add(new ItemPaymentMethodBean("Bank Card Transfer", 20, false));
        //寄付月结
        methodBeanList.add(new ItemPaymentMethodBean("Credit Account Payment", 4, false));
    }

    private void initView() {
        //寄付
        mSenderLl = findViewById(R.id.ac_payment_method_sender_pay_layout);
        mSenderRlv = findViewById(R.id.ac_payment_method_sender_pay_rlv);
        //寄付月结卡号
        mSenderInputLl = findViewById(R.id.ac_payment_method_sender_pay_input_ll);
        mSenderInputEdt = findViewById(R.id.ac_payment_method_sender_pay_input_edt);
        mSenderInputBtn = findViewById(R.id.ac_payment_method_sender_pay_input_btn);
        //到付
        mDelivarLl = findViewById(R.id.ac_payment_method_deliver_pay_layout);
        mDelivarRbtn = findViewById(R.id.ac_payment_method_deliver_pay_rbtn);
        //转第三方
        mOtherLl = findViewById(R.id.ac_payment_method_other_pay_layout);
        mOtherRbtn = findViewById(R.id.ac_payment_method_other_pay_rbtn);
        //转第三方月结卡号
        mOtherInputLl = findViewById(R.id.ac_payment_method_other_pay_input_ll);
        mOtherInputEdt = findViewById(R.id.ac_payment_method_other_pay_input_edt);
        mOtherInputBtn = findViewById(R.id.ac_payment_method_other_pay_input_btn);
        //税金
        mTaxLl = findViewById(R.id.ac_payment_method_tax_pay_layout);
        //税金月结
        mTaxAccountRbtn = findViewById(R.id.ac_payment_method_tax_account_pay_rbtn);
        //税金到付
        mTaxDeliverRbtn = findViewById(R.id.ac_payment_method_tax_pay_rbtn);
        //税金月结卡号
        mTaxInputLl = findViewById(R.id.ac_payment_method_tax_pay_input_ll);
        mTaxInputEdt = findViewById(R.id.ac_payment_method_tax_pay_input_edt);
        mTaxInputBtn = findViewById(R.id.ac_payment_method_tax_pay_input_btn);

        //确定
        mCompleteBtn = findViewById(R.id.ac_payment_method_sure_btn);

        mMethodAdapter = new SelectPaymentMethodAdapter(this, methodBeanList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mSenderRlv.setLayoutManager(gridLayoutManager);
        mSenderRlv.setAdapter(mMethodAdapter);

        if (isInternational) {
            //国际件显示税金支付方式
            mTaxLl.setVisibility(View.VISIBLE);
        } else {
            mTaxLl.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        mSenderInputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString();
                if(!TextUtils.isEmpty(result)){
                    mSenderInputBtn.setChecked(true);
                }else {
                    mSenderInputBtn.setChecked(false);
                    //运费月结待验证
                    freightCheckState = 0;
                    checkResult();
                }
            }
        });
        mOtherInputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString();
                if(!TextUtils.isEmpty(result)){
                    mOtherInputBtn.setChecked(true);
                }else {
                    mOtherInputBtn.setChecked(false);
                    //运费月结待验证
                    freightCheckState = 0;
                    checkResult();
                }
            }
        });
        mTaxInputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString();
                if(!TextUtils.isEmpty(result)){
                    mTaxInputBtn.setChecked(true);
                }else {
                    mTaxInputBtn.setChecked(false);
                    //税金月结待验证
                    taxCheckState = 0;
                    checkResult();
                }
            }
        });

        //到付
        mDelivarRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelSenderPaymentMethod();
                //转第三方
                mOtherRbtn.setChecked(false);
                //不显示运费月结输入框
                showInputLayout(-1);
                //运费支付方式
                mPaymentMethodFreight = 9;
                //判断底部按钮
                checkResult();
            }
        });
        //转第三方月结
        mOtherRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelSenderPaymentMethod();
                //到付
                mDelivarRbtn.setChecked(false);
                //显示运费转第三方月结输入框
                showInputLayout(1);
                //运费支付方式
                mPaymentMethodFreight = 3;
                //判断底部按钮
                checkResult();
            }
        });
        //税金月结
        mTaxAccountRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaxRbtn(true);
                //显示税金月结账号输入框
                showTaxInputLayout(true);
                //税金支付方式
                mPaymentMethodTax = 4;
                //判断底部按钮
                checkResult();
            }
        });
        //税金到付
        mTaxDeliverRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTaxRbtn(false);
                //不显示税金月结账号输入框
                showTaxInputLayout(false);
                //税金支付方式
                mPaymentMethodTax = 2;
                //判断底部按钮
                checkResult();
            }
        });
        //寄付月结确定按钮
        mSenderInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                SoftKeyboardUtil.hideSoftKeyboard(PaymentMethodActivity.this);
                String account = mSenderInputEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(account)) {
                    Toast.makeText(getApplicationContext(), "加载中...", Toast.LENGTH_LONG).show();
                    myHandler.sendEmptyMessageDelayed(CHECK_FREIGHT_STATE, 3000);
                }
            }
        });
        //转第三方月结确定按钮
        mOtherInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                SoftKeyboardUtil.hideSoftKeyboard(PaymentMethodActivity.this);
                String account = mOtherInputEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(account)) {
                    Toast.makeText(getApplicationContext(), "加载中...", Toast.LENGTH_LONG).show();
                    myHandler.sendEmptyMessageDelayed(CHECK_FREIGHT_STATE, 3000);
                }
            }
        });
        //税金月结确定按钮
        mTaxInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                SoftKeyboardUtil.hideSoftKeyboard(PaymentMethodActivity.this);
                String account = mTaxInputEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(account)) {
                    Toast.makeText(getApplicationContext(), "加载中...", Toast.LENGTH_LONG).show();
                    myHandler.sendEmptyMessageDelayed(CHECK_TAX_STATE, 3000);
                }
            }
        });

        //确定
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void changeTaxRbtn(boolean isChange) {
        //税金月结
        mTaxAccountRbtn.setChecked(isChange);
        //税金到付
        mTaxDeliverRbtn.setChecked(!isChange);
    }

    /*
     * @Description : 取消寄付的所有支付放回寺
     * @Params :
     * @Author : daiw
     * @Date : 2020-02-13
     */
    private void setCancelSenderPaymentMethod() {
        if (methodBeanList != null && methodBeanList.size() > 0) {
            for (ItemPaymentMethodBean bean : methodBeanList) {
                if (bean != null) {
                    bean.setChecked(false);
                }
            }
            mMethodAdapter.notifyDataSetChanged();
        }
    }

    /*
     * @Description : 显示与隐藏税金月结账号输入框
     * @Params :
     * @Author : daiw
     * @Date : 2020-02-14
     */
    private void showTaxInputLayout(boolean isShow) {
        if (isShow) {
            mTaxInputLl.setVisibility(View.VISIBLE);
            //税金月结待验证
            taxCheckState = 0;
        } else {
            mTaxInputLl.setVisibility(View.GONE);
            //不需要验证
            taxCheckState = -1;
        }
    }

    private void showInputLayout(int type) {
        switch (type) {
            case 0:
                //显示寄付月结
                mSenderInputLl.setVisibility(View.VISIBLE);
                mOtherInputLl.setVisibility(View.GONE);
                mOtherInputEdt.setText("");

                //运费月结待验证
                freightCheckState = 0;
                break;
            case 1:
                //显示寄付月结
                mSenderInputLl.setVisibility(View.GONE);
                mSenderInputEdt.setText("");
                mOtherInputLl.setVisibility(View.VISIBLE);

                //运费月结待验证
                freightCheckState = 0;
                break;
            default:
                //非月结
                mSenderInputLl.setVisibility(View.GONE);
                mOtherInputLl.setVisibility(View.GONE);
                mSenderInputEdt.setText("");
                mOtherInputEdt.setText("");

                //不需要验证
                freightCheckState = -1;
                break;
        }
    }

    /*
     * @Description : 运费月结验证通过
     * @Params :
     * @Author : daiw
     * @Date : 2020-02-14
     */
    public void handlerCheckFreightSuccess() {
        freightCheckState = 1;
        //判断底部按钮
        checkResult();
    }

    /*
     * @Description : 税金月结验证通过
     * @Params :
     * @Author : daiw
     * @Date : 2020-02-14
     */
    public void handlerCheckTaxSuccess() {
        taxCheckState = 1;
        //判断底部按钮
        checkResult();
    }

    private void checkResult() {
        if ((mPaymentMethodFreight == 4 && freightCheckState == 1)
                || (mPaymentMethodFreight != 4 && freightCheckState == -1)) {
            if (isInternational) {
                //国际件需要判断税金支付方式
                if ((mPaymentMethodTax == 4 && taxCheckState == 1)
                        || (mPaymentMethodTax != 4 && taxCheckState == -1)) {
                    //确定
                    mCompleteBtn.setChecked(true);
                    return;
                }
            }else {
                //确定
                mCompleteBtn.setChecked(true);
                return;
            }
        }
        //确定
        mCompleteBtn.setChecked(false);
    }

    @Override
    public void itemClickListener(int position) {
        //到付
        mDelivarRbtn.setChecked(false);
        //转第三方
        mOtherRbtn.setChecked(false);
        if (methodBeanList != null && methodBeanList.size() > position && position >= 0) {
            ItemPaymentMethodBean bean = methodBeanList.get(position);
            mPaymentMethodFreight = bean.getType();
            if (4 == bean.getType()) {
                //显示运费寄付月结输入框
                showInputLayout(0);
            } else {
                //不显示运费月结输入框
                showInputLayout(-1);
            }
        }
        //判断底部按钮
        checkResult();
    }
}

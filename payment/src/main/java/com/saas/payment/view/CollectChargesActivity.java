package com.saas.payment.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.saas.payment.R;
import com.saas.payment.adapter.CollectChargesAdapter;
import com.saas.payment.bean.CollectChargesBean;
import com.saas.payment.bean.WaybillFeeVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CollectChargesActivity extends AppCompatActivity implements CollectChargesAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ConstraintLayout mBottomCl;
    private ImageView mCheckIv;
    private TextView mAllSelectedTv;
    private TextView mTotalAmountTv;
    private RadioButton mCollectRbtn;

    private List<CollectChargesBean> mList = new ArrayList<>();

    private CollectChargesAdapter mAdapter;

    /**
     * 是否全选
     */
    private boolean isAllSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_charges);

        initData();
        initView();
        setListener();
    }

    private void setListener() {
        //全选
        mBottomCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList != null && mList.size() > 0) {
                    for (CollectChargesBean chargesBean : mList) {
                        if (chargesBean != null) {
                            chargesBean.setSelect(!isAllSelected);
                            mAdapter.notifyDataSetChanged();
                            //显示总价格
                            setCollect();
                        }
                    }
                    isAllSelected = !isAllSelected;
                }
            }
        });
        //计价
        mCollectRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollectChargesActivity.this, SelectPaymentActivity.class));
            }
        });
    }

    /*
     * @Description : 显示总价格
     * @Params :
     * @Author : daiw
     * @Date : 2020-02-17
     */
    private void setCollect() {
        double totalAmount = 0;
        String currency = "";
        boolean isAll = true;
        if (mList != null && mList.size() > 0) {
            for (CollectChargesBean chargesBean : mList) {
                if (chargesBean != null) {
                    if (chargesBean.isSelect()) {
                        totalAmount = BigDecimal.valueOf(totalAmount).add(BigDecimal.valueOf(chargesBean.getAmount())).doubleValue();
                        if (TextUtils.isEmpty(currency)) {
                            currency = chargesBean.getCurrency();
                        }
                    } else {
                        isAll = false;
                    }
                }
            }
        }
        if (isAll) {
            mCheckIv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_checked));
        } else {
            mCheckIv.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_uncheck));
        }

        mTotalAmountTv.setText("Toltal: " + totalAmount + " " + currency);
    }

    private void initData() {
        //第一条
        CollectChargesBean chargesBean = new CollectChargesBean();
        chargesBean.setSelect(false);
        chargesBean.setWaybillno("SF1011625371573");
        chargesBean.setAmount(10.00);
        chargesBean.setCurrency("SGD");
        List<WaybillFeeVo> waybillFeeVoList = new ArrayList<>();
        WaybillFeeVo waybillFeeVo1 = new WaybillFeeVo();
        waybillFeeVo1.setWaybillFeeName("运费");
        waybillFeeVo1.setCurrencyName("SGD");
        waybillFeeVo1.setFeeAmt(8.00);
        waybillFeeVo1.setFeeTypeCode("1");
        waybillFeeVo1.setPaymentTypeCode("1");
        waybillFeeVo1.setSettletmentTypeCode("1");

        WaybillFeeVo waybillFeeVo2 = new WaybillFeeVo();
        waybillFeeVo2.setWaybillFeeName("燃油附加费");
        waybillFeeVo2.setCurrencyName("SGD");
        waybillFeeVo2.setFeeAmt(2.00);
        waybillFeeVo2.setFeeTypeCode("14");
        waybillFeeVo2.setPaymentTypeCode("1");
        waybillFeeVo2.setSettletmentTypeCode("1");

        waybillFeeVoList.add(waybillFeeVo1);
        waybillFeeVoList.add(waybillFeeVo2);
        chargesBean.setWaybillFeeVos(waybillFeeVoList);
        //第二条
        CollectChargesBean chargesBean2 = new CollectChargesBean();
        chargesBean2.setSelect(false);
        chargesBean2.setWaybillno("SF1011625371573");
        chargesBean2.setAmount(7.00);
        chargesBean2.setCurrency("SGD");
        List<WaybillFeeVo> waybillFeeVoList2 = new ArrayList<>();
        WaybillFeeVo waybillFeeVo3 = new WaybillFeeVo();
        waybillFeeVo3.setWaybillFeeName("运费");
        waybillFeeVo3.setCurrencyName("SGD");
        waybillFeeVo3.setFeeAmt(6.00);
        waybillFeeVo3.setFeeTypeCode("1");
        waybillFeeVo3.setPaymentTypeCode("1");
        waybillFeeVo3.setSettletmentTypeCode("1");

        WaybillFeeVo waybillFeeVo4 = new WaybillFeeVo();
        waybillFeeVo4.setWaybillFeeName("包装服务费");
        waybillFeeVo4.setCurrencyName("SGD");
        waybillFeeVo4.setFeeAmt(1.00);
        waybillFeeVo4.setFeeTypeCode("14");
        waybillFeeVo4.setPaymentTypeCode("1");
        waybillFeeVo4.setSettletmentTypeCode("1");

        waybillFeeVoList2.add(waybillFeeVo3);
        waybillFeeVoList2.add(waybillFeeVo4);
        chargesBean2.setWaybillFeeVos(waybillFeeVoList2);

        //第三条
        CollectChargesBean chargesBean3 = new CollectChargesBean();
        chargesBean3.setSelect(false);
        chargesBean3.setWaybillno("SF1011625371573");
        chargesBean3.setAmount(6.00);
        chargesBean3.setCurrency("SGD");
        List<WaybillFeeVo> waybillFeeVoList3 = new ArrayList<>();
        WaybillFeeVo waybillFeeVo5 = new WaybillFeeVo();
        waybillFeeVo5.setWaybillFeeName("运费");
        waybillFeeVo5.setCurrencyName("SGD");
        waybillFeeVo5.setFeeAmt(6.00);
        waybillFeeVo5.setFeeTypeCode("1");
        waybillFeeVo5.setPaymentTypeCode("1");
        waybillFeeVo5.setSettletmentTypeCode("1");

        waybillFeeVoList3.add(waybillFeeVo5);
        chargesBean3.setWaybillFeeVos(waybillFeeVoList3);

        mList.add(chargesBean);
        mList.add(chargesBean2);
        mList.add(chargesBean3);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.ac_collect_charges_rlv);
        mBottomCl = findViewById(R.id.ac_collect_charges_bottom);
        mCheckIv = findViewById(R.id.ac_collect_charges_bottom_iv);
        mAllSelectedTv = findViewById(R.id.ac_collect_charges_bottom_tv);
        mTotalAmountTv = findViewById(R.id.ac_collect_charges_bottom_total_amount_tv);
        mCollectRbtn = findViewById(R.id.ac_collect_charges_bottom_collect_tv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CollectChargesAdapter(this, mList, this);
        mRecyclerView.setAdapter(mAdapter);
        //显示总价格
        setCollect();
    }

    @Override
    public void onItemClickListener(int position) {
        mList.get(position).setSelect(!mList.get(position).isSelect());
        mAdapter.notifyDataSetChanged();
        //显示总价格
        setCollect();
    }

    @Override
    public void onClickDetails(int position) {

    }
}

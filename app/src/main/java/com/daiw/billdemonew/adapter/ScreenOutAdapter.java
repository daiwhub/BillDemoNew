package com.daiw.billdemonew.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daiw.billdemonew.R;
import com.daiw.billdemonew.bean.BillBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-08  11:07
 *
 *         ***************************
 */
public class ScreenOutAdapter extends RecyclerView.Adapter<ScreenOutAdapter.ViewHolder> {

    private Context mContext;
    private List<BillBean> mList;

    private SwipeLayout childCurrentExpandedSwipeLayout;

    public ScreenOutAdapter(Context mContext, List<BillBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList == null || mList.size() <= position || position < 0) {
            return;
        }
        BillBean mBillBean = mList.get(position);

        if (!TextUtils.isEmpty(mBillBean.getTaskType())) {
            if ("0".equals(mBillBean.getTaskType())) {
                //寄付
                holder.mWaybillStateTv.setText("Shipper Pay");
                holder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_shipper_pay));
            } else {
                //到付
                holder.mWaybillStateTv.setText("Receiver Pay");
                holder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_receiver_pay));
            }
        }
        if (!TextUtils.isEmpty(mBillBean.getWayBillNo())) {
            //运单号
            holder.mWaybillNoTv.setText(mBillBean.getWayBillNo());
        } else {
            //运单号
            holder.mWaybillNoTv.setText("");
        }
        //支付方式
        String mReceivable = "";
        String mPayment = "";
        if (!TextUtils.isEmpty(mBillBean.getPayMethod())) {
            if ("0".equals(mBillBean.getPayMethod())) {
                //现金
                mPayment = "Cash ";
            }
        }
        mReceivable = mPayment + mBillBean.getAmountReality() + " SGD";
        //应收金额
        holder.mReceivableTv.setText(mReceivable);

        mReceivable = mPayment + mBillBean.getAmountShould() + " SGD";
        //实收金额
        holder.mReceiptsTv.setText(mReceivable);

        if (Double.compare(mBillBean.getAmountReality(), mBillBean.getAmountShould()) == 0) {
            //应收 == 实收
            //实收金额
            holder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_black));
            //异常数量
            holder.mSignAccountTv.setVisibility(View.GONE);
        } else {
            //应收 != 实收
            //实收金额
            holder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
            //异常数量
            holder.mSignAccountTv.setVisibility(View.VISIBLE);
            //异常数量
            holder.mSignAccountTv.setText("!");
        }

        String mFeeType = "";
        //费用类型
        if (!TextUtils.isEmpty(mBillBean.getFeeType())) {
            if ("0".equals(mBillBean.getFeeType())) {
                //运费
                mFeeType = "Freight";
            } else if ("99".equals(mBillBean.getFeeType())) {
                //税金
                mFeeType = "Tax";
            } else if ("5".equals(mBillBean.getFeeType())) {
                //COD
                mFeeType = "COD";
            }
            //费用类型
            holder.mFeeTypeTv.setText(mFeeType);
        } else {
            //费用类型
            holder.mFeeTypeTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillBean.getSenderContract())) {
            //寄件联系人
            holder.mSenderContractTv.setText(mBillBean.getSenderContract());
        } else {
            //寄件联系人
            holder.mSenderContractTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillBean.getDeliverContract())) {
            //收件联系人
            holder.mDeliverContractTv.setText(mBillBean.getDeliverContract());
        } else {
            //收件联系人
            holder.mDeliverContractTv.setText("");
        }

        //展开/收起
        holder.mExpandedStateIv.setVisibility(View.GONE);

        //侧滑
        holder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.mSwipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                if (null != childCurrentExpandedSwipeLayout && childCurrentExpandedSwipeLayout != layout) {
                    childCurrentExpandedSwipeLayout.close(true);
                }
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                childCurrentExpandedSwipeLayout = layout;
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeLayout mSwipeLayout;
        private LinearLayout mRightLayout;
        private CheckBox mAffirmCbx;

        private TextView mWaybillStateTv;
        private TextView mWaybillNoTv;
        private TextView mSignAccountTv;
        private TextView mReceivableTv;
        private TextView mReceiptsTv;
        private TextView mFeeTypeTv;
        private TextView mSenderContractTv;
        private TextView mDeliverContractTv;
        private ImageView mExpandedStateIv;

        public ViewHolder(View itemView) {
            super(itemView);
            //侧滑
            mSwipeLayout = itemView.findViewById(R.id.swipe);
            //侧滑布局
            mRightLayout = itemView.findViewById(R.id.item_right_wrapper);
            //侧滑确认按钮
            mAffirmCbx = itemView.findViewById(R.id.item_right_wrapper_cbx);

            //寄/到付
            mWaybillStateTv = itemView.findViewById(R.id.item_child_waybill_state_tv);
            //运单号
            mWaybillNoTv = itemView.findViewById(R.id.item_child_waybill_no_tv);
            //异常数量
            mSignAccountTv = itemView.findViewById(R.id.item_child_sign_account_tv);
            //应收金额
            mReceivableTv = itemView.findViewById(R.id.item_child_amount_receivable_tv);
            //实收金额
            mReceiptsTv = itemView.findViewById(R.id.item_child_amount_receipts_tv);
            //费用类型
            mFeeTypeTv = itemView.findViewById(R.id.item_child_fee_type_tv);
            //寄件联系人
            mSenderContractTv = itemView.findViewById(R.id.item_child_sender_contract_tv);
            //收件联系人
            mDeliverContractTv = itemView.findViewById(R.id.item_child_deliver_contract_tv);
            //展开/收起
            mExpandedStateIv = itemView.findViewById(R.id.item_child_expanded_state_iv);
        }
    }
}

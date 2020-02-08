package com.daiw.billdemonew.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daiw.billdemonew.R;
import com.daiw.billdemonew.bean.BillBean;
import com.daiw.billdemonew.bean.BillGroupBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-03  17:46
 *
 *         ***************************
 */
public class MyAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<BillGroupBean> mGroupList;
    private List<List<BillBean>> mChildList;
    private LayoutInflater mInflater;

    private SwipeLayout groupCurrentExpandedSwipeLayout;
    private SwipeLayout childCurrentExpandedSwipeLayout;

    public MyAdapter(Context mContext, List<BillGroupBean> mGroupList, List<List<BillBean>> mChildList) {
        this.mContext = mContext;
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;
        mInflater = LayoutInflater.from(mContext);
    }

    //父项的个数
    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    //某个父项的子项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    //获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    //获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    //父项的Id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_child, parent, false);

            mViewHolder = new ViewHolderGroup();
            //侧滑
            mViewHolder.mSwipeLayout = convertView.findViewById(R.id.swipe);
            //侧滑布局
            mViewHolder.mRightLayout = convertView.findViewById(R.id.item_right_wrapper);
            //侧滑确认按钮
            mViewHolder.mAffirmCbx = convertView.findViewById(R.id.item_right_wrapper_cbx);

            //寄/到付
            mViewHolder.mWaybillStateTv = convertView.findViewById(R.id.item_child_waybill_state_tv);
            //运单号
            mViewHolder.mWaybillNoTv = convertView.findViewById(R.id.item_child_waybill_no_tv);
            //异常数量
            mViewHolder.mSignAccountTv = convertView.findViewById(R.id.item_child_sign_account_tv);
            //应收金额
            mViewHolder.mReceivableTv = convertView.findViewById(R.id.item_child_amount_receivable_tv);
            //实收金额
            mViewHolder.mReceiptsTv = convertView.findViewById(R.id.item_child_amount_receipts_tv);
            //费用类型
            mViewHolder.mFeeTypeTv = convertView.findViewById(R.id.item_child_fee_type_tv);
            //寄件联系人
            mViewHolder.mSenderContractTv = convertView.findViewById(R.id.item_child_sender_contract_tv);
            //收件联系人
            mViewHolder.mDeliverContractTv = convertView.findViewById(R.id.item_child_deliver_contract_tv);
            //展开/收起
            mViewHolder.mExpandedStateIv = convertView.findViewById(R.id.item_child_expanded_state_iv);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolderGroup) convertView.getTag();
        }
        BillGroupBean mBillGroupBean = mGroupList.get(groupPosition);
        if (null == mBillGroupBean) {
            return convertView;
        }

        if (!TextUtils.isEmpty(mBillGroupBean.getTaskType())) {
            if ("0".equals(mBillGroupBean.getTaskType())) {
                //寄付
                mViewHolder.mWaybillStateTv.setText("Shipper Pay");
                mViewHolder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_shipper_pay));
            } else {
                //到付
                mViewHolder.mWaybillStateTv.setText("Receiver Pay");
                mViewHolder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_receiver_pay));
            }
        }
        if (!TextUtils.isEmpty(mBillGroupBean.getWayBillNo())) {
            //运单号
            mViewHolder.mWaybillNoTv.setText(mBillGroupBean.getWayBillNo());
        } else {
            //运单号
            mViewHolder.mWaybillNoTv.setText("");
        }
        //支付方式
        String mReceivable = "";
        String mPayment = "";
        if (!TextUtils.isEmpty(mBillGroupBean.getPayMethod())) {
            if ("0".equals(mBillGroupBean.getPayMethod())) {
                //现金
                mPayment = "Cash ";
            }else if("1".equals(mBillGroupBean.getPayMethod())){
                //在线支付
                mPayment = "Online ";
            }else if("2".equals(mBillGroupBean.getPayMethod())){
                //POS支付
                mPayment = "POS ";
            }else if("3".equals(mBillGroupBean.getPayMethod())){
                //银行转账
                mPayment = "Bank Transfer ";
            }else if("4".equals(mBillGroupBean.getPayMethod())){
                //支票
                mPayment = "Cheque ";
            }
        }
        mReceivable = mPayment + mBillGroupBean.getAmountReality() + " SGD";
        //应收金额
        mViewHolder.mReceivableTv.setText(mReceivable);

        mReceivable = mPayment + mBillGroupBean.getAmountShould() + " SGD";
        //实收金额
        mViewHolder.mReceiptsTv.setText(mReceivable);

        if (Double.compare(mBillGroupBean.getAmountReality(), mBillGroupBean.getAmountShould()) == 0) {
            //应收 == 实收
            //实收金额
            mViewHolder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_black));
        } else {
            //应收 != 实收
            //实收金额
            mViewHolder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
        }

        String mFeeType = "";
        //费用类型
        if (!TextUtils.isEmpty(mBillGroupBean.getFeeType())) {
            if ("0".equals(mBillGroupBean.getFeeType())) {
                //运费
                mFeeType = "Freight";
            } else if ("99".equals(mBillGroupBean.getFeeType())) {
                //税金
                mFeeType = "Tax";
            } else if ("5".equals(mBillGroupBean.getFeeType())) {
                //COD
                mFeeType = "COD";
            }
            //费用类型
            mViewHolder.mFeeTypeTv.setText(mFeeType);
        } else {
            //费用类型
            mViewHolder.mFeeTypeTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillGroupBean.getSenderContract())) {
            //寄件联系人
            mViewHolder.mSenderContractTv.setText(mBillGroupBean.getSenderContract());
        } else {
            //寄件联系人
            mViewHolder.mSenderContractTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillGroupBean.getDeliverContract())) {
            //收件联系人
            mViewHolder.mDeliverContractTv.setText(mBillGroupBean.getDeliverContract());
        } else {
            //收件联系人
            mViewHolder.mDeliverContractTv.setText("");
        }

        if(mBillGroupBean.getSize() > 0) {
            //异常数量
            mViewHolder.mSignAccountTv.setVisibility(View.VISIBLE);
            mViewHolder.mSignAccountTv.setText(String.valueOf(mBillGroupBean.getSize()));
            //展开/收起
            mViewHolder.mExpandedStateIv.setVisibility(View.VISIBLE);
        }else {
            mViewHolder.mSignAccountTv.setVisibility(View.GONE);
            //展开/收起
            mViewHolder.mExpandedStateIv.setVisibility(View.GONE);
        }

        if (isExpanded) {
            //收起
            mViewHolder.mExpandedStateIv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_expand));
        } else {
            //收起
            mViewHolder.mExpandedStateIv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.icon_fold));
        }

        //侧滑
        mViewHolder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        mViewHolder.mSwipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                if(null != groupCurrentExpandedSwipeLayout && groupCurrentExpandedSwipeLayout != layout){
                    groupCurrentExpandedSwipeLayout.close(true);
                }
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                groupCurrentExpandedSwipeLayout = layout;
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

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderGroup mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_child, parent, false);

            mViewHolder = new ViewHolderGroup();
            //侧滑
            mViewHolder.mSwipeLayout = convertView.findViewById(R.id.swipe);
            //侧滑布局
            mViewHolder.mRightLayout = convertView.findViewById(R.id.item_right_wrapper);
            //侧滑确认按钮
            mViewHolder.mAffirmCbx = convertView.findViewById(R.id.item_right_wrapper_cbx);

            //寄/到付
            mViewHolder.mWaybillStateTv = convertView.findViewById(R.id.item_child_waybill_state_tv);
            //运单号
            mViewHolder.mWaybillNoTv = convertView.findViewById(R.id.item_child_waybill_no_tv);
            //异常数量
            mViewHolder.mSignAccountTv = convertView.findViewById(R.id.item_child_sign_account_tv);
            //应收金额
            mViewHolder.mReceivableTv = convertView.findViewById(R.id.item_child_amount_receivable_tv);
            //实收金额
            mViewHolder.mReceiptsTv = convertView.findViewById(R.id.item_child_amount_receipts_tv);
            //费用类型
            mViewHolder.mFeeTypeTv = convertView.findViewById(R.id.item_child_fee_type_tv);
            //寄件联系人
            mViewHolder.mSenderContractTv = convertView.findViewById(R.id.item_child_sender_contract_tv);
            //收件联系人
            mViewHolder.mDeliverContractTv = convertView.findViewById(R.id.item_child_deliver_contract_tv);
            //展开/收起
            mViewHolder.mExpandedStateIv = convertView.findViewById(R.id.item_child_expanded_state_iv);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolderGroup) convertView.getTag();
        }

        BillBean mBillBean = mChildList.get(groupPosition).get(childPosition);
        if (null == mBillBean) {
            return convertView;
        }
        if (!TextUtils.isEmpty(mBillBean.getTaskType())) {
            if ("0".equals(mBillBean.getTaskType())) {
                //寄付
                mViewHolder.mWaybillStateTv.setText("Shipper Pay");
                mViewHolder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_shipper_pay));
            } else {
                //到付
                mViewHolder.mWaybillStateTv.setText("Receiver Pay");
                mViewHolder.mWaybillStateTv.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_receiver_pay));
            }
        }
        if (!TextUtils.isEmpty(mBillBean.getWayBillNo())) {
            //运单号
            mViewHolder.mWaybillNoTv.setText(mBillBean.getWayBillNo());
        } else {
            //运单号
            mViewHolder.mWaybillNoTv.setText("");
        }
        //支付方式
        String mReceivable = "";
        String mPayment = "";
        if (!TextUtils.isEmpty(mBillBean.getPayMethod())) {
            if ("0".equals(mBillBean.getPayMethod())) {
                //现金
                mPayment = "Cash ";
            }else if("1".equals(mBillBean.getPayMethod())){
                //在线支付
                mPayment = "Online ";
            }else if("2".equals(mBillBean.getPayMethod())){
                //POS支付
                mPayment = "POS ";
            }else if("3".equals(mBillBean.getPayMethod())){
                //银行转账
                mPayment = "Bank Transfer ";
            }else if("4".equals(mBillBean.getPayMethod())){
                //支票
                mPayment = "Cheque ";
            }
        }
        mReceivable = mPayment + mBillBean.getAmountReality() + " SGD";
        //应收金额
        mViewHolder.mReceivableTv.setText(mReceivable);

        mReceivable = mPayment + mBillBean.getAmountShould() + " SGD";
        //实收金额
        mViewHolder.mReceiptsTv.setText(mReceivable);

        if (Double.compare(mBillBean.getAmountReality(), mBillBean.getAmountShould()) == 0) {
            //应收 == 实收
            //实收金额
            mViewHolder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_black));
            //异常数量
            mViewHolder.mSignAccountTv.setVisibility(View.GONE);
        } else {
            //应收 != 实收
            //实收金额
            mViewHolder.mReceiptsTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
            //异常数量
            mViewHolder.mSignAccountTv.setVisibility(View.VISIBLE);
            //异常数量
            mViewHolder.mSignAccountTv.setText("!");
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
            mViewHolder.mFeeTypeTv.setText(mFeeType);
        } else {
            //费用类型
            mViewHolder.mFeeTypeTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillBean.getSenderContract())) {
            //寄件联系人
            mViewHolder.mSenderContractTv.setText(mBillBean.getSenderContract());
        } else {
            //寄件联系人
            mViewHolder.mSenderContractTv.setText("");
        }

        if (!TextUtils.isEmpty(mBillBean.getDeliverContract())) {
            //收件联系人
            mViewHolder.mDeliverContractTv.setText(mBillBean.getDeliverContract());
        } else {
            //收件联系人
            mViewHolder.mDeliverContractTv.setText("");
        }

        //展开/收起
        mViewHolder.mExpandedStateIv.setVisibility(View.GONE);

        //侧滑
        mViewHolder.mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        mViewHolder.mSwipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                if(null != childCurrentExpandedSwipeLayout && childCurrentExpandedSwipeLayout != layout){
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

        return convertView;
    }

    //子项是否可选中,如果要设置子项的点击事件,需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolderGroup {
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
    }

}

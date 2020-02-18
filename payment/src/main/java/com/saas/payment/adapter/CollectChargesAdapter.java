package com.saas.payment.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.saas.payment.R;
import com.saas.payment.bean.CollectChargesBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-17  10:05
 *
 *         ***************************
 */
public class CollectChargesAdapter extends RecyclerView.Adapter<CollectChargesAdapter.ViewHolder> {

    private Context mContext;
    private List<CollectChargesBean> mList;
    private OnItemClickListener listener;

    public CollectChargesAdapter(Context mContext, List<CollectChargesBean> mList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collect_charges, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList == null || mList.size() <= position || position < 0) {
            return;
        }
        CollectChargesBean chargesBean = mList.get(position);
        if (chargesBean == null) {
            return;
        }
        if(chargesBean.isSelect()){
            holder.mCheckIv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.icon_checked));
        }else {
            holder.mCheckIv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.icon_uncheck));
        }

        if (!TextUtils.isEmpty(chargesBean.getWaybillno())) {
            holder.mWaybillNoTv.setText(chargesBean.getWaybillno());
        } else {
            holder.mWaybillNoTv.setText("");
        }

        holder.mAmountTv.setText(chargesBean.getAmount() + " " + chargesBean.getCurrency());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                if(listener != null){
                    listener.onItemClickListener(index);
                }
            }
        });
        holder.mDetailsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCheckIv;
        private TextView mWaybillNoTv;
        private TextView mAmountTv;
        private TextView mDetailsTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mCheckIv = itemView.findViewById(R.id.item_collect_charges_check_iv);
            mWaybillNoTv = itemView.findViewById(R.id.item_collect_charges_waybillno_tv);
            mAmountTv = itemView.findViewById(R.id.item_collect_charges_sum_tv);
            mDetailsTv = itemView.findViewById(R.id.item_collect_charges_details_tv);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);

        void onClickDetails(int position);
    }
}

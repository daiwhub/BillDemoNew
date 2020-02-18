package com.saas.payment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saas.payment.R;
import com.saas.payment.bean.SelectPaymentBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-17  17:02
 *
 *         ***************************
 */
public class SelectPaymentAdapter extends RecyclerView.Adapter<SelectPaymentAdapter.ViewHolder> {

    private Context mContext;
    private List<SelectPaymentBean> mList;
    private OnItemClickListener listener;

    public SelectPaymentAdapter(Context mContext, List<SelectPaymentBean> mList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_payment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mList == null || mList.size() <= position || position < 0){
            return;
        }
        SelectPaymentBean bean = mList.get(position);

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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView paymentTypeIv;
        private ImageView qrCodeIv;

        public ViewHolder(View itemView) {
            super(itemView);
            paymentTypeIv = itemView.findViewById(R.id.item_select_payment_left_iv);
            qrCodeIv = itemView.findViewById(R.id.item_select_payment_right_iv);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }
}

package com.saas.payment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.saas.payment.R;
import com.saas.payment.bean.ItemPaymentMethodBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-12  15:23
 *
 *         ***************************
 */
public class SelectPaymentMethodAdapter extends RecyclerView.Adapter<SelectPaymentMethodAdapter.ViewHolder> {

    private Context mContext;
    private List<ItemPaymentMethodBean> methodBeanList;
    private LayoutInflater mInflater;
    private OnItemClickListener itemClickListener;

    public SelectPaymentMethodAdapter(Context mContext, List<ItemPaymentMethodBean> methodBeanList, OnItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.methodBeanList = methodBeanList;
        this.itemClickListener = itemClickListener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_payment_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (methodBeanList == null || methodBeanList.size() <= position || position < 0) {
            return;
        }
        ItemPaymentMethodBean bean = methodBeanList.get(position);
        if (!TextUtils.isEmpty(bean.getName())) {
            holder.mItemBtn.setText(bean.getName());
        }
        holder.mItemBtn.setChecked(bean.isChecked());
        holder.mItemBtn.setTag(position);
        holder.mItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                for (int i = 0; i < methodBeanList.size(); i++) {
                    if (i == index) {
                        methodBeanList.get(i).setChecked(true);
                    } else {
                        methodBeanList.get(i).setChecked(false);
                    }
                }
                notifyDataSetChanged();
                if (itemClickListener != null) {
                    itemClickListener.itemClickListener(index);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return methodBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RadioButton mItemBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemBtn = itemView.findViewById(R.id.item_payment_method_rbtn);
        }
    }

    public interface OnItemClickListener {
        void itemClickListener(int position);
    }
}

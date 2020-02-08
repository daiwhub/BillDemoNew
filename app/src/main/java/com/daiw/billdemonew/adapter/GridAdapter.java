package com.daiw.billdemonew.adapter;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.daiw.billdemonew.R;
import com.daiw.billdemonew.bean.GridItemBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-07  21:21
 *
 *         ***************************
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GridItemBean> mList;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    public GridAdapter(Context mContext, List<GridItemBean> mList,OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_grid, parent, false);

            mViewHolder = new ViewHolder();

            mViewHolder.mItemBtn = convertView.findViewById(R.id.item_grid_rbtn);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if (mList == null || mList.size() <= 0) {
            return convertView;
        }

        GridItemBean bean = mList.get(position);

        mViewHolder.mItemBtn.setText(bean.getText());
        mViewHolder.mItemBtn.setChecked(bean.isSelected());

        mViewHolder.mItemBtn.setTag(position);
        mViewHolder.mItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if(mListener != null){
                    mListener.onItemClickListener(pos);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        private RadioButton mItemBtn;
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }
}

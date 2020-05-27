package com.daiw.billdemonew;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daiw.billdemonew.adapter.GridAdapter;
import com.daiw.billdemonew.bean.GridItemBean;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-06  14:57
 *
 *         ***************************
 */
public class SelectPopup extends PopupWindow {

    private Context mContext;
    private View mView;
    private GridView mGridView;
    private Button mCancelBtn;
    private List<GridItemBean> mList;

    private GridAdapter mAdapter;

    private OnItemCallback mCallback;
    private OnDismissCallback callback;

    public SelectPopup(Context context, View locationView, String title, List<GridItemBean> mList, OnItemCallback mCallback,OnDismissCallback callback) {
        super(context);
        this.mList = mList;
        this.mContext = context;
        this.mCallback = mCallback;
        this.callback = callback;
        init(context, locationView, title);
    }

    private void init(Context context, View locationView, String title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_layout, null);
        TextView mTitleTv = mView.findViewById(R.id.head_hint_tv);
        mTitleTv.setText(title);

        mGridView = mView.findViewById(R.id.gridview);
        mCancelBtn = mView.findViewById(R.id.cancel_btn);

        mAdapter = new GridAdapter(context, mList, new GridAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (mCallback != null) {
                    mCallback.onResult(position);
                }
            }
        });
        mGridView.setAdapter(mAdapter);

        // 设置外部可点击
        this.setOutsideTouchable(false);

        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.mView);

        // 设置弹出窗体的宽和高
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x10000000);
        this.setBackgroundDrawable(new BitmapDrawable());
        //在一个控件的下方
        this.showAsDropDown(locationView,-5,3);

//        setBackgroundAlpha(0.5f);//设置屏幕透明度

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                // popupWindow隐藏时恢复屏幕正常透明度
//                setBackgroundAlpha(1.0f);
                if(null != callback){
                    callback.onDismiss();
                }
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = alpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public interface OnItemCallback {
        void onResult(int position);
    }

    public interface OnDismissCallback{
        void onDismiss();
    }
}

package com.daiw.billdemonew.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daiw.billdemonew.R;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020/3/15  6:17 PM
 *
 *         ***************************
 */
public class ExtendTextView extends LinearLayout {

    private Context mContext;
    private View mView;
    private TextView mTextView;
    private ImageView mRightIv;

    public ExtendTextView(Context context) {
        this(context,null);
    }

    public ExtendTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.custom_extend_textview,this,true);
        mTextView = mView.findViewById(R.id.custom_extend_tv);
        mRightIv = mView.findViewById(R.id.custom_extend_iv);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ExtendTextView);

        String text = typedArray.getString(R.styleable.ExtendTextView_text);
        int textColor = typedArray.getColor(R.styleable.ExtendTextView_textColor, ContextCompat.getColor(mContext,R.color.color_black));
        float textSize = typedArray.getDimension(R.styleable.ExtendTextView_textSize,8);
        int drawableRightResId = typedArray.getResourceId(R.styleable.ExtendTextView_drawableRight,1000);
        int drawablePadding = typedArray.getInteger(R.styleable.ExtendTextView_drawablePadding,5);

        mTextView.setText(text);
        mTextView.setTextSize(textSize);
        mTextView.setTextColor(textColor);

        setDrawableRight(drawableRightResId);
        setDrawablePadding(drawablePadding);

        typedArray.recycle();
    }

    public void setText(String text){
        mTextView.setText(text);
    }

    public void setTextSize(float textSize){
        mTextView.setTextSize(textSize);
    }

    public void setDrawableRight(int resId){
        mRightIv.setImageResource(resId);
    }

    public void setDrawablePadding(int drawablePadding){
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.rightMargin = drawablePadding;
        mRightIv.setLayoutParams(params);
    }

    public void setOnClickListener(final OnClickListener listener){
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDrawableRotate(true);
                listener.onClick(v);
            }
        });
    }

    public void setDrawableRotate(boolean isUp){
        if(isUp){
            mRightIv.animate().rotation(180);
        }else {
            mRightIv.animate().rotation(0);
        }
    }

    public interface OnClickListener{
        void onClick(View view);
    }
}

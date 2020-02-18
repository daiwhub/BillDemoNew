package com.saas.payment.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-18  11:22
 *
 *         ***************************
 */
public class SpannableStringUtils {

     /*
      * @Description : Payment页面展示付款费用
      * @Params :
      * @Author : daiw
      * @Date : 2020-02-18
      */
    public static SpannableString spannablePaymentNeedToPayString(String text,int startPosition,int endPosition,int startColor,int endColor){
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(startColor),0,startPosition, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(endColor),startPosition,endPosition, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return spannableString;
    }

}

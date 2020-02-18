package com.saas.payment.bean;

import android.os.Parcel;
import android.os.Parcelable;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-17  10:23
 *
 *         ***************************
 */
public class WaybillFeeVo implements Parcelable {

    /**
     *费用名称
     */
    private String waybillFeeName;
    /**
     *费用类型
     */
    private String feeTypeCode;
    /**
     *金额
     */
    private double feeAmt;
    /**
     *币种
     */
    private String currencyName;
    /**
     *付款类型：1寄付，2到付，3转第三方
     */
    private String paymentTypeCode;
    /**
     *结算类型：1现结，2月结
     */
    private String settletmentTypeCode;
    /**
     *月结卡号
     */
    private String customerAcctCode;

    public WaybillFeeVo() {
    }

    protected WaybillFeeVo(Parcel in) {
        waybillFeeName = in.readString();
        feeTypeCode = in.readString();
        feeAmt = in.readDouble();
        currencyName = in.readString();
        paymentTypeCode = in.readString();
        settletmentTypeCode = in.readString();
        customerAcctCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(waybillFeeName);
        dest.writeString(feeTypeCode);
        dest.writeDouble(feeAmt);
        dest.writeString(currencyName);
        dest.writeString(paymentTypeCode);
        dest.writeString(settletmentTypeCode);
        dest.writeString(customerAcctCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WaybillFeeVo> CREATOR = new Creator<WaybillFeeVo>() {
        @Override
        public WaybillFeeVo createFromParcel(Parcel in) {
            return new WaybillFeeVo(in);
        }

        @Override
        public WaybillFeeVo[] newArray(int size) {
            return new WaybillFeeVo[size];
        }
    };

    public String getWaybillFeeName() {
        return waybillFeeName;
    }

    public void setWaybillFeeName(String waybillFeeName) {
        this.waybillFeeName = waybillFeeName;
    }

    public String getFeeTypeCode() {
        return feeTypeCode;
    }

    public void setFeeTypeCode(String feeTypeCode) {
        this.feeTypeCode = feeTypeCode;
    }

    public double getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(double feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    public String getSettletmentTypeCode() {
        return settletmentTypeCode;
    }

    public void setSettletmentTypeCode(String settletmentTypeCode) {
        this.settletmentTypeCode = settletmentTypeCode;
    }

    public String getCustomerAcctCode() {
        return customerAcctCode;
    }

    public void setCustomerAcctCode(String customerAcctCode) {
        this.customerAcctCode = customerAcctCode;
    }

    @Override
    public String toString() {
        return "WaybillFeeVo{" +
                "waybillFeeName='" + waybillFeeName + '\'' +
                ", feeTypeCode='" + feeTypeCode + '\'' +
                ", feeAmt=" + feeAmt +
                ", currencyName='" + currencyName + '\'' +
                ", paymentTypeCode='" + paymentTypeCode + '\'' +
                ", settletmentTypeCode='" + settletmentTypeCode + '\'' +
                ", customerAcctCode='" + customerAcctCode + '\'' +
                '}';
    }
}

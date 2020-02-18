package com.saas.payment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-17  10:10
 *
 *         ***************************
 */
public class CollectChargesBean implements Parcelable {

    private boolean isSelect;
    private String waybillno;
    private double amount;
    private String currency;
    private List<WaybillFeeVo> waybillFeeVos;

    public CollectChargesBean() {
    }

    protected CollectChargesBean(Parcel in) {
        isSelect = in.readByte() != 0;
        waybillno = in.readString();
        amount = in.readDouble();
        currency = in.readString();
        waybillFeeVos = in.createTypedArrayList(WaybillFeeVo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeString(waybillno);
        dest.writeDouble(amount);
        dest.writeString(currency);
        dest.writeTypedList(waybillFeeVos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CollectChargesBean> CREATOR = new Creator<CollectChargesBean>() {
        @Override
        public CollectChargesBean createFromParcel(Parcel in) {
            return new CollectChargesBean(in);
        }

        @Override
        public CollectChargesBean[] newArray(int size) {
            return new CollectChargesBean[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<WaybillFeeVo> getWaybillFeeVos() {
        return waybillFeeVos;
    }

    public void setWaybillFeeVos(List<WaybillFeeVo> waybillFeeVos) {
        this.waybillFeeVos = waybillFeeVos;
    }
}

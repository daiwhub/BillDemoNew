package com.saas.payment.bean;

import android.os.Parcel;
import android.os.Parcelable;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-12  15:27
 *
 *         ***************************
 */
public class ItemPaymentMethodBean implements Parcelable {

    private String name;
    private int type;
    private boolean isChecked;

    public ItemPaymentMethodBean() {
    }

    public ItemPaymentMethodBean(String name, int type, boolean isChecked) {
        this.name = name;
        this.type = type;
        this.isChecked = isChecked;
    }

    protected ItemPaymentMethodBean(Parcel in) {
        name = in.readString();
        type = in.readInt();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(type);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemPaymentMethodBean> CREATOR = new Creator<ItemPaymentMethodBean>() {
        @Override
        public ItemPaymentMethodBean createFromParcel(Parcel in) {
            return new ItemPaymentMethodBean(in);
        }

        @Override
        public ItemPaymentMethodBean[] newArray(int size) {
            return new ItemPaymentMethodBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

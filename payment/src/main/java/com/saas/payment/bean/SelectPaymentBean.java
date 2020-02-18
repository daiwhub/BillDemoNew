package com.saas.payment.bean;

import android.os.Parcel;
import android.os.Parcelable;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-17  17:02
 *
 *         ***************************
 */
public class SelectPaymentBean implements Parcelable {

    private int type;
    private String imageUrl;

    public SelectPaymentBean() {
    }

    protected SelectPaymentBean(Parcel in) {
        type = in.readInt();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SelectPaymentBean> CREATOR = new Creator<SelectPaymentBean>() {
        @Override
        public SelectPaymentBean createFromParcel(Parcel in) {
            return new SelectPaymentBean(in);
        }

        @Override
        public SelectPaymentBean[] newArray(int size) {
            return new SelectPaymentBean[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SelectPaymentBean{" +
                "type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

package com.daiw.billdemonew.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-03  17:34
 *
 *         ***************************
 */
public class BillGroupBean implements Parcelable{

    private int size;//费用差异数量

    private String  wayBillNo;//运单号
    private String  taskType;//收派类型
    private String  feeType;//费用类型
    private Double  amountReality;//实收
    private Double  amountShould;//应收
    private String  payMethod;//支付方式
    private String  senderContract;//寄件人姓名
    private String  deliverContract;//收件人姓名

    private List<BillBean> childList;

    public BillGroupBean() {
    }

    protected BillGroupBean(Parcel in) {
        size = in.readInt();
        wayBillNo = in.readString();
        taskType = in.readString();
        feeType = in.readString();
        if (in.readByte() == 0) {
            amountReality = null;
        } else {
            amountReality = in.readDouble();
        }
        if (in.readByte() == 0) {
            amountShould = null;
        } else {
            amountShould = in.readDouble();
        }
        payMethod = in.readString();
        senderContract = in.readString();
        deliverContract = in.readString();
        childList = in.createTypedArrayList(BillBean.CREATOR);
    }

    public static final Creator<BillGroupBean> CREATOR = new Creator<BillGroupBean>() {
        @Override
        public BillGroupBean createFromParcel(Parcel in) {
            return new BillGroupBean(in);
        }

        @Override
        public BillGroupBean[] newArray(int size) {
            return new BillGroupBean[size];
        }
    };

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getWayBillNo() {
        return wayBillNo;
    }

    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Double getAmountReality() {
        return amountReality;
    }

    public void setAmountReality(Double amountReality) {
        this.amountReality = amountReality;
    }

    public Double getAmountShould() {
        return amountShould;
    }

    public void setAmountShould(Double amountShould) {
        this.amountShould = amountShould;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getSenderContract() {
        return senderContract;
    }

    public void setSenderContract(String senderContract) {
        this.senderContract = senderContract;
    }

    public String getDeliverContract() {
        return deliverContract;
    }

    public void setDeliverContract(String deliverContract) {
        this.deliverContract = deliverContract;
    }

    public List<BillBean> getChildList() {
        return childList;
    }

    public void setChildList(List<BillBean> childList) {
        this.childList = childList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size);
        dest.writeString(wayBillNo);
        dest.writeString(taskType);
        dest.writeString(feeType);
        if (amountReality == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amountReality);
        }
        if (amountShould == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amountShould);
        }
        dest.writeString(payMethod);
        dest.writeString(senderContract);
        dest.writeString(deliverContract);
        dest.writeTypedList(childList);
    }
}

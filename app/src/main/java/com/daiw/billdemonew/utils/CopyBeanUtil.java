package com.daiw.billdemonew.utils;

import android.text.TextUtils;

import com.daiw.billdemonew.bean.BillBean;
import com.daiw.billdemonew.bean.BillGroupBean;

import java.util.ArrayList;
import java.util.List;

/****************************
 * 类描述：
 *
 * @author: daiw
 * @time: 2020-02-09  21:19
 *
 *         ***************************
 */
public class CopyBeanUtil {

    public static BillGroupBean copyGroupBean(BillGroupBean groupBean){
        BillGroupBean billGroupBean = new BillGroupBean();

        billGroupBean.setSize(groupBean.getSize());
        if (!TextUtils.isEmpty(groupBean.getWayBillNo())) {
            billGroupBean.setWayBillNo(groupBean.getWayBillNo());
        }
        if (!TextUtils.isEmpty(groupBean.getTaskType())) {
            billGroupBean.setTaskType(groupBean.getTaskType());
        }
        if (!TextUtils.isEmpty(groupBean.getFeeType())) {
            billGroupBean.setFeeType(groupBean.getFeeType());
        }
        billGroupBean.setAmountReality(groupBean.getAmountReality());
        billGroupBean.setAmountShould(groupBean.getAmountShould());

        if (!TextUtils.isEmpty(groupBean.getPayMethod())) {
            billGroupBean.setPayMethod(groupBean.getPayMethod());
        }
        if (!TextUtils.isEmpty(groupBean.getSenderContract())) {
            billGroupBean.setSenderContract(groupBean.getSenderContract());
        }
        if (!TextUtils.isEmpty(groupBean.getDeliverContract())) {
            billGroupBean.setDeliverContract(groupBean.getDeliverContract());
        }

        List<BillBean> billBeanList = new ArrayList<>();
        for (BillBean bean : groupBean.getChildList()){
            if(bean != null){
                BillBean billBean = copyChildBean(bean);
                billBeanList.add(billBean);
            }
        }
        billGroupBean.setChildList(billBeanList);

        return billGroupBean;
    }

    public static BillBean copyChildBean(BillBean bean){
        BillBean billBean = new BillBean();

        if (!TextUtils.isEmpty(bean.getWayBillNo())) {
            billBean.setWayBillNo(bean.getWayBillNo());
        }
        if (!TextUtils.isEmpty(bean.getTaskType())) {
            billBean.setTaskType(bean.getTaskType());
        }
        if (!TextUtils.isEmpty(bean.getFeeType())) {
            billBean.setFeeType(bean.getFeeType());
        }
        billBean.setAmountReality(bean.getAmountReality());
        billBean.setAmountShould(bean.getAmountShould());

        if (!TextUtils.isEmpty(bean.getPayMethod())) {
            billBean.setPayMethod(bean.getPayMethod());
        }
        if (!TextUtils.isEmpty(bean.getSenderContract())) {
            billBean.setSenderContract(bean.getSenderContract());
        }
        if (!TextUtils.isEmpty(bean.getDeliverContract())) {
            billBean.setDeliverContract(bean.getDeliverContract());
        }

        return billBean;
    }

}

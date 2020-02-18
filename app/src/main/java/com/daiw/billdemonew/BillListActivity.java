package com.daiw.billdemonew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.daiw.billdemonew.adapter.MyAdapter;
import com.daiw.billdemonew.adapter.ScreenOutAdapter;
import com.daiw.billdemonew.bean.BillBean;
import com.daiw.billdemonew.bean.BillGroupBean;
import com.daiw.billdemonew.bean.GridItemBean;
import com.daiw.billdemonew.utils.CopyBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class BillListActivity extends AppCompatActivity implements SelectPopup.OnItemCallback {

    private ExpandableListView mListView;
    private RecyclerView mScreenOutRlv;
    private MyAdapter myAdapter;
    private ScreenOutAdapter mScreenOutAdapter;
    private List<BillGroupBean> mGroupDefaultList = new ArrayList<>();
    private List<BillGroupBean> mGroupList;
    private List<List<BillBean>> mChildDefaultList = new ArrayList<>();
    private List<List<BillBean>> mChildList;

    private TextView mSlectPaymethodTv;
    private TextView mSelectUncheckTv;
    private TextView mSelectFeeTypeTv;

    private List<GridItemBean> mPayMethodList = new ArrayList<>();
    private List<GridItemBean> mFeeTypeList = new ArrayList<>();

    private List<BillBean> mScreenOutList = new ArrayList<>();

    private SelectPopup mPopup;

    //-1默认，0支付方式筛选，1费用类型筛选
    private int selectFlag = -1;

    private int mPayMethodIndex = 0;
    private int mFeeTypeIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mGroupDefaultList = setGroupDefaultList(mGroupList);
        mChildDefaultList = setChildDefaultList(mChildList);

        mSlectPaymethodTv = findViewById(R.id.payment_method_tv);
        mSelectUncheckTv = findViewById(R.id.uncheck_tv);
        mSelectFeeTypeTv = findViewById(R.id.fee_type_tv);

        //初始化时的两级列表
        mListView = findViewById(R.id.expandable_list);
        //通过筛选条件后的单层级列表
        mScreenOutRlv = findViewById(R.id.screen_out_rlv);

        myAdapter = new MyAdapter(this, mGroupList, mChildList);

        mListView.setAdapter(myAdapter);

        mScreenOutAdapter = new ScreenOutAdapter(this, mScreenOutList);
        mScreenOutRlv.setLayoutManager(new LinearLayoutManager(this));
        mScreenOutRlv.setAdapter(mScreenOutAdapter);

        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPopup();
    }

    private List<BillGroupBean> setGroupDefaultList(List<BillGroupBean> temList) {
        List<BillGroupBean> billGroupBeans = new ArrayList<>();

        if (temList != null && temList.size() > 0) {
            for (BillGroupBean groupBean : temList) {
                if (groupBean != null) {
                    BillGroupBean billGroupBean = CopyBeanUtil.copyGroupBean(groupBean);
                    billGroupBeans.add(billGroupBean);
                }
            }
        }
        return billGroupBeans;
    }

    private List<List<BillBean>> setChildDefaultList(List<List<BillBean>> temList) {
        List<List<BillBean>> childList = new ArrayList<>();

        if (temList != null && temList.size() > 0) {
            for (List<BillBean> list : temList) {
                List<BillBean> listTem = new ArrayList<>();
                if (list != null && list.size() > 0) {
                    for (BillBean bean : list) {
                        BillBean billBean = CopyBeanUtil.copyChildBean(bean);
                        listTem.add(billBean);
                    }
                }
                if (listTem.size() > 0) {
                    childList.add(listTem);
                }
            }
        }
        return childList;
    }

    private void showPopup(View view, List<GridItemBean> itemBeanList, String title) {
        if (mPopup == null) {
            mPopup = new SelectPopup(this, view, title, itemBeanList, this);
        } else {
            mPopup = null;
            mPopup = new SelectPopup(this, view, title, itemBeanList, this);
        }
    }

    private void dismissPopup() {
        if (mPopup != null) {
            mPopup.dismiss();
        }
    }

    private void setListener() {
        mSlectPaymethodTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //支付方式筛选
                selectFlag = 0;
                showPopup(v, mPayMethodList, "Payment method");
            }
        });

        mSelectFeeTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //费用类型方式筛选
                selectFlag = 1;
                showPopup(v, mFeeTypeList, "Fee type");
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = mListView.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {
                        mListView.collapseGroup(i);
                    } else {
                        BillGroupBean billGroupBean = mGroupList.get(i);
                        if (null != billGroupBean && billGroupBean.getChildList() != null) {
                            int size = billGroupBean.getChildList().size();
                            if (size == 1) {
                                mListView.collapseGroup(i);
                            }
                        }
                    }
                }
            }
        });
    }

    private void initData() {
        mPayMethodList.add(new GridItemBean("All", true, "-1"));
        mPayMethodList.add(new GridItemBean("Cast", false, "0"));
        mPayMethodList.add(new GridItemBean("Online", false, "1"));
        mPayMethodList.add(new GridItemBean("POS", false, "2"));
        mPayMethodList.add(new GridItemBean("Bank Transfer", false, "3"));
        mPayMethodList.add(new GridItemBean("Cheque", false, "4"));

        mFeeTypeList.add(new GridItemBean("All", true, "-1"));
        mFeeTypeList.add(new GridItemBean("Freight Charges", false, "0"));
        mFeeTypeList.add(new GridItemBean("Tax", false, "99"));
        mFeeTypeList.add(new GridItemBean("COD", false, "5"));

        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();

        BillGroupBean billGroupBean = new BillGroupBean();

        List<BillBean> childList1 = new ArrayList<>();

        BillBean billBean2 = new BillBean();
        billBean2.setWayBillNo("SF10116299303818");
        billBean2.setTaskType("0");
        billBean2.setFeeType("99");
        billBean2.setAmountReality(2.00);//应收
        billBean2.setAmountShould(1.00);//实收
        billBean2.setPayMethod("0");
        billBean2.setSenderContract("Sender");
        billBean2.setDeliverContract("Deliver");

        BillBean billBean3 = new BillBean();
        billBean3.setWayBillNo("SF10116299303818");
        billBean3.setTaskType("0");
        billBean3.setFeeType("5");
        billBean3.setAmountReality(500.00);//应收
        billBean3.setAmountShould(500.00);//实收
        billBean3.setPayMethod("4");
        billBean3.setSenderContract("Sender");
        billBean3.setDeliverContract("Deliver");

        childList1.add(billBean2);
        childList1.add(billBean3);

        billGroupBean.setSize(2);
        billGroupBean.setWayBillNo("SF10116299303818");
        billGroupBean.setTaskType("0");
        billGroupBean.setFeeType("0");
        billGroupBean.setAmountReality(9.00);//应收
        billGroupBean.setAmountShould(8.00);//实收
        billGroupBean.setPayMethod("3");
        billGroupBean.setSenderContract("Sender");
        billGroupBean.setDeliverContract("Deliver");
        billGroupBean.setChildList(childList1);

        //第二条
        BillGroupBean billGroupBean2 = new BillGroupBean();

        List<BillBean> childList2 = new ArrayList<>();

        BillBean billBean5 = new BillBean();
        billBean5.setWayBillNo("SF10116299303810");
        billBean5.setTaskType("1");
        billBean5.setFeeType("99");
        billBean5.setAmountReality(2.00);//应收
        billBean5.setAmountShould(2.00);//实收
        billBean5.setPayMethod("1");
        billBean5.setSenderContract("Sender");
        billBean5.setDeliverContract("Deliver");

        BillBean billBean6 = new BillBean();
        billBean6.setWayBillNo("SF10116299303810");
        billBean6.setTaskType("1");
        billBean6.setFeeType("5");
        billBean6.setAmountReality(500.00);//应收
        billBean6.setAmountShould(500.00);//实收
        billBean6.setPayMethod("0");
        billBean6.setSenderContract("Sender");
        billBean6.setDeliverContract("Deliver");

        childList2.add(billBean5);
        childList2.add(billBean6);

        billGroupBean2.setSize(1);
        billGroupBean2.setWayBillNo("SF10116299303810");
        billGroupBean2.setTaskType("1");
        billGroupBean2.setFeeType("0");
        billGroupBean2.setAmountReality(9.00);//应收
        billGroupBean2.setAmountShould(8.00);//实收
        billGroupBean2.setPayMethod("0");
        billGroupBean2.setSenderContract("Sender");
        billGroupBean2.setDeliverContract("Deliver");
        billGroupBean2.setChildList(childList2);

        //第三条
        BillGroupBean billGroupBean3 = new BillGroupBean();

        List<BillBean> childList3 = new ArrayList<>();

        BillBean billBean8 = new BillBean();
        billBean8.setWayBillNo("SF10116299303812");
        billBean8.setTaskType("1");
        billBean8.setFeeType("99");
        billBean8.setAmountReality(2.00);//应收
        billBean8.setAmountShould(2.00);//实收
        billBean8.setPayMethod("2");
        billBean8.setSenderContract("Sender");
        billBean8.setDeliverContract("Deliver");

        BillBean billBean9 = new BillBean();
        billBean9.setWayBillNo("SF10116299303812");
        billBean9.setTaskType("1");
        billBean9.setFeeType("5");
        billBean9.setAmountReality(500.00);//应收
        billBean9.setAmountShould(500.00);//实收
        billBean9.setPayMethod("0");
        billBean9.setSenderContract("Sender");
        billBean9.setDeliverContract("Deliver");

        childList3.add(billBean8);
        childList3.add(billBean9);

        billGroupBean3.setSize(0);
        billGroupBean3.setWayBillNo("SF10116299303812");
        billGroupBean3.setTaskType("1");
        billGroupBean3.setFeeType("0");
        billGroupBean3.setAmountReality(9.00);//应收
        billGroupBean3.setAmountShould(9.00);//实收
        billGroupBean3.setPayMethod("0");
        billGroupBean3.setSenderContract("Sender");
        billGroupBean3.setDeliverContract("Deliver");
        billGroupBean3.setChildList(childList3);

        //第四条
        BillGroupBean billGroupBean4 = new BillGroupBean();

        List<BillBean> childList4 = new ArrayList<>();
        BillBean billBean10 = new BillBean();
        billBean10.setWayBillNo("SF10116299303814");
        billBean10.setTaskType("0");
        billBean10.setFeeType("99");
        billBean10.setAmountReality(9.00);//应收
        billBean10.setAmountShould(9.00);//实收
        billBean10.setPayMethod("0");
        billBean10.setSenderContract("Sender");
        billBean10.setDeliverContract("Deliver");

        childList4.add(billBean10);

        billGroupBean4.setSize(0);
        billGroupBean4.setWayBillNo("SF10116299303814");
        billGroupBean4.setTaskType("0");
        billGroupBean4.setFeeType("99");
        billGroupBean4.setAmountReality(9.00);//应收
        billGroupBean4.setAmountShould(9.00);//实收
        billGroupBean4.setPayMethod("0");
        billGroupBean4.setSenderContract("Sender");
        billGroupBean4.setDeliverContract("Deliver");
        billGroupBean4.setChildList(childList4);

        mGroupList.add(billGroupBean);
        mGroupList.add(billGroupBean2);
        mGroupList.add(billGroupBean3);
        mGroupList.add(billGroupBean4);
        mChildList.add(childList1);
        mChildList.add(childList2);
        mChildList.add(childList3);
        mChildList.add(childList4);
    }

    @Override
    public void onResult(int position) {
        dismissPopup();
        if (selectFlag == 0) {
            mPayMethodIndex = position;
            for (int i = 0; i < mPayMethodList.size(); i++) {
                if (i == position) {
                    mPayMethodList.get(i).setSelected(true);
                } else {
                    mPayMethodList.get(i).setSelected(false);
                }
            }
            if (0 == position) {
                mSlectPaymethodTv.setText("All Payment method");
            } else {
                mSlectPaymethodTv.setText(mPayMethodList.get(position).getText());
            }
//            if (mPayMethodIndex == 0 && mFeeTypeIndex == 0) {
//                showView(true);
//            } else {
//                setScreenOut();
//            }
            setScreenOutNew();
        } else {
            mFeeTypeIndex = position;
            for (int i = 0; i < mFeeTypeList.size(); i++) {
                if (i == position) {
                    mFeeTypeList.get(i).setSelected(true);
                } else {
                    mFeeTypeList.get(i).setSelected(false);
                }
            }
            if (0 == position) {
                mSelectFeeTypeTv.setText("All Fee type");
            } else {
                mSelectFeeTypeTv.setText(mFeeTypeList.get(position).getText());
            }
            setScreenOutNew();
//            if (mPayMethodIndex == 0 && mFeeTypeIndex == 0) {
//                showView(true);
//            } else {
//                setScreenOutNew();
//            }
        }
    }

    private void setScreenOutNew() {
        List<BillGroupBean> groupBeanList = new ArrayList<>();
        List<List<BillBean>> childBeanList = new ArrayList<>();

        String payMethod = "";
        if (mPayMethodIndex > 0) {
            //支付方式
            payMethod = mPayMethodList.get(mPayMethodIndex).getType();
        } else {
            payMethod = "";
        }
        String feeType = "";
        if (mFeeTypeIndex > 0) {
            //费用类型方式
            feeType = mFeeTypeList.get(mFeeTypeIndex).getType();
        } else {
            feeType = "";
        }

        List<BillGroupBean> groupDefaultList = setGroupDefaultList(mGroupDefaultList);

        boolean isDefault = false;

        for (BillGroupBean groupBean : groupDefaultList) {
            if (groupBean != null) {
                if (!TextUtils.isEmpty(payMethod) && !TextUtils.isEmpty(feeType)) {
                    //两种筛选条件
                    BillGroupBean billGroupBean = null;
                    if (payMethod.equals(groupBean.getPayMethod()) && feeType.equals(groupBean.getFeeType())) {
                        //一级菜单是否存在
                        billGroupBean = groupBean;
                    }
                    List<BillBean> beanListTem = new ArrayList<>();
                    if (groupBean.getChildList() != null && groupBean.getChildList().size() > 0) {
                        List<BillBean> beanList = groupBean.getChildList();
                        for (BillBean childBean : beanList) {
                            if (childBean != null) {
                                if (payMethod.equals(childBean.getPayMethod()) && feeType.equals(childBean.getFeeType())) {
                                    //二级菜单是否存在
                                    if (billGroupBean == null) {
                                        //如果没有添加一级菜单，需先添加一级菜单
                                        billGroupBean = getGroupBillBean(childBean);
                                    }
                                    beanListTem.add(childBean);
                                }
                            }
                        }
                    }
                    if (beanListTem.size() == 0) {
                        //没有二级菜单， 需要创建一个和一级菜单相同数据的二级菜单
                        if (billGroupBean != null) {
                            BillBean bean = getBillBean(billGroupBean);
                            beanListTem.add(bean);
                        }
                    } else {
                        if (billGroupBean == null && beanListTem.get(0) != null) {
                            billGroupBean = getGroupBillBean(beanListTem.get(0));
                        }
                    }
                    childBeanList.add(beanListTem);
                    if (billGroupBean != null) {
                        int size = 0;
                        if (Double.compare(billGroupBean.getAmountReality(), billGroupBean.getAmountShould()) != 0) {
                            size++;
                        }
                        if (beanListTem.size() > 0) {
                            for (BillBean childBean : beanListTem) {
                                if (Double.compare(childBean.getAmountReality(), childBean.getAmountShould()) != 0) {
                                    size++;
                                }
                            }
                            billGroupBean.setChildList(beanListTem);
                        }
                        billGroupBean.setSize(size);
                        groupBeanList.add(billGroupBean);
                    }
                    isDefault = false;
                } else if (!TextUtils.isEmpty(payMethod) && TextUtils.isEmpty(feeType)) {
                    //筛选支付方式
                    BillGroupBean billGroupBean = null;
                    if (payMethod.equals(groupBean.getPayMethod())) {
                        //一级菜单是否存在
                        billGroupBean = groupBean;
                    }
                    List<BillBean> beanListTem = new ArrayList<>();
                    if (groupBean.getChildList() != null && groupBean.getChildList().size() > 0) {
                        List<BillBean> beanList = groupBean.getChildList();
                        for (BillBean childBean : beanList) {
                            if (childBean != null) {
                                if (payMethod.equals(childBean.getPayMethod())) {
                                    //二级菜单是否存在
                                    if (billGroupBean == null) {
                                        //如果没有添加一级菜单，需先添加一级菜单
                                        billGroupBean = getGroupBillBean(childBean);
                                    }
                                    beanListTem.add(childBean);
                                }
                            }
                        }
                    }
                    if (beanListTem.size() == 0) {
                        //没有二级菜单， 需要创建一个和一级菜单相同数据的二级菜单
                        if (billGroupBean != null) {
                            BillBean bean = getBillBean(billGroupBean);
                            beanListTem.add(bean);
                        }
                    } else {
                        if (billGroupBean == null && beanListTem.get(0) != null) {
                            billGroupBean = getGroupBillBean(beanListTem.get(0));
                        }
                    }
                    childBeanList.add(beanListTem);
                    if (billGroupBean != null) {
                        int size = 0;
                        if (Double.compare(billGroupBean.getAmountReality(), billGroupBean.getAmountShould()) != 0) {
                            size++;
                        }
                        if (beanListTem.size() > 0) {
                            for (BillBean childBean : beanListTem) {
                                if (Double.compare(childBean.getAmountReality(), childBean.getAmountShould()) != 0) {
                                    size++;
                                }
                            }
                            billGroupBean.setChildList(beanListTem);
                        }
                        billGroupBean.setSize(size);
                        groupBeanList.add(billGroupBean);
                    }
                    isDefault = false;
                } else if (TextUtils.isEmpty(payMethod) && !TextUtils.isEmpty(feeType)) {
                    //筛选费用类型
                    BillGroupBean billGroupBean = null;
                    if (feeType.equals(groupBean.getFeeType())) {
                        //一级菜单是否存在
                        billGroupBean = groupBean;
                    }
                    List<BillBean> beanListTem = new ArrayList<>();
                    if (groupBean.getChildList() != null && groupBean.getChildList().size() > 0) {
                        List<BillBean> beanList = groupBean.getChildList();
                        for (BillBean childBean : beanList) {
                            if (childBean != null) {
                                if (feeType.equals(childBean.getFeeType())) {
                                    //二级菜单是否存在
                                    if (billGroupBean == null) {
                                        //如果没有添加一级菜单，需先添加一级菜单
                                        billGroupBean = getGroupBillBean(childBean);
                                    }
                                    beanListTem.add(childBean);
                                }
                            }
                        }
                    }
                    if (beanListTem.size() == 0) {
                        //没有二级菜单， 需要创建一个和一级菜单相同数据的二级菜单
                        if (billGroupBean != null) {
                            BillBean bean = getBillBean(billGroupBean);
                            beanListTem.add(bean);
                        }
                    } else {
                        if (billGroupBean == null && beanListTem.get(0) != null) {
                            billGroupBean = getGroupBillBean(beanListTem.get(0));
                        }
                    }
                    childBeanList.add(beanListTem);
                    if (billGroupBean != null) {
                        int size = 0;
                        if (Double.compare(billGroupBean.getAmountReality(), billGroupBean.getAmountShould()) != 0) {
                            size++;
                        }
                        if (beanListTem.size() > 0) {
                            for (BillBean childBean : beanListTem) {
                                if (Double.compare(childBean.getAmountReality(), childBean.getAmountShould()) != 0) {
                                    size++;
                                }
                            }
                            billGroupBean.setChildList(beanListTem);
                        }
                        billGroupBean.setSize(size);
                        groupBeanList.add(billGroupBean);
                    }
                    isDefault = false;
                } else {
                    //无筛选条件，默认
                    isDefault = true;

                }
            }
        }
        if(isDefault) {
            if (mGroupDefaultList.size() > 0 && mChildDefaultList.size() > 0) {
                setNewData(mGroupDefaultList, mChildDefaultList);
            }
        }else {
            if (groupBeanList.size() > 0 && childBeanList.size() > 0) {
                setNewData(groupBeanList, childBeanList);
            }
        }
        if (mGroupList != null && mGroupList.size() > 0 && mChildList != null && mChildList.size() > 0) {
            myAdapter.notifyDataSetChanged();
        }
    }

    private void setNewData(List<BillGroupBean> groupBeanList, List<List<BillBean>> childBeanList) {
        if (mGroupList != null) {
            if (mGroupList.size() > 0) {
                mGroupList.clear();
            }
            if (groupBeanList != null && groupBeanList.size() > 0) {
                mGroupList.addAll(groupBeanList);
            }
        }
        if (mChildList != null) {
            if (mChildList.size() > 0) {
                mChildList.clear();
            }
            if (childBeanList != null && childBeanList.size() > 0) {
                mChildList.addAll(childBeanList);
            }
        }
    }

    private void setScreenOut() {
        List<BillBean> resultList = new ArrayList<>();

        String payMethod = "";
        if (mPayMethodIndex > 0) {
            //支付方式
            payMethod = mPayMethodList.get(mPayMethodIndex).getType();
        } else {
            payMethod = "";
        }
        String feeType = "";
        if (mFeeTypeIndex > 0) {
            //费用类型方式
            feeType = mFeeTypeList.get(mFeeTypeIndex).getType();
        } else {
            feeType = "";
        }
        for (BillGroupBean groupBean : mGroupList) {
            if (groupBean != null && groupBean.getChildList() != null && groupBean.getChildList().size() > 0) {
                if (!TextUtils.isEmpty(payMethod) && !TextUtils.isEmpty(feeType)) {
                    if (payMethod.equals(groupBean.getPayMethod()) && feeType.equals(groupBean.getFeeType())) {
                        BillBean bean = getBillBean(groupBean);
                        resultList.add(bean);
                        if (groupBean.getChildList().size() == 1) {
                            continue;
                        }
                    }
                    for (BillBean billBean : groupBean.getChildList()) {
                        if (payMethod.equals(groupBean.getPayMethod()) && feeType.equals(billBean.getFeeType())) {
                            resultList.add(billBean);
                        }
                    }
                } else if (!TextUtils.isEmpty(payMethod) && TextUtils.isEmpty(feeType)) {
                    if (payMethod.equals(groupBean.getPayMethod())) {
                        BillBean bean = getBillBean(groupBean);
                        resultList.add(bean);
                        if (groupBean.getChildList().size() == 1) {
                            continue;
                        }
                    }
                    for (BillBean billBean : groupBean.getChildList()) {
                        if (payMethod.equals(billBean.getPayMethod())) {
                            resultList.add(billBean);
                        }
                    }
                } else if (TextUtils.isEmpty(payMethod) && !TextUtils.isEmpty(feeType)) {
                    if (feeType.equals(groupBean.getFeeType())) {
                        BillBean bean = getBillBean(groupBean);
                        resultList.add(bean);
                        if (groupBean.getChildList().size() == 1) {
                            continue;
                        }
                    }
                    for (BillBean billBean : groupBean.getChildList()) {
                        if (feeType.equals(billBean.getFeeType())) {
                            resultList.add(billBean);
                        }
                    }
                } else {
                    showView(true);
                }
            }
        }
        if (resultList.size() > 0) {
            if (mScreenOutList != null && mScreenOutList.size() > 0) {
                mScreenOutList.clear();
            }
            mScreenOutList.addAll(resultList);
            showView(false);
            mScreenOutAdapter.notifyDataSetChanged();
        }
    }

    private BillBean getBillBean(BillGroupBean groupBean) {
        BillBean bean = new BillBean();
        if (!TextUtils.isEmpty(groupBean.getWayBillNo())) {
            bean.setWayBillNo(groupBean.getWayBillNo());
        }
        if (!TextUtils.isEmpty(groupBean.getTaskType())) {
            bean.setTaskType(groupBean.getTaskType());
        }
        if (!TextUtils.isEmpty(groupBean.getFeeType())) {
            bean.setFeeType(groupBean.getFeeType());
        }
        bean.setAmountReality(groupBean.getAmountReality());
        bean.setAmountShould(groupBean.getAmountShould());

        if (!TextUtils.isEmpty(groupBean.getPayMethod())) {
            bean.setPayMethod(groupBean.getPayMethod());
        }
        if (!TextUtils.isEmpty(groupBean.getSenderContract())) {
            bean.setSenderContract(groupBean.getSenderContract());
        }
        if (!TextUtils.isEmpty(groupBean.getDeliverContract())) {
            bean.setDeliverContract(groupBean.getDeliverContract());
        }
        return bean;
    }

    private BillGroupBean getGroupBillBean(BillBean childBean) {
        BillGroupBean groupBean = new BillGroupBean();
        if (!TextUtils.isEmpty(childBean.getWayBillNo())) {
            groupBean.setWayBillNo(childBean.getWayBillNo());
        }
        if (!TextUtils.isEmpty(childBean.getTaskType())) {
            groupBean.setTaskType(childBean.getTaskType());
        }
        if (!TextUtils.isEmpty(childBean.getFeeType())) {
            groupBean.setFeeType(childBean.getFeeType());
        }
        groupBean.setAmountReality(childBean.getAmountReality());
        groupBean.setAmountShould(childBean.getAmountShould());

        if (!TextUtils.isEmpty(childBean.getPayMethod())) {
            groupBean.setPayMethod(childBean.getPayMethod());
        }
        if (!TextUtils.isEmpty(childBean.getSenderContract())) {
            groupBean.setSenderContract(childBean.getSenderContract());
        }
        if (!TextUtils.isEmpty(childBean.getDeliverContract())) {
            groupBean.setDeliverContract(childBean.getDeliverContract());
        }
        return groupBean;
    }

    private void showView(boolean isDefault) {
        if (isDefault) {
            mListView.setVisibility(View.VISIBLE);
            mScreenOutRlv.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.GONE);
            mScreenOutRlv.setVisibility(View.VISIBLE);
        }
    }
}

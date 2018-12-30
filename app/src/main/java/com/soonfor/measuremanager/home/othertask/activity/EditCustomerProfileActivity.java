package com.soonfor.measuremanager.home.othertask.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.home.othertask.adapter.editcustomerprofile.CustomerProfileAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerProfileBean;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerSelfProfileBean;
import com.soonfor.measuremanager.home.othertask.presenter.editcustomerprofile.EditCustomerProfilePresenter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
import com.soonfor.measuremanager.view.dialog.AlertDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ljc on 2018/1/9.
 */

public class EditCustomerProfileActivity extends BaseActivity<EditCustomerProfilePresenter> {

    @BindView(R.id.tag1)
    TagFlowLayout tag1;
    @BindView(R.id.tag2)
    TagFlowLayout tag2;
    @BindView(R.id.tag3)
    TagFlowLayout tag3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.zidingyiHuaxiang)
    TextView zidingyiHuaxiang;
    String customHuaxiang = "";
    @BindView(R.id.txtTmp)
    TextView txtTmp;

    @Override
    protected int attachLayoutRes() {
        return R.layout.acitivty_edit_customer_profile;
    }


    private String customerId;

    private CustomerProfileAdapter adapter;


    @Override
    protected void initViews() {
        selectedBeans = new ArrayList<>();
        customerId = getIntent().getStringExtra("customerId");
        tv_title.setText("编辑 " + getIntent().getStringExtra("customerName") + " 的客户画像");
        adapter = new CustomerProfileAdapter(this, null);
        adapter.setListener(new CustomerProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<CustomerProfileBean.DataBean.ListBean.ItemsBean> beans = adapter.getBeans();
                CustomerProfileBean.DataBean.ListBean.ItemsBean removeBean = beans.get(position);
                beans.remove(position);
                adapter.notifyDataSetChanged(beans);


                for (int i = 0; i < bean1.size(); i++) {
                    if (removeBean.getCode().equals(bean1.get(i).getCode())) {
                        Set selectList = tag1.getSelectedList();
                        selectList.remove(i);
                        tagAdapter1.setSelectedList(selectList);
                    }
                }

                for (int i = 0; i < bean2.size(); i++) {
                    if (removeBean.getCode().equals(bean2.get(i).getCode())) {
                        Set selectList = tag2.getSelectedList();
                        selectList.remove(i);
                        tagAdapter2.setSelectedList(selectList);
                    }
                }


                for (int i = 0; i < bean3.size(); i++) {
                    if (removeBean.getCode().equals(bean3.get(i).getCode())) {
                        Set selectList = tag3.getSelectedList();
                        selectList.remove(i);
                        tagAdapter3.setSelectedList(selectList);
                    }
                }
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        txtTmp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String[] tmp = s.toString().split(" ");
                    if (tmp.length > 0) {
                        if (selectedBeans == null) {
                            selectedBeans = new ArrayList<>();
                        }
                        int i = selectedBeans.size();
                        for (String sss : tmp) {
                            CustomerProfileBean.DataBean.ListBean.ItemsBean bean = new CustomerProfileBean.DataBean.ListBean.ItemsBean();
                            bean.setCode("0");
                            bean.setName(sss);
                            bean.setIndex(i);
                            bean.setItems(null);
                            selectedBeans.add(bean);
                            i++;
                        }
                        adapter.notifyDataSetChanged(selectedBeans);
                    }
                } catch (Exception e) {
                    txtTmp.setText("");
                    Toast.makeText(EditCustomerProfileActivity.this, "输入的内容格式不正确, 请重新输入", Toast.LENGTH_SHORT);
                    zidingyiHuaxiang.callOnClick();
                }

            }
        });

        bean4 = new ArrayList<>();
    }

    @Override
    protected void initPresenter() {
        presenter = new EditCustomerProfilePresenter(this, customerId);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }


    @OnClick({R.id.tv_save, R.id.zidingyiHuaxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                JSONArray ja = new JSONArray();
                List<CustomerProfileBean.DataBean.ListBean.ItemsBean> items = adapter.getBeans();
                if (items != null) {
                    if (items.size() == 0) {
                        Toast.makeText(this, "请选择客户画像", Toast.LENGTH_SHORT).show();
                    } else if (items.size() > 0) {
                        for (int i = 0; i < items.size(); i++) {
                            JSONObject jo = new JSONObject();
                            try {
                                jo.put("customerId", customerId);
                                jo.put("portraitCode", items.get(i).getCode());
                                jo.put("portraitName", items.get(i).getName());
                                jo.put("index", i);

                                ja.put(jo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        presenter.saveProfile(ja.toString());
                    }
                } else {
                    Toast.makeText(this, "请选择客户画像", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.zidingyiHuaxiang:
                AlertDialog.build(EditCustomerProfileActivity.this, customHuaxiang, txtTmp).show();
                break;
        }
    }

    public void notifyData(CustomerSelfProfileBean selfProfileBean) {
        if(selfProfileBean==null){
            finish();
            UpdateTaskResultActivity.FinishActivity();
            return;
        }
        if (selfProfileBean.getData().getList() != null && selfProfileBean.getData().getList().size() != 0 && bean1 != null
                && bean2 != null && bean3 != null) {
            for (CustomerSelfProfileBean.DataBean.ListBean listBean : selfProfileBean.getData().getList()) {
                Set selectList1 = tag1.getSelectedList();
                for (int i = 0; i < bean1.size(); i++) {
                    if (bean1.get(i).getCode().equals(listBean.getPortraitCode())) {
                        selectList1.add(i);
                    }
                }
                tagAdapter1.setSelectedList(selectList1);

                Set selectList2 = tag2.getSelectedList();
                for (int i = 0; i < bean2.size(); i++) {
                    if (bean2.get(i).getCode().equals(listBean.getPortraitCode())) {
                        selectList2.add(i);
                    }
                }
                tagAdapter2.setSelectedList(selectList2);


                Set selectList3 = tag3.getSelectedList();
                for (int i = 0; i < bean3.size(); i++) {
                    if (bean3.get(i).getCode().equals(listBean.getPortraitCode())) {
                        selectList3.add(i);
                    }
                }
                tagAdapter3.setSelectedList(selectList3);
            }

            //单独处理那些自定义的
            if(selectedBeans==null){
                selectedBeans = new ArrayList<>();
            }
            for (CustomerSelfProfileBean.DataBean.ListBean listBean : selfProfileBean.getData().getList()) {
                if(listBean.getPortraitCode().equals("0")){
                    CustomerProfileBean.DataBean.ListBean.ItemsBean bean = new CustomerProfileBean.DataBean.ListBean.ItemsBean();
                    bean.setCode(listBean.getPortraitCode());
                    bean.setName(listBean.getPortraitName());
                    bean.setIndex(listBean.getIndex());
                    bean.setItems(null);
                    selectedBeans.add(bean);
                }
            }

            getProfile();

            adapter.notifyDataSetChanged(selectedBeans);
        }
    }

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> selectedBeans;

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> getProfile() {
        if (bean1 != null && bean2 != null && bean3 != null) {
            Set<Integer> select1 = tag1.getSelectedList();
            Set<Integer> select2 = tag2.getSelectedList();
            Set<Integer> select3 = tag3.getSelectedList();

            selectedBeans = new ArrayList<>();

            if (select1 != null && select1.size() != 0) {
                for (Integer i : select1) {
                    selectedBeans.add(bean1.get(i));
                }
            }

            if (select2 != null && select2.size() != 0) {
                for (Integer i : select2) {
                    selectedBeans.add(bean2.get(i));
                }
            }

            if (select3 != null && select3.size() != 0) {
                for (Integer i : select3) {
                    selectedBeans.add(bean3.get(i));
                }
            }

            return selectedBeans;
        }
        return null;
    }


    private TagAdapter tagAdapter1, tagAdapter2, tagAdapter3;

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean1, bean2, bean3, bean4;

    public void showProfiles(final List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean1,
                             List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean2,
                             List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean3) {
        this.bean1 = bean1;
        this.bean2 = bean2;
        this.bean3 = bean3;

        tag1.setMaxSelectCount(-1);
        tag2.setMaxSelectCount(-1);
        tag3.setMaxSelectCount(-1);

        final LayoutInflater mLayoutInflater = LayoutInflater.from(this);

        tagAdapter1 = new TagAdapter<CustomerProfileBean.DataBean.ListBean.ItemsBean>(bean1) {
            @Override
            public View getView(FlowLayout parent, int position, CustomerProfileBean.DataBean.ListBean.ItemsBean o) {
                View view = mLayoutInflater.inflate(R.layout.item_tag_text, parent, false);
                TextView tv = view.findViewById(R.id.tv);
                tv.setText(o.getName());
                tv.setBackgroundResource(R.drawable.bg_tag_orange);
                tv.setTextColor(getResources().getColorStateList(R.color.tag_text_selector_orange));
                return tv;
            }
        };
        tag1.setAdapter(tagAdapter1);

        tag1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                getProfile();
                adapter.notifyDataSetChanged(selectedBeans);
            }
        });

        tag2.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                getProfile();
                adapter.notifyDataSetChanged(selectedBeans);
            }
        });

        tag3.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                getProfile();
                adapter.notifyDataSetChanged(selectedBeans);
            }
        });

        tagAdapter2 = new TagAdapter<CustomerProfileBean.DataBean.ListBean.ItemsBean>(bean2) {
            @Override
            public View getView(FlowLayout parent, int position, CustomerProfileBean.DataBean.ListBean.ItemsBean o) {
                View view = mLayoutInflater.inflate(R.layout.item_tag_text, parent, false);
                TextView tv = view.findViewById(R.id.tv);
                tv.setText(o.getName());
                tv.setBackgroundResource(R.drawable.bg_tag_green);
                tv.setTextColor(getResources().getColorStateList(R.color.tag_text_selector_green));
                return tv;
            }
        };
        tag2.setAdapter(tagAdapter2);

        tagAdapter3 = new TagAdapter<CustomerProfileBean.DataBean.ListBean.ItemsBean>(bean3) {
            @Override
            public View getView(FlowLayout parent, int position, CustomerProfileBean.DataBean.ListBean.ItemsBean o) {
                View view = mLayoutInflater.inflate(R.layout.item_tag_text, parent, false);
                TextView tv = view.findViewById(R.id.tv);
                tv.setText(o.getName());
                tv.setBackgroundResource(R.drawable.bg_tag_blue);
                tv.setTextColor(getResources().getColorStateList(R.color.tag_text_selector_blue));
                return tv;
            }
        };
        tag3.setAdapter(tagAdapter3);
    }

    public static void start(String customerId, String customerName, Context context) {
        Intent intent = new Intent(context, EditCustomerProfileActivity.class);
        intent.putExtra("customerId", customerId);
        intent.putExtra("customerName", customerName);
        context.startActivity(intent);
    }


}

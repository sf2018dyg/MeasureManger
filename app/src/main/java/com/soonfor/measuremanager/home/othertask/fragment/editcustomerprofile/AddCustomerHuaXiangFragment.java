package com.soonfor.measuremanager.home.othertask.fragment.editcustomerprofile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.home.othertask.activity.EditCustomerProfileActivity;
import com.soonfor.measuremanager.home.othertask.adapter.editcustomerprofile.CustomerProfileAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerProfileBean;
import com.soonfor.measuremanager.home.othertask.model.bean.CustomerSelfProfileBean;
import com.soonfor.measuremanager.home.othertask.presenter.editcustomerprofile.EditCustomerProfilePresenter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/8/29 0029 18:48
 * 邮箱：suibozhu@139.com
 * 类用途：
 */
public class AddCustomerHuaXiangFragment extends BaseFragment<EditCustomerProfilePresenter> {

    Unbinder unbinder;
    @BindView(R.id.tag1)
    TagFlowLayout tag1;
    @BindView(R.id.tag2)
    TagFlowLayout tag2;
    @BindView(R.id.tag3)
    TagFlowLayout tag3;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private String customerId;
    private CustomerProfileAdapter adapter;
    final Handler saveHandle = new Handler();
    Runnable runnable = null;
    List<CustomerProfileBean.DataBean.ListBean.ItemsBean> tmpBeans;

    public static AddCustomerHuaXiangFragment newInstance(String customerId) {
        Bundle args = new Bundle();
        args.putString("customerId", customerId);
        AddCustomerHuaXiangFragment fragment = new AddCustomerHuaXiangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView
            (LayoutInflater inflater, @Nullable ViewGroup container,
             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate
                (R.layout.fragment_check_in_huaxiang, container,
                        false);

        unbinder = ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void initViews() {
        customerId = getArguments().getString("customerId");
        adapter = new CustomerProfileAdapter(getContext(), null);
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

        FullyGridLayoutManager manager = new FullyGridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray ja = new JSONArray();
                List<CustomerProfileBean.DataBean.ListBean.ItemsBean> items = adapter.getBeans();
                if (items != null) {
                    if (items.size() == 0) {
                        Toast.makeText(getContext(), "请选择客户画像", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "请选择客户画像", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //从草稿中恢复
        try {
            tmpBeans = Hawk.get("AddCustomerCustomerProfileBean", null);
            if (tmpBeans != null) {
                adapter.notifyDataSetChanged(tmpBeans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Hawk.delete("AddCustomerCustomerProfileBean");
        }

        //建立一个线程 每隔一会 就保存一下变量
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    tmpBeans = adapter.getBeans();
                    Hawk.put("AddCustomerCustomerProfileBean", tmpBeans);
                    saveHandle.postDelayed(this, 5000);
                } catch (Exception e) {

                }
            }
        };
        saveHandle.post(runnable);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    protected void initPresenter() {
        presenter = new EditCustomerProfilePresenter(this, customerId);
    }


    public void notifyData(CustomerSelfProfileBean selfProfileBean) {
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

            getProfile();
            adapter.notifyDataSetChanged(selectedBeans);
        }
    }

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> selectedBeans;

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> getProfile() {
        selectedBeans = new ArrayList<>();
        if (bean1 != null && bean2 != null && bean3 != null) {
            Set<Integer> select1 = tag1.getSelectedList();
            Set<Integer> select2 = tag2.getSelectedList();
            Set<Integer> select3 = tag3.getSelectedList();

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

    private List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean1, bean2, bean3;

    public void showProfiles(final List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean1,
                             List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean2,
                             List<CustomerProfileBean.DataBean.ListBean.ItemsBean> bean3) {
        this.bean1 = bean1;
        this.bean2 = bean2;
        this.bean3 = bean3;

        tag1.setMaxSelectCount(-1);
        tag2.setMaxSelectCount(-1);
        tag3.setMaxSelectCount(-1);

        final LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveHandle.removeCallbacks(runnable);
        unbinder.unbind();
    }
}

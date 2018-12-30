package com.soonfor.measuremanager.upload.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.me.adapter.mycase.MycaseListAdapter;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/4/4 0004 11:34
 * 邮箱：suibozhu@139.com
 * 类用途：选择方案
 */
public class SelectCaseActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Activity mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    GridLayoutManager manager;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    List<DesignCaseBean> cbList;
    MycaseListAdapter cbAdapter;
    @BindView(R.id.confirmSelect)
    RelativeLayout confirmSelect;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_case;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = SelectCaseActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        cbList = new ArrayList<>();
        manager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(manager);
        cbAdapter = new MycaseListAdapter(mContext, cbList);
        recyclerView.setAdapter(cbAdapter);
        isHaveData(0);

        edtSearch.addTextChangedListener(textWatcher);
    }

    private void isHaveData(int type) {
        switch (type) {
            case 0:
                llftxterror.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
            case 1:
                llftxterror.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //me_evaluate_ranking.notifyDataSetChanged(searchByKey(beans, s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //本地遍历
    private List<DesignCaseBean> searchByKey(List<DesignCaseBean> comparBean, String key) {
        List<DesignCaseBean> tmp = new ArrayList<>();
        for (DesignCaseBean bean : comparBean) {
            String ckey = bean.getTitle() + "@" + bean.getAreaName() + "@" + bean.getComments();
            if (ckey.contains(key)) {
                tmp.add(bean);
            }
        }
        if (tmp.size() > 0) {
            isHaveData(1);
        } else {
            isHaveData(0);
        }
        return tmp;
    }

    @OnClick({R.id.confirmSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirmSelect:
                int posi = cbAdapter.getCurPosi();
                if (posi != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("CaseBean", cbList.get(posi));
                    setResult(7777, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mLoadingDialog.show();
        //RepRequest.pageHouseTemplate(mContext, this, "1", "50");

//        DesignCaseBean cb = new DesignCaseBean("","","2018-1-1","","我是标题","我是建筑","","","1","1","1");
//        cbList.add(cb);
//        cbList.add(cb);
//        cbList.add(cb);
//        cbList.add(cb);
//        cbList.add(cb);
        cbAdapter.notifyDataSetChanged(cbList);
        isHaveData(1);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        mLoadingDialog.dismiss();
        final Gson gosn = new Gson();
        switch (requestCode) {
            case Request.PAGE_HOUSE_TEMPLATE:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {

                        } catch (Exception e) {
                            showMsg(e.toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        showMsg("请求失败代码:" + requestCode);
        closeLoadingDialog();
    }
}
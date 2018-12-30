package com.soonfor.measuremanager.home.liangchi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.PageTurnBean;
import com.soonfor.measuremanager.home.liangchi.adapter.ApartmentLayoutListAdapter;
import com.soonfor.measuremanager.home.liangchi.model.bean.ApartmentLayoutBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/9 8:20
 * 邮箱：suibozhu@139.com
 * <p>
 * 选择户型
 */

public class ApartmentLayoutActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    GridLayoutManager manager;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    ApartmentLayoutListAdapter adapter;
    List<ApartmentLayoutBean> beans;
    @BindView(R.id.confirmSelect)
    RelativeLayout confirmSelect;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;

    PageTurnBean pageTurnBean;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fuchi_apartment_layout;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = ApartmentLayoutActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);
        manager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(manager);

        beans = new ArrayList<>();
        adapter = new ApartmentLayoutListAdapter(mContext, ApartmentLayoutActivity.this, beans);
        recyclerView.setAdapter(adapter);
        isHaveData(0);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Request.pageHouseTemplate(mContext, ApartmentLayoutActivity.this, "1", "10", edtSearch.getText().toString());
                }
                return false;
            }
        });
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

    @Override
    public void RefreshData(boolean isRefresh) {
        super.RefreshData(isRefresh);
        Request.pageHouseTemplate(mContext, this, String.valueOf(pageTurnBean.getPageNo()), "10", edtSearch.getText().toString());
    }

    @Override
    protected void loadmoredata() {
        if (pageTurnBean != null && pageTurnBean.getPageCount() > pageTurnBean.getPageNo()) {
            Request.pageHouseTemplate(mContext, this, String.valueOf(pageTurnBean.getPageNo() + 1), "10", edtSearch.getText().toString());
        } else {
            finishRefresh();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.showToast(mActivity, "已是最后一页了！");
                }
            }, 1000);*/
        }
    }

/*    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            me_evaluate_ranking.notifyDataSetChanged(searchByKey(beans, s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/

   /* //本地遍历
    private List<ApartmentLayoutBean> searchByKey(List<ApartmentLayoutBean> comparBean, String key) {
        List<ApartmentLayoutBean> tmp = new ArrayList<>();
        for (ApartmentLayoutBean bean : comparBean) {
            String ckey = bean.getFname() + "@" + bean.getFspecname() + "@" + bean.getFaddress();
            if (ckey.contains(key)) {
                tmp.add(bean);
            }
        }
        if (tmp.size() > 0) {
            isHaveData(1);
        } else {
            isHaveData(0);
        }

        //清空选中的状态
        if(me_evaluate_ranking!=null){
             me_evaluate_ranking.setCurPosi(-1);
        }

        return tmp;
    }*/

    @OnClick({R.id.confirmSelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirmSelect:
                int posi = adapter.getCurPosi();
                if (posi != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("ApartmentLayoutBean", beans.get(posi));
                    setResult(7777, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoadingDialog.show();
        Request.pageHouseTemplate(mContext, this, "1", "10", edtSearch.getText().toString());
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            setResult(7778, intent);
            finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(7778, intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        mLoadingDialog.dismiss();
        finishRefresh();
        final Gson gosn = new Gson();
        switch (requestCode) {
            case Request.PAGE_HOUSE_TEMPLATE:
                JsonUtils.analysisJsonHead(object.toString(), new JsonUtils.OperateData() {
                    @Override
                    public void doingInFail(String msg) {
                        showNoDataHint(msg);
                    }

                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject jo = new JSONObject(data);
                            pageTurnBean = JsonUtils.getPageBean(jo.getString("pageTurn"));
                            JSONArray ja = new JSONArray(jo.getString("list"));

                            if (pageTurnBean.getPageNo() > 1) {

                            } else {
                                beans = new ArrayList<>();
                            }

                            for (int i = 0; i < ja.length(); i++) {
                                ApartmentLayoutBean ab = gosn.fromJson(ja.get(i).toString(), new TypeToken<ApartmentLayoutBean>() {
                                }.getType());
                                ab.setFspecname(ab.getFspecname().replace("null", " "));
                                if (ab != null) {
                                    beans.add(ab);
                                }
                            }
                            adapter.notifyDataSetChanged(beans);
                            if (beans.size() > 0) {
                                isHaveData(1);
                            } else {
                                isHaveData(0);
                            }
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
        finishRefresh();
        mLoadingDialog.dismiss();
    }
}

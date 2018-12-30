package com.soonfor.measuremanager.afflatus.activity;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.TintplateBean;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/22 11:05
 * 邮箱：suibozhu@139.com
 * 色板详情
 */
public class TintplateDetailActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    @BindView(R.id.imgpath)
    ImageView imgpath;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.txtdesc)
    TextView txtdesc;
    @BindView(R.id.txtDescHtml)
    TextView txtDescHtml;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_tintplate_detail;
    }

    @Override
    protected void initViews() {
        mContext = TintplateDetailActivity.this;
        TintplateBean tb = (TintplateBean)getIntent().getSerializableExtra("TintplateBean");
        if (tb != null) {
            ImageUtil.loadCaselibImage(mContext, tb.getImgUrl(),imgpath);
            txttitle.setText(CommonUtils.formatStr(tb.getTitle()));
            txtdesc.setText(CommonUtils.formatStr(tb.getRemark()));
            txtDescHtml.setText(Html.fromHtml(CommonUtils.formatStr(tb.getDetails())));
        }
    }

    @OnClick({R.id.imgBack, R.id.imgShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgShare:
                MyToast.showToast(mContext, "分享..");
                break;
        }
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }
}

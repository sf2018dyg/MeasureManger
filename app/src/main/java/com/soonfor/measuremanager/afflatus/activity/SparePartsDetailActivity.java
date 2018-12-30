package com.soonfor.measuremanager.afflatus.activity;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.SparePartsBean;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.JsonUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jesse.nativelogger.NLogger;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/22 11:05
 * 邮箱：suibozhu@139.com
 * 配件详情
 */
public class SparePartsDetailActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

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
    SparePartsBean sb;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_spare_parts_detail;
    }

    @Override
    protected void initViews() {
        mContext = SparePartsDetailActivity.this;
        sb = (SparePartsBean)getIntent().getSerializableExtra("SparePartsBean");
        if (sb != null) {
            ImageUtil.loadCaselibImage(mContext, sb.getImgUrl(),imgpath);
            txttitle.setText(CommonUtils.formatStr(sb.getName()));
            txtdesc.setText("分类: " + CommonUtils.formatStr(sb.getSortname()) + "\n" + "品牌: " + CommonUtils.formatStr(sb.getBrandname()) + "\n" + "规格: " + CommonUtils.formatStr(sb.getSpecification()));
            txtDescHtml.setText(Html.fromHtml(CommonUtils.formatStr(sb.getDetails())));
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
        Request.getInfoPartDetail(mContext,sb.getId(),this);
    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        Gson gson = new Gson();
        switch (requestCode){
            case Request.GETINFOPARTDETAIL:
                JsonUtils.analysisJsonHead( object.toString(), new JsonUtils.OperateData() {                    @Override                    public void doingInFail(String msg) {                        showNoDataHint(msg);                    }
                    @Override
                    public void doingInSuccess(String data) {
                        try {
                            JSONObject o = new JSONObject(data);
                            ImageUtil.loadCaselibImage(mContext, o.getString("surfacePlotUrl"), imgpath);
                            txtdesc.setText("分类: " + CommonUtils.formatStr(o.getString("sortname")) + "\n" + "品牌: " + CommonUtils.formatStr(o.getString("brandname")) + "\n" + "规格: " + CommonUtils.formatStr(o.getString("specification")));
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
    }
}

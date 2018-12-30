package com.soonfor.measuremanager.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.bean.PrizeRuleBean;
import com.soonfor.measuremanager.me.presenter.prizerule.IPrizeRuleView;
import com.soonfor.measuremanager.me.presenter.prizerule.PrizeRulePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17 0017.
 * 抽奖专区
 */

public class PrizeRuleActivity extends BaseActivity<PrizeRulePresenter> implements IPrizeRuleView {

    @BindView(R.id.img_pic)
    ImageView imgPic;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_lottery_name)
    TextView tvLotteryName;
    @BindView(R.id.tv_lottery_frequency)
    TextView tvLotteryFrequency;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.bt_lottery)
    Button btLottery;
    @BindView(R.id.prize_rule_show)
    RelativeLayout prizeRuleShow;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_prize_rule;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {}

    /**
     * 初始化presenter
     */
    @Override
    protected void initPresenter() {
        presenter = new PrizeRulePresenter(this, getIntent().getStringExtra("prizeId"));
    }

    /**
     * 更新视图控件
     *
     * @param isRefresh
     */
    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void setData(PrizeRuleBean bean) {
        prizeRuleShow.setVisibility(View.VISIBLE);
        imageLoading(bean.getData().getThumbnail(), imgPic, R.mipmap.icn_anli);
        tvLotteryName.setText(bean.getData().getName());
        tvLotteryFrequency.setText(bean.getData().getDescription());
        tvPoints.setText("-" + bean.getData().getWorkPoints() + "工分");
//        tvRule.setText(Html.fromHtml(bean.getData().getRule().toString()) + "");
    }

    @OnClick(R.id.bt_lottery)
    public void onViewClicked() {
        presenter.getKey();
    }
}

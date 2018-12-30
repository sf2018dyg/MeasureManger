package com.soonfor.measuremanager.me.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.SoonforApplication;
import com.soonfor.measuremanager.http.api.UserInfo;
import com.soonfor.measuremanager.me.activity.MyCardActivity;
import com.soonfor.measuremanager.me.activity.MyGrowthActivity;
import com.soonfor.measuremanager.me.activity.MyPerformanceActivity;
import com.soonfor.measuremanager.me.activity.PersonalDataActivity;
import com.soonfor.measuremanager.me.activity.SettingActivity;
import com.soonfor.measuremanager.me.activity.WorkPointsCenterActivity;
import com.soonfor.measuremanager.me.bean.MeMineBean;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.RoundImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 作者：DC-DingYG on 2018-11-27 16:00
 * 邮箱：dingyg012655@126.com
 * 我的个人信息
 */
public class MeInfoView extends LinearLayout {

    private Activity mContext;
    @BindView(R.id.imgfSet)
    ImageView imgfSet;//设置
    @BindView(R.id.imgfMingPian)
    ImageView imgfMingPian;//名片
    @BindView(R.id.img_avatar)
    RoundImageView imgAvatar;//头像
    @BindView(R.id.tv_name)
    TextView tvName;//客户名称
    @BindView(R.id.tvfCity)
    TextView tvfCity;//城市区域
    @BindView(R.id.tv_storeName)
    TextView tvStoreName;//门店
    @BindView(R.id.mRbClass)
    MaterialRatingBar mRbClass;//等级(代表几星的星号)
    @BindView(R.id.tvfClassName)
    TextView tvfClassName;//等级称谓

    @BindView(R.id.tv_work_points)
    TextView tvWorkPoints;
    @BindView(R.id.tv_growth)
    TextView tvGrowth;
    @BindView(R.id.tv_performance)
    TextView tvPerformance;

    private MeMineBean mmBean;

    public MeInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_mefragment_mineinfo, this);
        ButterKnife.bind(this, view);
    }


    public void initMineInfoVeiw(Activity mContext, MeMineBean bean) {
        this.mContext = mContext;
        mmBean = bean;
        if(mmBean!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String url = "http://" + Hawk.get(SoonforApplication.ServerAdr_fj) + UserInfo.DOWNLOAD_FILE + bean.getAvatar();
                                Picasso.with(mContext).load(url)
                                        .placeholder(R.drawable.avatar_default)
                                        .error(R.drawable.avatar_default)
                                        .into(imgAvatar);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tvName.setText(bean.getName());
                            //if(bean.get)
                            tvStoreName.setText(bean.getStoreName());
                            mRbClass.setRating((float) bean.getLevel());
                            if (!bean.getPost().equals("")) {
                                tvfClassName.setText("[" + bean.getPost() + "]");
                            }
                            tvWorkPoints.setText(bean.getPoints() + "");
                            tvGrowth.setText(bean.getGrowthValue() + "");
                            tvPerformance.setText(mContext.getResources().getString(R.string.yuan) + bean.getPerformance());

                        }
                    });
                }
            }).start();
        }
    }

    @OnClick({R.id.imgfSet, R.id.imgfMingPian, R.id.llfBaseInfo, R.id.linear_work_points, R.id.linear_growth, R.id.linear_my_performance})
    public void onViewClicked(View view) {
        if (mContext != null) {
            switch (view.getId()) {
                case R.id.imgfSet://设置
                    mContext.startActivity(new Intent(getContext(), SettingActivity.class));
                    break;
                case R.id.imgfMingPian://名片
                    if(mmBean!=null) {
                        Bundle bundle0 = new Bundle();
                        bundle0.putParcelable(Tokens.Mine.SKIP_TO_MYCARD, mmBean);
                        startNewAct(MyCardActivity.class, bundle0);
                    }
                    break;
                case R.id.llfBaseInfo://基本信息
                    mContext.startActivity(new Intent(getContext(), PersonalDataActivity.class));
                    break;
                case R.id.linear_work_points://工分
                    Bundle bundle = new Bundle();
                    bundle.putString("points", tvWorkPoints.getText().toString());
                    startNewAct(WorkPointsCenterActivity.class, bundle);
                    break;
                case R.id.linear_growth://成长值
                    startNewAct(MyGrowthActivity.class, null);
                    break;
                case R.id.linear_my_performance://绩效
                    Bundle bundle1 = new Bundle();
                    int jxValue = 0;
                    if (mmBean != null) {
                        jxValue = mmBean.getPerformance();
                    }
                    bundle1.putInt(Tokens.Mine.SKIP_TO_JX_ACTIVITY, jxValue);
                    startNewAct(MyPerformanceActivity.class, bundle1);
                    break;
            }
        }
    }

    public void startNewAct(Class<?> targetActClass, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(getContext(), targetActClass);
        if (data != null) {
            intent.putExtras(data);
        }
        mContext.startActivity(intent);
    }
}

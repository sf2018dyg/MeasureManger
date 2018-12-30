package com.soonfor.measuremanager.me.activity.my_information;

import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.me.bean.AnnouncementBean;
import com.soonfor.measuremanager.me.presenter.my_information.IMyInformationView;
import com.soonfor.measuremanager.me.presenter.my_information.MyInformationPresenter;
import com.soonfor.measuremanager.tools.Tokens;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class AnnouncementDetailActivity extends BaseActivity<MyInformationPresenter> implements IMyInformationView{

    @BindView(R.id.imgcover) ImageView imgfAnn;
    @BindView(R.id.tvfAnnTitle) TextView tvfTitle;
    @BindView(R.id.tvfAmmounDate) TextView tvfAnnDate;
    @BindView(R.id.tvfReadingNum) TextView tvfReadingNum;
    @BindView(R.id.tvfDesc) TextView tvfDesc;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_announcement_detail;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initViews() {
        AnnouncementBean accBean = getIntent().getParcelableExtra(Tokens.Mine.SKIP_TO_ANNOUNCEMENTDETAIL);
        if(accBean!=null){
            tvfTitle.setText(accBean.getfAnnTheme());
            tvfAnnDate.setText(accBean.getfAnnTime());
            if(!accBean.getfAnnImagePath().equals("")) {
                Picasso.with(this)
                        .load(accBean.getfAnnImagePath())
                        .placeholder(R.mipmap.loading)
                        .error(R.mipmap.zanuw)
                        .into(imgfAnn);
            }
            tvfDesc.setText("\u3000\u3000"+ accBean.getfAnnDesc());
        }
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
}

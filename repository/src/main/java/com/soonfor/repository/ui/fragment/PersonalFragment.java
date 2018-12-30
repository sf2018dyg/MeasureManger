package com.soonfor.repository.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.R;

import com.soonfor.repository.base.RepBaseFragment;
import com.soonfor.repository.model.person.PersonInfo;
import com.soonfor.repository.presenter.personal.PersonalPresenter;
import com.soonfor.repository.tools.CircleImageView;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.ui.RepApp;
import com.soonfor.repository.ui.activity.personal.MyAddedKnowLedgeActivity;
import com.soonfor.repository.ui.activity.personal.MyFavoriteActivity;
import com.soonfor.repository.view.personal.IPersonalView;
import com.squareup.picasso.Picasso;

import cn.jzvd.JZVideoPlayer;


/**
 * 作者：DC-DingYG on 2018-06-15 11:49
 * 邮箱：dingyg012655@126.com
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/8/2 0002 8:36
 * 邮箱：suibozhu@139.com
 * 修改目的：完善功能
 */
public class PersonalFragment extends RepBaseFragment<PersonalPresenter> implements IPersonalView, View.OnClickListener {

    ImageView ivfLeft;
    ImageView imageView;
    CircleImageView ciVfHead;//圆形图片头像
    LinearLayout llfMyFavourite;
    LinearLayout llfMyAddedKnowledge;
    TextView tvfSName;
    TextView tvfStation;

    private void findViewById(View view) {
        ivfLeft = view.findViewById(R.id.ivfLeft);
        imageView = view.findViewById(R.id.imgfNews);
        ciVfHead = view.findViewById(R.id.civfHead);
        tvfSName = view.findViewById(R.id.tvfSName);
        tvfStation = view.findViewById(R.id.tvfStation);
        llfMyFavourite = view.findViewById(R.id.llfMyFavourite);
        llfMyAddedKnowledge = view.findViewById(R.id.llfMyAddedKnowledge);
        llfMyFavourite.setOnClickListener(this);
        llfMyAddedKnowledge.setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        presenter = PersonalPresenter.getInstense(this);
    }

    @Override
    protected void initViews() {
        PersonInfo personInfo = Hawk.get(Tokens.SP_PERSONINFO, null);
        if (personInfo == null) {
            presenter.getPersonalInfo(mActivity);
        } else {
            refreshInfo(personInfo);
        }
        ivfLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFinishFullWindow = JZVideoPlayer.backPress();//是否先关闭了正在全屏播放视频的窗口
                if (!isFinishFullWindow) {
                    Hawk.delete(Tokens.SP_PERSONINFO);
                    mActivity.finish();
                }
            }
        });
        ciVfHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.showCaveatToast(mActivity, "暂无消息！");
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        BasefindViewById(rootView);
        findViewById(rootView);
        return rootView;
    }

    @Override
    protected void updateViews(boolean isRefresh) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //网络请求数据
            if (presenter != null && mActivity != null) {
                presenter.getPersonalInfo(mActivity);
            }
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void showDataToView(String returnJson) {
        super.showDataToView(returnJson);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llfMyFavourite) {
            startNewAct(MyFavoriteActivity.class);
        } else if (view.getId() == R.id.llfMyAddedKnowledge) {
            startNewAct(MyAddedKnowLedgeActivity.class);
        }
    }

    @Override
    public void refreshInfo(PersonInfo personInfo) {
        if (personInfo != null) {
            tvfSName.setText(personInfo.getName());
            tvfStation.setText(personInfo.getPost());
            if (!TextUtils.isEmpty(personInfo.getAvatar()) && !RepApp.DOWNLOAD_FILE.equals("")) {
                Picasso.with(getContext())
                        .load(RepApp.DOWNLOAD_FILE + "" + personInfo.getAvatar())
                        .placeholder(R.mipmap.avatar_default)
                        .error(R.mipmap.avatar_default)
                        .into(ciVfHead);
            }else {
                ciVfHead.setImageResource(R.mipmap.avatar_default);
            }
            Hawk.put(Tokens.SP_PERSONINFO, personInfo);
        }
    }
}

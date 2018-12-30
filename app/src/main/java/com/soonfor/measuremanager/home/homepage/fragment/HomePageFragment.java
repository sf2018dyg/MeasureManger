package com.soonfor.measuremanager.home.homepage.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.gallerlib.GallerViewPager;
import com.soonfor.gallerlib.ScaleGallerTransformer;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.complexruler.activity.FuChiMainActivity;
import com.soonfor.measuremanager.home.design.activity.DesignMainActivity;
import com.soonfor.measuremanager.home.grab.activity.GrabPondActivity;
import com.soonfor.measuremanager.home.homepage.activity.DesignReTieActivity;
import com.soonfor.measuremanager.home.homepage.presenter.IHomePageView;
import com.soonfor.measuremanager.home.liangchi.activity.LiangChiMainActivity;
import com.soonfor.measuremanager.home.lofting.activity.LoftingMainActivity;
import com.soonfor.measuremanager.home.login.activity.LoginActivity;
import com.soonfor.measuremanager.home.main.MainActivity;
import com.soonfor.measuremanager.home.homepage.adapter.SaleFunnelAdapter;
import com.soonfor.measuremanager.home.homepage.adapter.ShouYeAdvertisementAdpter;
import com.soonfor.measuremanager.home.homepage.adapter.topAchievementAdpter;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypes;
import com.soonfor.measuremanager.home.homepage.model.bean.TaskTypesDetail;
import com.soonfor.measuremanager.home.homepage.model.bean.topAchievementEntity;
import com.soonfor.measuremanager.home.homepage.presenter.HomePagePresenter;
import com.soonfor.measuremanager.home.othertask.activity.OtherTaskMainActivity;
import com.soonfor.measuremanager.me.activity.my_information.MyInformationActivity;
import com.soonfor.measuremanager.tools.CommonUtils;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.Tokens;
import com.soonfor.measuremanager.view.floatactionbutton.DraggableFloatingButton;
import com.soonfor.repository.tools.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

/**
 * 作者：DC-ZhuSuiBo on 2018/1/27 11:22
 * 邮箱：suibozhu@139.com
 * dingyg整理于 2018-11-27
 */

public class HomePageFragment extends Fragment implements IHomePageView {

    MainActivity mainActivity;
    Unbinder unbinder;
    @BindView(R.id.fab)
    DraggableFloatingButton fab;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;
    @BindView(R.id.rvfOther)
    RecyclerView rvfOtherTask;
    @BindView(R.id.Advertisement)
    GallerViewPager Advertisement;
    ShouYeAdvertisementAdpter syAdpter;
    @BindView(R.id.topAchievement)
    RecyclerView topAchievement;
    @BindView(R.id.lightTxt)
    TextView lightTxt;
    @BindView(R.id.tvmore)
    TextView tvmore;
    @BindView(R.id.topPosts)
    RecyclerView topPosts;
    @BindView(R.id.newsmsg)
    ImageView newsmsg;
    @BindView(R.id.tvlcrw_top)
    TextView tvlcrw_top;
    @BindView(R.id.tvsjrw_top)
    TextView tvsjrw_top;
    @BindView(R.id.tvfcrw_top)
    TextView tvfcrw_top;
    @BindView(R.id.tvfyrw_top)
    TextView tvfyrw_top;
    @BindView(R.id.rlnewsmsg)
    RelativeLayout rlnewsmsg;
    @BindView(R.id.tvqiangnum)
    TextView tvqiangnum;

    @BindView(R.id.txtShejishiyejiError)
    TextView txtShejishiyejiError;
    @BindView(R.id.txtRetieError)
    TextView txtRetieError;

    private HomePagePresenter presenter;
    private topAchievementAdpter topAdpter;
    private SaleFunnelAdapter adapter1, adapter2, adapter3, adapter4, otherAdapter;
    private GridLayoutManager manager1, manager2, manager3, manager4, manager5, otherManager;
    private boolean isRevisible = false;//是否重新可见
    private boolean isResume = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouye, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainActivity = (MainActivity) getActivity();
        isRevisible = true;
        isResume = false;
        initView();//初始化视图 // 页面初始化已经完成 变量置为true
        //发起网络请求的方法
        //presenter.requestNet(this.getActivity()); //发起网络请求的方法
        return view;
    }

    public void initView() {
        presenter = new HomePagePresenter(this, mainActivity);
        List<String> syPicsLt = new ArrayList<>();
        syPicsLt.add("1");
        syPicsLt.add("2");
        syPicsLt.add("3");
        showPosterPics(1, syPicsLt);
        showTaskNum(presenter.getDefaultTaskOverview());
        presenter.requestNet(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isRevisible) {
            isResume = false;
            presenter.requestNet(mainActivity);
        }
    }

    @OnClick({R.id.tvmore, R.id.newsmsg, R.id.fab, R.id.tvlcrw_top, R.id.tvsjrw_top, R.id.tvfcrw_top, R.id.tvfyrw_top, R.id.tvfOtherTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvmore:
                startActivity(new Intent(mainActivity, DesignReTieActivity.class));
                break;
            case R.id.newsmsg:
                startActivity(new Intent(mainActivity, MyInformationActivity.class));
                break;
            case R.id.fab:
                startActivity(new Intent(mainActivity, GrabPondActivity.class));
                break;
            case R.id.tvlcrw_top:
                Intent intent = new Intent(mainActivity, LiangChiMainActivity.class);
                intent.putExtra("posi", 0);
                startActivity(intent);
                break;
            case R.id.tvsjrw_top:
                Intent intentSj = new Intent(mainActivity, DesignMainActivity.class);
                intentSj.putExtra(Tokens.Design.SKIPTOFRAGMENT_STATUS, 0);
                startActivity(intentSj);
                break;
            case R.id.tvfcrw_top:
                intent = new Intent(mainActivity, FuChiMainActivity.class);
                intent.putExtra("posi", 0);
                startActivity(intent);
                break;
            case R.id.tvfyrw_top:
                Intent intentFy = new Intent(mainActivity, LoftingMainActivity.class);
                intentFy.putExtra(Tokens.Lofing.SKIPTOFRAGMENT_STATUS, 0);
                startActivity(intentFy);
                break;
            case R.id.tvfOtherTask:
                Intent intentOther = new Intent(mainActivity, OtherTaskMainActivity.class);
                intentOther.putExtra(Tokens.OtherTask.SKIPTOFRAGMENT_STATUS, 0);
                startActivity(intentOther);
                break;
        }
    }

    //展示海报
    @Override
    public void showPosterPics(int type, List<String> syPicsLt) {
        if (syPicsLt != null && syPicsLt.size() > 0) {
            if (syAdpter == null) {
                syAdpter = new ShouYeAdvertisementAdpter(getContext(), syPicsLt);
                Advertisement.setAdapter(syAdpter);
                Advertisement.setPageTransformer(true, new ScaleGallerTransformer());
                Advertisement.setDuration(4000);
                Advertisement.startAutoCycle();
                Advertisement.setSliderTransformDuration(1500, null);
            } else {
                syAdpter.notifyDataSetChanged(syPicsLt);
            }
        }
        Advertisement.setVisibility(View.VISIBLE);
    }

    //显示任务数量
    @Override
    public void showTaskNum(List<TaskTypes> numInfos) {
        AsyShowView(new AsyInterface() {
            @Override
            public void showView() {
                for (int i = 0; i < numInfos.size(); i++) {
                    TaskTypes taskTypes = numInfos.get(i);
                    List<TaskTypesDetail> details = taskTypes.getTaskDetails();
                    if (taskTypes.getTaskType().equals("Measure")) {
                        if (adapter1 == null) {
                            manager1 = new GridLayoutManager(getContext(), 3);
                            adapter1 = new SaleFunnelAdapter(getContext(), details, 1);
                            recyclerView1.setLayoutManager(manager1);
                            recyclerView1.setAdapter(adapter1);
                        } else
                            adapter1.notifyDataSetChanged(details);
                    } else if (taskTypes.getTaskType().equals("Design")) {
                        if (adapter2 == null) {
                            manager2 = new GridLayoutManager(getContext(), 3);
                            adapter2 = new SaleFunnelAdapter(getContext(), details, 2);
                            recyclerView2.setLayoutManager(manager2);
                            recyclerView2.setAdapter(adapter2);
                        } else {
                            adapter2.notifyDataSetChanged(details);
                        }
                    } else if (taskTypes.getTaskType().equals("Remeasure")) {
                        if (adapter3 == null) {
                            manager3 = new GridLayoutManager(getContext(), 3);
                            adapter3 = new SaleFunnelAdapter(getContext(), details, 3);
                            recyclerView3.setLayoutManager(manager3);
                            recyclerView3.setAdapter(adapter3);
                        } else {
                            adapter3.notifyDataSetChanged(details);
                        }
                    } else if (taskTypes.getTaskType().equals("Mark")) {
                        if (adapter4 == null) {
                            manager4 = new GridLayoutManager(getContext(), 3);
                            adapter4 = new SaleFunnelAdapter(getContext(), details, 4);
                            recyclerView4.setLayoutManager(manager4);
                            recyclerView4.setAdapter(adapter4);
                        } else adapter4.notifyDataSetChanged(details);
                    } else if (taskTypes.getTaskType().equals("Other")) {
                        if (otherAdapter == null) {
                            otherManager = new GridLayoutManager(getContext(), 3);
                            otherAdapter = new SaleFunnelAdapter(getContext(), details, 5);
                            rvfOtherTask.setLayoutManager(otherManager);
                            rvfOtherTask.setAdapter(otherAdapter);
                        } else otherAdapter.notifyDataSetChanged(details);
                    }
                }
            }
        });
    }

    //待抢单数量
    @Override
    public void showGrabNum(String num) {
        tvqiangnum.setText("(" + num + ")");
    }

    //前10名
    @Override
    public void show10Tops(topAchievementEntity topEntity) {
        AsyShowView(new AsyInterface() {
            @Override
            public void showView() {
                lightTxt.setText("我的业绩: " + CommonUtils.formatStr(topEntity.getMyRank()) + "");
                //获取设计师前10业绩
                txtShejishiyejiError.setVisibility(View.GONE);
                lightTxt.setVisibility(View.VISIBLE);
                topAchievement.setVisibility(View.VISIBLE);
                manager5 = new GridLayoutManager(getContext(), 10);
                topAdpter = new topAchievementAdpter(mainActivity, topEntity.getDetails());
                topAchievement.setLayoutManager(manager5);
                topAchievement.setAdapter(topAdpter);
            }
        });
    }

    //消息的数量
    @Override
    public void showNoticeNum(int num) {
        AsyShowView(new AsyInterface() {
            @Override
            public void showView() {
                new QBadgeView(mainActivity).bindTarget(rlnewsmsg).setBadgeNumber(-1).setBadgePadding(3, true).setGravityOffset(1, 1, true);
            }
        });
    }

    @Override
    public void showTip(String msg) {
        if (msg != null && !msg.equals(""))
            MyToast.showToast(mainActivity, msg);
    }

    //异步展示数据
    private void AsyShowView(AsyInterface asy) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        asy.showView();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}


interface AsyInterface {
    void showView();
}




package com.soonfor.repository.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.ninegrid.NineGridView;
import com.orhanobut.hawk.Hawk;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.soonfor.repository.R;
import com.soonfor.repository.http.api.RepRequest;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.person.PersonInfo;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.tools.tablayout.MTabLayout;
import com.soonfor.repository.tools.tablayout.TabItem;
import com.soonfor.repository.ui.RepApp;
import com.soonfor.repository.ui.activity.knowledge.KnowledgeDetailActivity;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;
import com.soonfor.repository.ui.fragment.PersonalFragment;
import com.soonfor.repository.ui.fragment.SeekhelpFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jesse.nativelogger.NLogger;
import cn.jzvd.JZVideoPlayer;

@Route(path = "/repository/ui/activity/RepositoryMainActivity")
public class RepositoryMainActivity extends FragmentActivity implements MTabLayout.OnTabClickListener, RepAsyncUtils.AsyncCallback {

    //@BindView(R.id.llfTop)
    LinearLayout llfTop;
    // @BindView(R.id.toolbar)
    Toolbar toolbar;
    // @BindView(R.id.tvfTitile)
    TextView tvfTitle;
    // @BindView(R.id.imgfRight)
    ImageView imgfRight;
    // @BindView(R.id.tab_layout)
    MTabLayout mTabLayout;
    private RepositoryMainActivity mActivity;
    private ArrayList<TabItem> tabs;
    //RepBaseFragment fragment;
    Fragment frament_knowledge, fragment_personal, fragment_seekhelp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_main);
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        mActivity = RepositoryMainActivity.this;
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String serviceAddress = "";
        String uuid = "";
        try {
            serviceAddress = bundle.getString("SERVICEADDRESS", "");
            uuid = bundle.getString("UUID", "");
        } catch (Exception e) {
        }
        if (serviceAddress.equals("") || uuid.equals("")) {
            finish();
        } else {
            RepApp.AppContext = getApplicationContext();
            RepApp.dm = getResources().getDisplayMetrics();
            if (!Hawk.isBuilt()) {
                Hawk.init(getApplicationContext()).build();
            }
            //初始化9九宫格
            NineGridView.setImageLoader(new RepApp.GlideImageLoader());

            if (serviceAddress != null && !serviceAddress.equals("")) {
                if (!serviceAddress.startsWith("http://")) {
                    serviceAddress = "http://" + serviceAddress;
                }
                Hawk.put(DataTools.ServerAdr, serviceAddress);
                Hawk.put(DataTools.UUID, uuid);
            }
            findViewById();
            PersonInfo personInfo = null;
            try {
                personInfo = bundle.getParcelable("MINEINFO");
            } catch (Exception e) {
            }
            Hawk.put(Tokens.SP_PERSONINFO, personInfo);
            RepApp.DOWNLOAD_FILE = bundle.getString("DOWNLOAD_ADDRESS", "");//取crm图片
            String uploadpath = bundle.getString("UPLOADPATH", "");//上传下载地址
            if(RepApp.DOWNLOAD_FILE!=null && !RepApp.DOWNLOAD_FILE.toLowerCase().startsWith("http://")){
                RepApp.DOWNLOAD_FILE = "http://" + RepApp.DOWNLOAD_FILE;
            }
            if(!uploadpath.equals("")){
                if(!uploadpath.toLowerCase().startsWith("http://")){
                    uploadpath = "http://" + uploadpath;
                }
                Hawk.put(Tokens.SP_UPLOADCNETERPATH, uploadpath);
            }else {
                RepRequest.getSysCode(mActivity, "uploadCenter", this);
            }
            Hawk.delete(Tokens.SP_ARTIFICIALSTAFFPATH);
            //initSmallVideo(mActivity);
            initData();

        }
    }

    @Override
    public void success(int requestCode, String data) {
        switch (requestCode) {
            case RepRequest.Knowledge.GET_SYSCODE:
                /**
                 * 修改人：DC-ZhuSuiBo on 2018/9/26 0026 11:37
                 * 邮箱：suibozhu@139.com
                 * 修改目的：
                 */
                try {
                    JSONArray jr = new JSONArray(data);
                    JSONObject o = new JSONObject(jr.get(0).toString());
                    if (o.getBoolean("success")) {
                        if (o.getString("code").equals("uploadCenter")) {
                            Hawk.put(Tokens.SP_UPLOADCNETERPATH, o.getString("value"));
                        }
                    } else {
                        Hawk.put(Tokens.SP_UPLOADCNETERPATH, "");
                    }

                } catch (Exception e) {
                    Hawk.put(Tokens.SP_UPLOADCNETERPATH, "");
                    NLogger.e(e.getMessage());
                }
                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String msg) {
        Hawk.put(Tokens.SP_UPLOADCNETERPATH, "");
    }

    private void findViewById() {
        llfTop = this.findViewById(R.id.llfTop);
        toolbar = this.findViewById(R.id.toolbar);
        tvfTitle = this.findViewById(R.id.tvfTitile);
        imgfRight = this.findViewById(R.id.imgfRight);
        mTabLayout = this.findViewById(R.id.tab_layout);
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InFinish();
            }
        });
        imgfRight.setVisibility(View.VISIBLE);
        imgfRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SearchActivity.class));
            }
        });
    }

    private void initData() {
        tabs = new ArrayList<TabItem>();
        frament_knowledge = new KnowledgeFragment();
        tabs.add(new TabItem(R.drawable.selector_tab_moments, R.mipmap.search, R.string.knowledge, "知识", frament_knowledge));
        fragment_seekhelp = new SeekhelpFragment();
        tabs.add(new TabItem(R.drawable.selector_tab_msg, -1, R.string.seek_help, "求助", fragment_seekhelp));
        fragment_personal = new PersonalFragment();
        tabs.add(new TabItem(R.drawable.selector_tab_profile, R.mipmap.news1, R.string.personnal, "个人中心", fragment_personal));
        mTabLayout.initData(tabs, this);
        tvfTitle.setText("知识");
        mTabLayout.setCurrentTab(0);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.rlfFragment, frament_knowledge).commit();


//        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(0);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//            @Override
//            public void onPageSelected(int position) {
//                mTabLayout.setCurrentTab(position);
//                tvfTitle.setText(tabs.get(position).title);
//                imgfRight.setImageResource(tabs.get(position).rightImageId);
//            }
//            @Override public void onPageScrollStateChanged(int state) {}
//        });
//        mViewPager.setNoScroll(true);

    }

    @Override
    public void onTabClick(TabItem tabItem) {
        if (tabs.indexOf(tabItem) == 2) {
            llfTop.setVisibility(View.GONE);
        } else {
            llfTop.setVisibility(View.VISIBLE);
            tvfTitle.setText(tabItem.title);
            imgfRight.setVisibility(View.VISIBLE);
            imgfRight.setImageResource(tabItem.rightImageId);
        }
        mTabLayout.setCurrentTab(tabs.indexOf(tabItem));
        if (tabs.indexOf(tabItem) == 0) {//第一个一级Fragment中嵌套了二级Fragment，当切换到其一级Fragment后再切换回来时，
            //发现子fragment被销毁，所以需要重新创建
            this.getSupportFragmentManager().beginTransaction().replace(R.id.rlfFragment, new KnowledgeFragment()).commit();
        } else {
            this.getSupportFragmentManager().beginTransaction().replace(R.id.rlfFragment, tabItem.tagFragmentClz).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataTools.clearAll();
    }

    @Override
    public void onBackPressed() {
        InFinish();
    }

    private void InFinish() {
        boolean isFinishFullWindow = JZVideoPlayer.backPress();//是否先关闭了正在全屏播放视频的窗口
        if (!isFinishFullWindow) {
            Hawk.delete(Tokens.SP_PERSONINFO);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Tokens.Knowledge.CLICK_TO_DETAIL:
                    int listPos = data.getIntExtra("POSITION", -1);
                    KnowledgeDetailActivity.listPositon = listPos;
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}

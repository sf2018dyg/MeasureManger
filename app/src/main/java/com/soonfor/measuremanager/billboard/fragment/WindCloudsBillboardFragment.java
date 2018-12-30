package com.soonfor.measuremanager.billboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.billboard.adpter.BillBoardListAdapter;
import com.soonfor.measuremanager.billboard.bean.BillBoardBean;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.DensityUtils;
import com.soonfor.measuremanager.tools.ViewTools;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.CommonTabLayout;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.entity.TabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.CustomTabEntity;
import com.soonfor.measuremanager.view.FlycoTabLayout_Lib.listener.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：DC-ZhuSuiBo on 2018/2/19 14:55
 * 邮箱：suibozhu@139.com
 */
public class WindCloudsBillboardFragment extends Fragment implements AsyncUtils.AsyncCallback {
    Unbinder unbinder;
    @BindView(R.id.tl_1)
    CommonTabLayout tl_1;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"业绩榜", "点赞榜", "人气榜"};
    @BindView(R.id.describeTxt)
    TextView describeTxt;
    @BindView(R.id.img_first)
    ImageView img_first;
    @BindView(R.id.img_second)
    ImageView img_second;
    @BindView(R.id.img_three)
    ImageView img_three;
    @BindView(R.id.txt_first_name)
    TextView txt_first_name;
    @BindView(R.id.txtd_first_num)
    TextView txtd_first_num;
    @BindView(R.id.txt_second_name)
    TextView txt_second_name;
    @BindView(R.id.txtd_second_num)
    TextView txtd_second_num;
    @BindView(R.id.txt_three_name)
    TextView txt_three_name;
    @BindView(R.id.txtd_three_num)
    TextView txtd_three_num;
    @BindView(R.id.tl_txt)
    TextView tl_txt;
    GridLayoutManager manager;
    @BindView(R.id.rvbillboard)
    RecyclerView rvbillboard;
    BillBoardListAdapter adapter;
    List<BillBoardBean> boardBeans;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if(mTabEntities==null){
                mTabEntities = new ArrayList<>();
            }else if(mTabEntities.size()>0){
                mTabEntities.clear();
            }
            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], R.mipmap.icon_quanjing, R.mipmap.icon_quanjing));
            }
            final String samplePath = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2579211415,1545415720&fm=27&gp=0.jpg";
            manager = new GridLayoutManager(getContext(), 1);
            rvbillboard.setLayoutManager(manager);

            tl_1.setTabData(mTabEntities);
            tl_1.setCurrentTab(0);
            tl_1.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    switch (position) {
                        case 0:
                            initOTT(samplePath,0);
                            break;
                        case 1:
                            initOTT(samplePath,1);
                            break;
                        case 2:
                            initOTT(samplePath,2);
                            break;
                    }
                }

                @Override
                public void onTabReselect(int position) {

                }
            });

            Picasso.with(getContext())
                    .load(samplePath)
                    .placeholder(R.mipmap.default_s)
                    .error(R.mipmap.default_s)
                    .into(img_first);
            Picasso.with(getContext())
                    .load(samplePath)
                    .placeholder(R.mipmap.default_s)
                    .error(R.mipmap.default_s)
                    .into(img_second);
            Picasso.with(getContext())
                    .load(samplePath)
                    .placeholder(R.mipmap.default_s)
                    .error(R.mipmap.default_s)
                    .into(img_three);
            initOTT(samplePath,0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_windcloudsbillboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initOTT(String samplePath, int stype) {
        switch (stype) {
            case 0:
                describeTxt.setText("[ 您的业绩: ￥8000; 排名: 205 ]");
                txt_first_name.setText("青山周平");
                txtd_first_num.setText("242565");
                txt_second_name.setText("woood空间设计");
                txtd_second_num.setText("201225");
                txt_three_name.setText("方建兴");
                txtd_three_num.setText("185642");
                ViewTools.returnDrawable(getContext(), txtd_first_num, 1, R.mipmap.icn_yeji, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_second_num, 1, R.mipmap.icn_yeji, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_three_num, 1, R.mipmap.icn_yeji, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                tl_txt.setText("业绩");
                setListDatas(samplePath, stype);
                break;
            case 1:
                describeTxt.setText("[ 您的作品总被点赞数: 1128; 排名: 201 ]");
                txt_first_name.setText("青山周平");
                txtd_first_num.setText("242565");
                txt_second_name.setText("woood空间设计");
                txtd_second_num.setText("201225");
                txt_three_name.setText("方建兴");
                txtd_three_num.setText("185642");
                ViewTools.returnDrawable(getContext(), txtd_first_num, 1, R.mipmap.icn_dianzan, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_second_num, 1, R.mipmap.icn_dianzan, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_three_num, 1, R.mipmap.icn_dianzan, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                tl_txt.setText("被点赞数");
                setListDatas(samplePath, stype);
                break;
            case 2:
                describeTxt.setText("[ 您的粉丝数: 128; 排名: 257 ]");
                txt_first_name.setText("青山周平");
                txtd_first_num.setText("242565");
                txt_second_name.setText("woood空间设计");
                txtd_second_num.setText("201225");
                txt_three_name.setText("方建兴");
                txtd_three_num.setText("185642");
                ViewTools.returnDrawable(getContext(), txtd_first_num, 1, R.mipmap.icn_renqi, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_second_num, 1, R.mipmap.icn_renqi, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                ViewTools.returnDrawable(getContext(), txtd_three_num, 1, R.mipmap.icn_renqi, new int[]{0, 0, DensityUtils.dp2px(getContext(), 12), DensityUtils.dp2px(getContext(), 12)});
                tl_txt.setText("粉丝数");
                setListDatas(samplePath, stype);
                break;
        }
    }

    private void setListDatas(String samplePath, int stype) {
        boardBeans = new ArrayList<BillBoardBean>();
        boardBeans.add(new BillBoardBean("1", samplePath, "吴婷婷", "设计，我们是认真的", "￥15254", "15254", "15254"));
        boardBeans.add(new BillBoardBean("2", samplePath, "韩文强", "设计师在成长中..", "￥12543", "12543", "12543"));
        boardBeans.add(new BillBoardBean("3", samplePath, "梁志天", "香港顶尖设计师", "￥10204", "10204", "10204"));
        boardBeans.add(new BillBoardBean("4", samplePath, "隈研吾", "日本著名设计师", "￥10045", "10045", "10045"));
        boardBeans.add(new BillBoardBean("5", samplePath, "衍射设计", "做自己喜欢的设计", "￥9846", "9846", "9846"));
        boardBeans.add(new BillBoardBean("6", samplePath, "吴婷婷", "设计，我们是认真的", "￥15254", "15254", "15254"));
        boardBeans.add(new BillBoardBean("7", samplePath, "韩文强", "设计师在成长中..", "￥12543", "12543", "12543"));
        boardBeans.add(new BillBoardBean("8", samplePath, "梁志天", "香港顶尖设计师", "￥10204", "10204", "10204"));
        boardBeans.add(new BillBoardBean("9", samplePath, "隈研吾", "日本著名设计师", "￥10045", "10045", "10045"));
        boardBeans.add(new BillBoardBean("10", samplePath, "衍射设计", "做自己喜欢的设计", "￥9846", "9846", "9846"));
        adapter = new BillBoardListAdapter(getContext(), boardBeans, stype);
        rvbillboard.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void success(int requestCode, JSONObject object) {
        switch (requestCode) {
//            case Request.GET_OPTION_TYPE:
//
//                Gson gson = new Gson();
//                LogTools.showLog(getContext(), object.toString());
//                BaseResultBean<ListBean<OptionTypeBean>> resultBean2 =
//                        gson.fromJson(object.toString(), new TypeToken<BaseResultBean<ListBean<OptionTypeBean>>>() {
//                        }.getType());
//
//                AppCache.typeBeanList = resultBean2.getData().getList();
//                break;
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {

    }
}

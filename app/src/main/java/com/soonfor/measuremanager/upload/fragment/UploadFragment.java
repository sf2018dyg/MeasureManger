package com.soonfor.measuremanager.upload.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dynamicpicpro.PhotoPreviewActivity;
import com.dynamicpicpro.PhotoSelectorActivity;
import com.dynamicpicpro.model.PhotoModel;
import com.dynamicpicpro.model.UploadGoodsBean;
import com.dynamicpicpro.util.CommonUtils;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.afflatus.bean.DesignCaseBean;
import com.soonfor.measuremanager.bases.BaseFragment;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.upload.activity.SelectCaseActivity;
import com.soonfor.measuremanager.upload.adpter.uploadAdpter;
import com.soonfor.measuremanager.upload.model.bean.uploadBean;
import com.soonfor.measuremanager.upload.presenter.CaseBasePresenter;
import com.soonfor.measuremanager.upload.view.ICaseBaseView;
import com.soonfor.measuremanager.view.RoundJiaoImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ding-Yg on 2018/1/11.
 */

/**
 * 修改人：DC-ZhuSuiBo on 2018/4/4 0004 11:35
 * 邮箱：suibozhu@139.com
 * 修改目的：添加功能
 */

public class UploadFragment extends BaseFragment<CaseBasePresenter> implements ICaseBaseView {
    @BindView(R.id.imgpath)
    RoundJiaoImageView imgpath;
    @BindView(R.id.invMsg)
    LinearLayout invMsg;
    @BindView(R.id.uploadList)
    RecyclerView uploadList;
    GridLayoutManager manager;
    @BindView(R.id.txtSubmit)
    TextView txtSubmit;
    List<uploadBean> uploadBeans;
    List<UploadGoodsBean> ul;
    uploadAdpter uploadAdpter;
    @BindView(R.id.txtChangePlan)
    TextView txtChangePlan;
    Unbinder unbinder;
    boolean isFirstSelectPlan = true;
    @BindView(R.id.rlnewsmsg)
    RelativeLayout rlnewsmsg;
    @BindView(R.id.txtPlanMsg)
    TextView txtPlanMsg;

    public static UploadFragment newInstance() {
        Bundle args = new Bundle();
        UploadFragment fragment = new UploadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initPresenter() {
        presenter = new CaseBasePresenter(this);
    }

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_upload;
    }

    /**
     * 初始化视图控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        if (isFirstSelectPlan) {
            isHaveHead(false);
        } else {
            isHaveHead(true);
        }
    }

    private void isHaveHead(boolean isHave) {
        if (isHave) {
            invMsg.setVisibility(View.GONE);
            invMsg.setOnClickListener(null);

            txtChangePlan.setVisibility(View.VISIBLE);
            txtPlanMsg.setVisibility(View.VISIBLE);
        } else {
            invMsg.setVisibility(View.VISIBLE);
            invMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SelectCaseActivity.class);
                    startActivityForResult(intent, 7777);
                }
            });

            txtChangePlan.setVisibility(View.INVISIBLE);
            txtPlanMsg.setVisibility(View.INVISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        uploadBeans = new ArrayList<uploadBean>();
        ul = new ArrayList<>();
        ul.add(null);
        uploadBeans.add(new uploadBean(0, "客厅", "LIVINGROOM", ul));
        uploadBeans.add(new uploadBean(1, "卧室", "BEDRROOM", ul));
        manager = new GridLayoutManager(getActivity(), 1);
        uploadList.setLayoutManager(manager);
        uploadAdpter = new uploadAdpter(getActivity(), uploadBeans, new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                List<UploadGoodsBean> img_uri = (ArrayList<UploadGoodsBean>) arg0.getTag(R.id.imgurls_key);
                int index = (int) arg0.getTag(R.id.imgurls_posi_key);

                Intent intent = new Intent(getActivity(), PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 9 - (img_uri.size() - 1));
                intent.putExtra("index", index);
                startActivityForResult(intent, 0);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UploadGoodsBean> img_uri = (ArrayList<UploadGoodsBean>) v.getTag(R.id.imgurls_key);
                int index = (int) v.getTag(R.id.imgurls_posi_key);

                List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
                for (int i = 0; i < img_uri.size(); i++) {
                    if (img_uri.get(i) != null) {
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setOriginalPath(img_uri.get(i).getUrl());
                        photoModel.setChecked(true);
                        single_photos.add(photoModel);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("photos", (Serializable) single_photos);
                bundle.putInt("position", index);
                bundle.putString("save", "save");
                CommonUtils.launchActivity(getContext(), PhotoPreviewActivity.class, bundle);
            }
        }, new View.OnClickListener() {
            private boolean is_addNull;

            @Override
            public void onClick(View arg0) {
                List<UploadGoodsBean> img_uri = (ArrayList<UploadGoodsBean>) arg0.getTag(R.id.imgurls_key);
                int index = (int) arg0.getTag(R.id.imgurls_posi_key);

                is_addNull = true;
                String img_url = img_uri.remove(index).getUrl();
                for (int i = 0; i < img_uri.size(); i++) {
                    if (img_uri.get(i) == null) {
                        is_addNull = false;
                        continue;
                    }
                }
                if (is_addNull) {
                    img_uri.add(null);
                }

                /*notifyDataSetChanged();*/
            }
        });
        uploadList.setAdapter(uploadAdpter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtChangePlan, R.id.rlnewsmsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtChangePlan:
                startActivity(new Intent(getActivity(), SelectCaseActivity.class));
                break;
            case R.id.rlnewsmsg:
                MyToast.showToast(getActivity(), "功能建设中");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                    int index = data.getExtras().getInt("index");
                    List<UploadGoodsBean> ugb = uploadBeans.get(index).getUpPics();
                    for (String path : paths) {
                        ugb.add(new UploadGoodsBean(index, path, true));
                    }
                    uploadAdpter.notifyDataSetChanged(uploadBeans);
                }
                break;
            case 7777:
                try {
                    DesignCaseBean caseBean = data.getParcelableExtra("CaseBean");

                    isHaveHead(true);
                    ImageUtil.loadImage(getActivity(), "http:\\/\\/api.jiamm.cn\\/test\\/lfj\\/floor\\/drawing\\/5abeff9c9e5a9c7e709d8633\\/5a966ab8b0f8298f236ca452\\/1920\\/1080?chair=false", imgpath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        mLoadingDialog.dismiss();
    }
}

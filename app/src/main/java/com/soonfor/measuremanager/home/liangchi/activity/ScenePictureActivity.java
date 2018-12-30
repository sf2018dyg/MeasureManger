package com.soonfor.measuremanager.home.liangchi.activity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.orhanobut.hawk.Hawk;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.tools.MyToast;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：DC-ZhuSuiBo on 2018/3/7 0007 10:50
 * 邮箱：suibozhu@139.com
 * 现场照片
 */
public class ScenePictureActivity extends BaseActivity implements AsyncUtils.AsyncCallback {

    Context mContext;
    @BindView(R.id.nineGrid)
    NineGridView nineGrid;
    List<ImageInfo> datas;
    @BindView(R.id.llftxterror)
    LinearLayout llftxterror;
    @BindView(R.id.txterror)
    TextView txterror;
    @BindView(R.id.llfdata)
    LinearLayout llfdata;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_scene_picture;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);// 关闭title
        mContext = ScenePictureActivity.this;
        toolbar.findViewById(R.id.ivfLeft).setOnClickListener(listener);

        datas = Hawk.get("ImageInfo");
        if (datas != null) {

        } else {
            datas = new ArrayList<>();
        }
        nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, datas));

        if(datas.size()>0){
            llfdata.setVisibility(View.VISIBLE);
            llftxterror.setVisibility(View.GONE);
            txterror.setText("");
        }else{
            llfdata.setVisibility(View.INVISIBLE);
            llftxterror.setVisibility(View.VISIBLE);
            txterror.setText("暂无数据");
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    /**
     * 更新视图
     */
    public void showMsg(String msg) {
        MyToast.showToast(mContext, msg);
    }

    @Override
    public void success(int requestCode, JSONObject object) {


        /*//实景图
        datas = new ArrayList<>();
        for (int i = 0; i < mb.getMeasureInfo().getLivePictures().size(); i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(mb.getMeasureInfo().getLivePictures().get(i));
            info.setBigImageUrl(mb.getMeasureInfo().getLivePictures().get(i));
            datas.add(info);
        }
        nineGrid.setAdapter(new NineGridViewClickAdapter(getContext(), datas));*/
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {

    }
}

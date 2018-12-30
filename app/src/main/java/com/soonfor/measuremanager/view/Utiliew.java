package com.soonfor.measuremanager.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.bases.BaseActivity;
import com.soonfor.measuremanager.bases.ErrorBean;
import com.soonfor.measuremanager.home.othertask.adapter.VoiceAdapter;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.AttachDto;
import com.soonfor.measuremanager.home.othertask.model.bean.detail.VoiceBean;
import com.soonfor.measuremanager.http.api.Request;
import com.soonfor.measuremanager.http.httptools.AsyncUtils;
import com.soonfor.measuremanager.other.adapter.PhotoAdapter;
import com.soonfor.measuremanager.other.adapter.layoutmanager.FullyGridLayoutManager;
import com.soonfor.measuremanager.other.bean.FileUploadBean;
import com.soonfor.measuremanager.tools.AudioRecoderUtils;
import com.soonfor.measuremanager.tools.LogTools;
import com.soonfor.measuremanager.tools.MyToast;
import com.soonfor.measuremanager.tools.TimeUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/9.
 */

public class Utiliew extends LinearLayout implements AsyncUtils.AsyncCallback, View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewVoice;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlRecord;
    private RelativeLayout rlLocation;
    private TextView tvLocation;
    private LinearLayout llocation;
    private LinearLayout voice;
    private PopupWindowFactory mPop;
    private AudioRecoderUtils mAudioRecoderUtils;

    private String provider;//位置提供器
    private LocationManager locationManager;//位置服务

    private ImageView mImageView;
    private TextView mTextView;
    private String location;
    private Context context;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.LayoutManager managerVoice;
    private PhotoAdapter adapter;
    private VoiceAdapter voiceAdpter;
    private List<LocalMedia> localPics = new ArrayList<>();
    private List<String> paths = new ArrayList<>();
    private List<VoiceBean> voices = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnClickListner listener;
    private int MaxSelePicNum = 9;

    private View rootView;
    private int uploadvoicePos = 0;//当前上传的录音位置

    public Utiliew(Context context) {
        super(context);
    }

    public Utiliew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflater = LayoutInflater.from(getContext());
        mAudioRecoderUtils = new AudioRecoderUtils();
        findView();
        initView();
    }

    private void findView() {
        rootView = mInflater.inflate(R.layout.view_util, this, true);
        recyclerView = rootView.findViewById(R.id.rvfImgPics);
        recyclerViewVoice = rootView.findViewById(R.id.recyclerViewVoice);
        rlPhoto = rootView.findViewById(R.id.rl_photo);
        rlRecord = rootView.findViewById(R.id.rl_record);
        rlLocation = rootView.findViewById(R.id.rl_location);
        tvLocation = rootView.findViewById(R.id.tv_location);
        llocation = rootView.findViewById(R.id.location);
        voice = rootView.findViewById(R.id.voice);

        rlPhoto.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
    }

    private void initView() {
        manager = new FullyGridLayoutManager(context, 3);
        adapter = new PhotoAdapter(context, null, PhotoAdapter.TYPE_LOCAL, true);
        adapter.setListener(new PhotoAdapter.onItemClick() {
            @Override
            public void itemClick(View view, ArrayList<String> images, int adapterPosition) {

                PermissionsUtil.requestPermission(context, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permission) {
                                List<LocalMedia> localMedias = new ArrayList<>();
                                for (int i = 0; i < images.size(); i++) {
                                    LocalMedia localMedia = new LocalMedia();
                                    localMedia.setCompressPath(images.get(i));
                                    localMedia.setPath(images.get(i));
                                    localMedias.add(localMedia);
                                }
                                //启动相册并拍照
                                PictureSelector.create((Activity) context)
                                        .externalPicturePreview(adapterPosition, localMedias);
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permission) {

                            }
                        }, Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void deleteClick(View view, ArrayList<String> beans, int adapterPosition) {
                if (adapterPosition < localPics.size()) {
                    localPics.remove(adapterPosition);
                    paths.remove(adapterPosition);
                    adapter.notifyDataSetChanged(paths);
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        managerVoice = new GridLayoutManager(context, 1);
        voiceAdpter = new VoiceAdapter(context, null, new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = (int) v.getTag(R.id.item_voice);
                if (position < voices.size()) {
                    voices.remove(position);
                    voiceAdpter.notifyDataSetChanged(voices, true);
                }
                return false;
            }
        });
        recyclerViewVoice.setLayoutManager(managerVoice);
        recyclerViewVoice.setAdapter(voiceAdpter);

        View popView = View.inflate(getContext(), R.layout.layout_microphone, null);

        mPop = new PopupWindowFactory(getContext(), popView);

        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) popView.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) popView.findViewById(R.id.tv_recording_time);

        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            private long duration = 0;

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                duration = time;
                mTextView.setText(TimeUtils.long2String(duration));
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                voice.setVisibility(VISIBLE);
                mTextView.setText(TimeUtils.long2String(0));
                voices.add(new VoiceBean(filePath, duration));
                voiceAdpter.notifyDataSetChanged(voices, true);
            }
        });

        rlRecord.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (listener != null) {
                            boolean isVoiceGrant = listener.onVoiceClick(mPop);
                            if (isVoiceGrant) {
                                mAudioRecoderUtils.startRecord();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
//                        mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        mPop.dismiss();

                        break;


                }
                return true;
            }
        });

    }

    public OnClickListner getListener() {
        return listener;
    }

    public void setListener(OnClickListner listener) {
        this.listener = listener;
    }

    public void dismissLocation() {
        rlLocation.setVisibility(GONE);
    }

    private boolean isShowLocation;

    public void showLocation(String location) {
        this.location = location;
        if (!isShowLocation) {
            llocation.setVisibility(VISIBLE);
            tvLocation.setText(location);
        } else {
            llocation.setVisibility(GONE);
        }
        isShowLocation = !isShowLocation;
    }

    /**
     * 上传的图片url
     */
    private List<String> uploadImgUrls = new ArrayList<>();

    /**
     * 上传音频的url
     */
    private List<VoiceBean> uploadVoiceUrls = new ArrayList<>();

    /**
     * 计算结果个数
     */
    private int requestSuccessCount = 0;//成功
    private int requestFailCount = 0;//失败

    @Override
    public void success(int requestCode, JSONObject object) {
        LogTools.showLog(getContext(), "url:" + object.toString());
        switch (requestCode) {
            case POST_IMAGE:
                Gson gson = new Gson();
                FileUploadBean uploadBean = gson.fromJson(object.toString(), FileUploadBean.class);
                if (uploadBean != null) {
                    uploadImgUrls.add(uploadBean.getFilepath());
                }
                break;

            case POST_VOICE:
                gson = new Gson();
                uploadBean = gson.fromJson(object.toString(), FileUploadBean.class);
                if (uploadBean != null) {
                    uploadVoiceUrls.add(new VoiceBean(uploadBean.getFilepath(), voices.get(uploadvoicePos).getDuration()));
                    if(uploadvoicePos + 1 < voices.size()){
                        uploadvoicePos ++;
                        Request.uploadFileToCrm(getContext(), new File(voices.get(uploadvoicePos).getPath()), POST_VOICE, this);
                    }
                }
                break;
        }
        saveData(true, null);
    }

    public synchronized void saveData(boolean isSuccess, String msg) {
        if (isSuccess)
            requestSuccessCount++;
        else {
            requestFailCount++;
        }
        if (requestSuccessCount + requestFailCount == localPics.size() + voices.size()) {
            //如果全部请求都返回
            if (requestFailCount > 0) {
                requestFailCount = 0;
                MyToast.showFailToast(context, "附件上传失败:" + msg);
                try {
                    ((BaseActivity) context).mLoadingDialog.dismiss();
                } catch (Exception e) {
                }
            } else {
                requestSuccessCount = 0;
                //调用保存接口
                if (listener != null) {
                    List<AttachDto> attachDtos = new ArrayList<>();
                    if (tvLocation.getText() != null
                            && tvLocation.getText().toString().length()>0
                            && !tvLocation.getText().toString().contains("定位失败")) {
                        AttachDto attachDto = new AttachDto();
                        attachDto.setLocation(location);
                        attachDto.setAttachType("4");
                        attachDtos.add(attachDto);
                    }
                    if (uploadImgUrls.size() != 0) {
                        for (String s : uploadImgUrls) {
                            AttachDto attachDto = new AttachDto();
                            attachDto.setAttachType("0");
                            attachDto.setAttachUrl(s);
                            attachDtos.add(attachDto);
                        }
                    }

                    if (uploadVoiceUrls.size() != 0) {
                        for (VoiceBean s : uploadVoiceUrls) {
                            AttachDto attachDto = new AttachDto();
                            attachDto.setAttachType("1");
                            attachDto.setAttachUrl(s.getPath());
                            attachDto.setAttachDesc(s.getDuration()+"");
                            attachDtos.add(attachDto);
                        }
                    }
                    listener.onPostAfter(attachDtos);
                }
            }
        }
    }

    @Override
    public void fail(int requestCode, int statusCode, String errorMsg) {
        saveData(false, errorMsg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_photo:
                PermissionsUtil.requestPermission(context, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                if (listener != null) {
                                    listener.onPictureClick(MaxSelePicNum, localPics);
                                }
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                            }
                        }, Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.rl_location:
                if (listener != null) {
                    listener.onLocationClick();
                }
                break;
        }
    }

    /**
     * 监听
     */
    public interface OnClickListner {
        /**
         * 图片点击
         */
        void onPictureClick(int maxSelNum, List<LocalMedia> lastMedias);

        /**
         * 录音点击
         *
         * @param popupWindow
         * @return
         */
        boolean onVoiceClick(PopupWindowFactory popupWindow);

        /**
         * 地理位置点击
         */
        void onLocationClick();

        /**
         * 图片和音频的上传后，用于调用接口
         *
         * @param attaches
         */
        void onPostAfter(List<AttachDto> attaches);

        /**
         * 图片和音频上传前，用于数据校验
         *
         * @return
         */
        boolean onPostBefore();
    }

    public String getLocation() {
        return location;
    }

    public void initRecycler(final List<LocalMedia> pics) {
        if (localPics == null) {
            localPics = new ArrayList<>();
        }
        localPics.addAll(pics);
        for (int i = 0; i < localPics.size() - 1; i++) {
            for (int j = localPics.size() - 1; j > i; j--) {
                if (localPics.get(j).getPath().equals(localPics.get(i).getPath())) {
                    localPics.remove(j);
                }
            }
        }
        if (paths == null) paths = new ArrayList<>();
        else if (paths.size() > 0) paths.clear();
        for (int i = 0; i < localPics.size(); i++) {
            paths.add(localPics.get(i).getCompressPath());
        }
        recyclerView.setVisibility(VISIBLE);
        adapter.notifyDataSetChanged(paths);
    }

    public static final int POST_IMAGE = 1001;
    public static final int POST_VOICE = 1002;

    /**
     * 上传文件api
     */
    public void postFile() {
        requestSuccessCount = 0;
        requestFailCount = 0;
        if (uploadImgUrls != null && uploadImgUrls.size() > 0)
            uploadImgUrls.clear();
        if (uploadVoiceUrls != null && uploadVoiceUrls.size() > 0)
            uploadVoiceUrls.clear();
        if (listener != null) {
            if (!listener.onPostBefore()) {
                return;
            }
        }
        boolean picIsNull = true, voiceIsNull = true;
        if (localPics != null && localPics.size() != 0) {
            picIsNull = false;
            //上传图片
            for (int i = 0; i < localPics.size(); i++) {
                Request.uploadFileToCrm(getContext(), new File(localPics.get(i).getCompressPath()), POST_IMAGE,this);
            }
        }

        if (voices != null && voices.size() > 0) {
            voiceIsNull = false;
            //上传附件
            uploadvoicePos = 0;
            Request.uploadFileToCrm(getContext(), new File(voices.get(0).getPath()), POST_VOICE, this);
        }

        //如果没有任何的文件上传
        if (picIsNull && picIsNull) {
            if (listener != null) {
                if (location != null) {
                    List<AttachDto> attachDtos = new ArrayList<>();
                    if (location != null) {
                        AttachDto attachDto = new AttachDto();
                        attachDto.setLocation(location);
                        attachDto.setAttachType("4");
                        attachDtos.add(attachDto);
                    }
                    listener.onPostAfter(attachDtos);
                } else {
                    listener.onPostAfter(null);
                }
            }
        }
    }
}

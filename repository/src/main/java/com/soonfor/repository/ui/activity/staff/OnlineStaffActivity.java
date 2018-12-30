package com.soonfor.repository.ui.activity.staff;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.soonfor.repository.R;


import com.soonfor.repository.adapter.knowledge.OnlineStaffAdapter;
import com.soonfor.repository.base.RepBaseActivity;
import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.StaffMsg;
import com.soonfor.repository.presenter.seekhelp.OnlineServicePresenter;
import com.soonfor.repository.tools.MyToast;
import com.soonfor.repository.tools.Tokens;
import com.soonfor.repository.tools.floatactionbutton.DraggableFloatingButton;
import com.soonfor.repository.tools.speech.SpeechUtil;
import com.soonfor.repository.view.seekhelp.IOnlinServiceView;

import java.util.ArrayList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;
import cn.jzvd.JZVideoPlayer;

/**
 * 作者：DC-DingYG on 2018-06-20 14:07
 * 邮箱：dingyg012655@126.com
 * 在线客服
 */
public class OnlineStaffActivity extends RepBaseActivity<OnlineServicePresenter>
        implements IOnlinServiceView, SpeechUtil.Result,View.OnClickListener {

    TextView tvfToArtificialStaff;
    RecyclerView mRecyclerView;
    ImageView imgfBack;
    ImageView imgfLeft;
    RelativeLayout rlfInputMsg;
    EditText etfInputMsg;
    Button bnfSend;
    DraggableFloatingButton fab;
    private List<StaffMsg> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private OnlineStaffAdapter adapter;
    private String content;
    SpeechUtil speechUtil;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_onlinestaff;
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }
    private void findViewById() {
        imgfBack = this.findViewById(R.id.ivfLeft);
        imgfBack.setOnClickListener(this);
        tvfToArtificialStaff = this.findViewById(R.id.tvfRight);
        tvfToArtificialStaff.setOnClickListener(this);
        mRecyclerView = this.findViewById(R.id.msg_recycler_view);
        imgfLeft = this.findViewById(R.id.imgfLeft);
        rlfInputMsg = this.findViewById(R.id.rlfInputMsg);
        etfInputMsg = this.findViewById(R.id.etfInputMsg);
        bnfSend = this.findViewById(R.id.mbfSend);
        fab = this.findViewById(R.id.fab);
        imgfLeft.setOnClickListener(this);
        bnfSend.setOnClickListener(this);
    }
    @Override
    protected OnlineServicePresenter initPresenter() {
        QMUIStatusBarHelper.translucent(this);// 沉浸式状态栏
        findViewById();
        presenter = new OnlineServicePresenter(this);
        return presenter;
    }

    @Override
    protected void initViews() {
        speechUtil = SpeechUtil.getInstense(this);
        speechUtil.initSpeech("5b7f7ef7");//app在科大讯飞上的标识id，不可更改
        imgfLeft.setTag(R.id.staff_image, 0);//默认为输入状态
        rlfInputMsg.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        initMsgs();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new OnlineStaffAdapter(this, msgList);
        msgRecyclerView.setAdapter(adapter);
        etfInputMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (s.toString().trim().length() > 0) {
                        bnfSend.setEnabled(true);
                    } else {
                        bnfSend.setEnabled(false);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        showLoadingDialog();
        presenter.getArtificialStaffPath(this);
    }

    private View.OnLongClickListener longclickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            openSpeech();
            return false;
        }
    };


    private void sendMsg(int status, String content) {
        if (!content.equals("")) {
            StaffMsg msg = new StaffMsg(StaffMsg.TYPE_SENT, content);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1);
            msgRecyclerView.scrollToPosition(msgList.size() - 1);
            presenter.getAnswer(this, content);
            if (status == 0) {
                etfInputMsg.setText("");
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etfInputMsg, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(etfInputMsg.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    private void initMsgs() {
        if (msgList == null) {
            msgList = new ArrayList<>();
        }
        StaffMsg msg1 = new StaffMsg(StaffMsg.TYPE_RECEIVED, "Hi,我是您的智能助手小i。请告诉我您的问题哦～");
        msgList.add(msg1);
    }

    @Override
    public void showSearchKnowLedge(boolean isSuccess, RepPageTurn pageTurn, ArrayList<KnowledgeBean> beans, String msg) {
        if (isSuccess && beans != null && beans.size() > 0) {
            StaffMsg smag = new StaffMsg(StaffMsg.TYPE_RECEIVED, "小i已为您找到以下答案，有疑问可再次问我哦～");
            smag.setKbList(beans);
            msgList.add(smag);
            adapter.notifyDataSetChanged(msgList);
        } else {
            StaffMsg smag = null;
            if (msg == null || msg.equals(""))
                smag = new StaffMsg(StaffMsg.TYPE_RECEIVED, "没有搜索到您要的答案，请转“人工客服”咨询～");
            else if (msg.contains("WLAN和移动网络均未连接"))
                smag = new StaffMsg(StaffMsg.TYPE_RECEIVED, "亲，您的网络已断开了～");
            else {
                smag = new StaffMsg(StaffMsg.TYPE_RECEIVED, "网络异常，小i找不到您要的结果～");
                NLogger.e("在线客服:", msg);
            }
            smag.setKbList(null);
            msgList.add(smag);
            adapter.notifyDataSetChanged(msgList);
        }
        msgRecyclerView.scrollToPosition(msgList.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Tokens.Knowledge.CLICK_TO_DETAIL:

                    break;
            }
        }
    }

    private void openSpeech() {
        PermissionsUtil.requestPermission(
                OnlineStaffActivity.this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permissions) {
                        MyToast.showToast(OnlineStaffActivity.this, "请开始说话！");
                        speechUtil.startSpeechDialog(OnlineStaffActivity.this);
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permissions) {
                    }
                }, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void setResult(String reuslt) {
        sendMsg(1, reuslt);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ivfLeft) {
            finish();

        } else if (i == R.id.tvfRight) {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    startNewAct(ArtificialStaffActivity.class);
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {

                }
            }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else if (i == R.id.imgfLeft) {
            int status = (int) view.getTag(R.id.staff_image);
            if (status == 0) {
                imgfLeft.setImageResource(R.mipmap.keyboard_icon);
                imgfLeft.setTag(R.id.staff_image, 1);
                rlfInputMsg.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab.setClickable(false);
                fab.setLongClickable(true);
                fab.setOnLongClickListener(longclickListener);
            } else if (status == 1) {
                imgfLeft.setImageResource(R.mipmap.voice2_icon);
                imgfLeft.setTag(R.id.staff_image, 0);
                rlfInputMsg.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
            }

//            case R.id.ibfTask:
//
//                break;
        } else if (i == R.id.mbfSend) {
            if (etfInputMsg != null && etfInputMsg.getText() != null)
                sendMsg(0, etfInputMsg.getText().toString().trim());

        }
    }
}

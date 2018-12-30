package com.soonfor.measuremanager.me.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;
import com.soonfor.measuremanager.home.login.bean.CurrentUserBean;
import com.soonfor.evaluate.bean.EvaluateRankingBean;
import com.soonfor.measuremanager.tools.ImageUtil;
import com.soonfor.measuremanager.tools.PreferenceUtil;
import com.soonfor.measuremanager.view.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：DC-DingYG on 2018-10-17 13:57
 * 邮箱：dingyg012655@126.com
 */
public class MeEvaluateRankView extends LinearLayout {
    private Context mContext;
    @BindView(R.id.left_avatar)
    RoundImageView leftAvatar;
    @BindView(R.id.tv_left_ranking)
    TextView tvLeftRanking;
    @BindView(R.id.tvfLeftGoodRate)
    TextView tvfLeftGoodRate;
    @BindView(R.id.tvfLeftGrade)
    TextView tvfLeftGrade;
    @BindView(R.id.me_avatar)
    RoundImageView middleAvatar;
    @BindView(R.id.tv_me_ranking)
    TextView tvMiddleRanking;
    @BindView(R.id.tvfMiddleGoodRate)
    TextView tvfMiddleGoodRate;
    @BindView(R.id.tvfMiddleGrade)
    TextView tvfMiddleGrade;
    @BindView(R.id.right_avatar)
    RoundImageView rightAvatar;
    @BindView(R.id.tv_right_ranking)
    TextView tvRightRanking;
    @BindView(R.id.tvfRightGoodRate)
    TextView tvfRightGoodRate;
    @BindView(R.id.tvfRightGrade)
    TextView tvfRightGrade;
    @BindView(R.id.rl_rank1)
    RelativeLayout rlRank1;
    @BindView(R.id.rl_rank2)
    RelativeLayout rlRank2;
    @BindView(R.id.rl_rank3)
    RelativeLayout rlRank3;
    @BindView(R.id.leftKuan)
    ImageView leftKuan;
    @BindView(R.id.middleKuan)
    ImageView middleKuan;
    @BindView(R.id.rightKuan)
    ImageView rightKuan;

    @BindView(R.id.tvfLeftName)
    TextView tvfLeftName;
    @BindView(R.id.tvfMiddleName)
    TextView tvfMiddleName;
    @BindView(R.id.tvfRightName)
    TextView tvfRightName;

    public MeEvaluateRankView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.me_evaluate_ranking, this);
        ButterKnife.bind(this, view);
    }
    int position = -1;
    //初始化
    public void initEvaluateRankingView(final Activity mContext, final List<EvaluateRankingBean> list, SetMainView smView) {
        this.mContext = mContext;
        if (list != null && list.size() > 0) {
            rlRank2.setVisibility(View.VISIBLE);
            CurrentUserBean userBean = PreferenceUtil.getCurrentUserBean();
            position = -1;
            if(userBean!=null && !userBean.getUserId().equals("")){
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).getUserId().equals(userBean.getUserId())){
                        position = i;
                        break;
                    }
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(list.size()==1){
                                rlRank2.setVisibility(VISIBLE);
                                rlRank1.setVisibility(INVISIBLE);
                                rlRank3.setVisibility(INVISIBLE);
                                tvMiddleRanking.setText(getRanksort(list, 0));
                                tvfMiddleName.setText(list.get(0).getUserName());
                                tvfMiddleGoodRate.setText(list.get(0).getGoodRank() + "%");
                                tvfMiddleGrade.setText(list.get(0).getFeedbackAvg() + "分");
                                ImageUtil.loadImageWithCache(mContext, list.get(0).getFheadpic(), middleAvatar, R.drawable.user2, true);
                            }else if(list.size()==2){
                                rlRank1.setVisibility(VISIBLE);
                                rlRank2.setVisibility(VISIBLE);
                                rlRank3.setVisibility(INVISIBLE);
                                tvLeftRanking.setText(getRanksort(list, 0));
                                tvfLeftName.setText(list.get(0).getUserName());
                                tvfLeftGoodRate.setText(list.get(0).getGoodRank() + "%");
                                tvfLeftGrade.setText(list.get(0).getFeedbackAvg() + "分");
                                tvMiddleRanking.setText(getRanksort(list, 1));
                                tvfMiddleName.setText(list.get(1).getUserName());
                                tvfMiddleGoodRate.setText(list.get(1).getGoodRank() + "%");
                                tvfMiddleGrade.setText(list.get(1).getFeedbackAvg() + "分");
                                ImageUtil.loadImageWithCache(mContext, list.get(0).getFheadpic(), leftAvatar, R.drawable.user2, true);
                                ImageUtil.loadImageWithCache(mContext, list.get(1).getFheadpic(), middleAvatar, R.drawable.user2, true);
                            }else if(list.size() >= 3){
                                rlRank1.setVisibility(VISIBLE);
                                rlRank2.setVisibility(VISIBLE);
                                rlRank3.setVisibility(VISIBLE);
                                tvLeftRanking.setText(getRanksort(list, 0));
                                tvfLeftName.setText(list.get(0).getUserName());
                                tvfLeftGoodRate.setText(list.get(0).getGoodRank() + "%");
                                tvfLeftGrade.setText(list.get(0).getFeedbackAvg() + "分");
                                tvMiddleRanking.setText(getRanksort(list, 1));
                                tvfMiddleName.setText(list.get(1).getUserName());
                                tvfMiddleGoodRate.setText(list.get(1).getGoodRank() + "%");
                                tvfMiddleGrade.setText(list.get(1).getFeedbackAvg() + "分");
                                tvRightRanking.setText(getRanksort(list, 2));
                                tvfRightName.setText(list.get(2).getUserName());
                                tvfRightGoodRate.setText(list.get(2).getGoodRank() + "%");
                                tvfRightGrade.setText(list.get(2).getFeedbackAvg() + "分");
                                ImageUtil.loadImageWithCache(mContext, list.get(0).getFheadpic(), leftAvatar, R.drawable.user2, true);
                                ImageUtil.loadImageWithCache(mContext, list.get(1).getFheadpic(), middleAvatar, R.drawable.user2, true);
                                ImageUtil.loadImageWithCache(mContext, list.get(2).getFheadpic(), rightAvatar, R.drawable.user2, true);
                            }
                        }
                    });
                }
            }).start();
            if(position>=0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(smView!=null) {
                                        smView.callback(list.get(position));
                                    }
                                    switch (position){
                                        case 0:
                                            leftKuan.setVisibility(VISIBLE);
                                            middleKuan.setVisibility(INVISIBLE);
                                            rightKuan.setVisibility(INVISIBLE);
                                            break;
                                        case 1:
                                            leftKuan.setVisibility(INVISIBLE);
                                            middleKuan.setVisibility(VISIBLE);
                                            rightKuan.setVisibility(INVISIBLE);
                                            break;
                                        case 2:
                                            leftKuan.setVisibility(INVISIBLE);
                                            middleKuan.setVisibility(INVISIBLE);
                                            rightKuan.setVisibility(VISIBLE);
                                            break;
                                    }
                                }
                            });
                        }
                    }).start();
            }
        }
    }

    private String getRanksort(List<EvaluateRankingBean> list, int position){
        if(!list.get(position).getRankSort().equals("")){
            return "第" + list.get(position).getRankSort() + "名";
        }else
            return "";
    };

    public interface SetMainView{
        void callback(EvaluateRankingBean currtBean);
    }
}

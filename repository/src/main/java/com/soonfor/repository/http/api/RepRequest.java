package com.soonfor.repository.http.api;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;
import com.soonfor.repository.http.httptools.RepAsyncUtils;
import com.soonfor.repository.model.knowledge.FileBean;
import com.soonfor.repository.tools.DataTools;
import com.soonfor.repository.tools.Tokens;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.soonfor.repository.http.api.RepRequest.Knowledge.GET_SYSCODE;
import static com.soonfor.repository.http.api.RepRequest.Knowledge.GET_SYSCODE_JIMSOCKET;
import static com.soonfor.repository.http.api.RepRequest.Knowledge.MYKNOWLEDGEPAGE;
import static com.soonfor.repository.http.api.RepRequest.Knowledge.SEARCHKNOWLEDGE;
import static com.soonfor.repository.http.api.RepRequest.Knowledge.SEARCHTITLE;
import static com.soonfor.repository.http.api.RepRequest.Personal.DEL_FAVORITE;
import static com.soonfor.repository.http.api.RepRequest.Personal.GET_MYFAVORITE;

/**
 * 作者：DC-DingYG on 2018-05-02 15:37
 * 邮箱：dingyg012655@126.com
 */
public class RepRequest {

    /**
     * 知识
     */
    public static class Knowledge {
        public static final int GET_CATEGORY_LIST = 101;
        public static final int GET_HOT_LIST = 102;
        public static final int GET_KNOWLEDGE_LIST = 103;
        public static final int GET_KNOWLEDGE_DETAIL = 104;
        public static final int ADDVIEWCOUNT = 105;
        public static final int GET_COMMENT_LIST = 106;
        public static final int SAVE_COMMENT = 107;
        public static final int SAVE_COMMENTREPLY = 108;
        public static final int COLLECT = 109;
        public static final int LIKE = 110;
        public static final int AddKNOWLEDGE = 111;
        public static final int SEARCHTITLE = 201;
        public static final int SEARCHKNOWLEDGE = 202;
        public static final int MYKNOWLEDGEPAGE = 114;
        public static final int GET_SYSCODE = 1504;
        public static final int GET_SYSCODE_JIMSOCKET = 1505;

        /**
         * 请求分类列表
         */
        public static void getCategoryList(Context context, RepAsyncUtils.AsyncCallback callback) {
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.GET_CATEGORY, "", GET_CATEGORY_LIST, callback);
        }

        /**
         * 请求热门列表
         *
         * @param context
         * @param pageno
         * @param callback
         */
        public static void getHotList(Context context, int pageno, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("pageSize", "10");
                jsonObject.put("pageNo", pageno + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.GET_HOTLIST, jsonObject.toString(), GET_HOT_LIST, callback);
        }

        /**
         * 请求知识列表
         *
         * @param context
         * @param sIds     categoryIds为二级目录的id集合，如选择一级目录，则传该一级目录下所有二级目录id集合，如选择二级目录，则把该二级目录id放进集合传送。
         * @param callback
         */
        public static void getKnowledgeList(Context context, JSONArray sIds, int pageno, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("categoryIds", sIds);
                //jsonObject.put("pageSize", "10");
                jsonObject.put("pageNo", pageno + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            RequestParams params = new RequestParams();
//            params.put("categoryIds",sIds);
//            params.put("pageNo", 1 + "");
//           // params.put("pageSize", "10");
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.GET_KNOWLEDGELIST, jsonObject.toString(), GET_KNOWLEDGE_LIST, callback);
        }

        /**
         * 请求知识详情
         *
         * @param context
         * @param _id
         * @param callback
         */
        public static void getDetailData(Context context, String _id, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", _id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.GET_KNOWLEDGEDETAIL, jsonObject.toString(), GET_KNOWLEDGE_DETAIL, callback);
        }

        /**
         * 增加浏览数量
         *
         * @param context
         * @param _id
         * @param callback
         */
        public static void addViewCount(Context context, String _id, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", _id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.ADDVIEWCOUNT, jsonObject.toString(), ADDVIEWCOUNT, callback);
        }

        /**
         * 请求评论列表
         *
         * @param context
         * @param _id
         * @param pageno
         * @param callback
         */
        public static void getCommentList(Context context, String _id, String pageno, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("knowledgeId", _id);
                jsonObject.put("pageNo", pageno);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.GET_COMMENTLIST, jsonObject.toString(), GET_COMMENT_LIST, callback);
        }

        /**
         * 保存评论回复
         *
         * @param context
         * @param commId
         * @param replyuserId
         * @param content
         * @param callback
         */
        public static void ReplyToComment(Context context, String commId, String replyuserId, String content, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("content", content);
                jsonObject.put("commentId", commId);
                jsonObject.put("replyUserId", replyuserId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.SAVE_COMMENTREPLY, jsonObject.toString(), SAVE_COMMENTREPLY, callback);
        }

        /**
         * 保存评论
         *
         * @param context
         * @param knowledgeId
         * @param content
         * @param callback
         */
        public static void SaveComment(Context context, String knowledgeId, String content, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("content", content);
                jsonObject.put("knowledgeId", knowledgeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(context, RepUserInfo.Knowledge.SAVE_COMMENT, jsonObject.toString(), SAVE_COMMENT, callback);
        }

        /**
         * 收藏
         *
         * @param cxt
         * @param knowledgeId
         * @param callback
         */
        public static void Collect(Context cxt, String knowledgeId, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", knowledgeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(cxt, RepUserInfo.Knowledge.COLLECT, jsonObject.toString(), COLLECT, callback);
        }

        /**
         * 点赞
         *
         * @param cxt
         * @param knowledgeId
         * @param callback
         */
        public static void Like(Context cxt, String knowledgeId, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", knowledgeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(cxt, RepUserInfo.Knowledge.LIKE, jsonObject.toString(), LIKE, callback);
        }

        /**
         * 新增知识
         *
         * @param cxt
         * @param categoryId
         * @param title
         * @param content
         * @param fileList
         */
        public static void AddKnowLedge(Context cxt, String categoryId, String title, String content, ArrayList<FileBean> fileList, RepAsyncUtils.AsyncCallback callback) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("categoryId", categoryId);
                jsonObject.put("title", title);
                jsonObject.put("content", content);
                jsonObject.put("fileList", fileList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RepAsyncUtils.post(cxt, RepUserInfo.Knowledge.ADDKNOWLEDGE, jsonObject.toString(), AddKNOWLEDGE, callback);
        }
    }

    /**
     * 求助
     */
    public static class Seekhelp {
        public static final int GET_TOPKNOWLEDGE = 201;

        /**
         * 获取求助示例列表（浏览量前25名）
         */
        public static void getSeekhelpList(Context context, RepAsyncUtils.AsyncCallback callback) {
            RepAsyncUtils.post(context, RepUserInfo.Seekhelp.GET_TOPKNOWLEDGE, "", GET_TOPKNOWLEDGE, callback);
        }
    }

    /**
     * 个人中心
     */
    public static class Personal {

        public static final int GET_PERSONALINFO = 301;
        public static final int GET_MYFAVORITE = 112;
        public static final int DEL_FAVORITE = 113;

        /**
         * 获取个人信息
         */
        public static void getPersonalInfo(Context context, RepAsyncUtils.AsyncCallback callback) {
            RepAsyncUtils.get(context, RepUserInfo.Personal.GET_PERSONALINFO, GET_PERSONALINFO, callback);
        }

    }

    /**
     * 打开网络文件
     */
    public static final int OPEN_FILE_BY_URL = 11;

    public static void openFileByUrl(Context context, String imagPath, RepAsyncUtils.AsyncCallback callback) {
        String fullUrl = Hawk.get(DataTools.ServerAdr);
        if (fullUrl != null && !fullUrl.equals("")) {
            fullUrl = fullUrl.substring(0, fullUrl.lastIndexOf(":")) + imagPath;
            RepAsyncUtils.get(context, fullUrl, OPEN_FILE_BY_URL, callback);
        }
    }

    public static final int UPLOADPIC = 111;

    /**
     * 上传文件
     */
    public static void uploadFileToCrm(Context context, String fileType, File imgfile, RepAsyncUtils.AsyncCallback2 callback) {
        RequestParams params = new RequestParams();
        try {
            params.put("type", "1");
            params.put("file", imgfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.uploadFileToCrm(context, fileType, Hawk.get(Tokens.SP_UPLOADCNETERPATH, "") + RepUserInfo.UPLOAD_FILE_TO_CRM, params, UPLOADPIC, callback);
    }

    public static void searchTitle(Context context, String keyword, RepAsyncUtils.AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", keyword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.post(context, RepUserInfo.Knowledge.SEARCHTITLE, jsonObject.toString(), SEARCHTITLE, callback);
    }

    public static void searchKnowledge(Context context, String keyword, int pageNo, int pageSize, RepAsyncUtils.AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keyword", keyword);
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.post(context, RepUserInfo.Knowledge.SEARCHKNOWLEDGE, jsonObject.toString(), SEARCHKNOWLEDGE, callback);
    }

    public static void getMyFavorite(Context context, int pageNo, int pageSize, RepAsyncUtils.AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.post(context, RepUserInfo.Personal.GET_MYCOLLECTIONS, jsonObject.toString(), GET_MYFAVORITE, callback);
    }

    public static void delMyFavorite(Context context, ArrayList<String> ids, RepAsyncUtils.AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jrids = new JSONArray();
            for (String s : ids) {
                jrids.put(s);
            }
            jsonObject.put("ids", jrids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.post(context, RepUserInfo.Personal.DELETECOLLECTIONS, jsonObject.toString(), DEL_FAVORITE, callback);
    }

    public static void getMyKnowLedgePage(Context context, int pageNo, int pageSize, RepAsyncUtils.AsyncCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RepAsyncUtils.post(context, RepUserInfo.Personal.GET_MYINCREASEDKNOWLEDGELIST, jsonObject.toString(), MYKNOWLEDGEPAGE, callback);
    }

    public static void getSysCode(Context cxt, String code, RepAsyncUtils.AsyncCallback callback) {
        RepAsyncUtils.get(cxt, RepUserInfo.Knowledge.GETSYSCODE + "?code=" + code, GET_SYSCODE, callback);
    }

    public static void getSysCodeJimSocket(Context cxt, RepAsyncUtils.AsyncCallback callback) {
        RepAsyncUtils.get(cxt, RepUserInfo.Knowledge.GETSYSCODE + "?code=jimSocket", GET_SYSCODE_JIMSOCKET, callback);
    }
}

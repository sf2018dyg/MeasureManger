package com.soonfor.repository.tools;

import android.content.Intent;
import android.nfc.NfcAdapter;

import com.soonfor.repository.model.RepPageTurn;
import com.soonfor.repository.model.knowledge.KnowledgeBean;
import com.soonfor.repository.model.knowledge.CategoryBean;
import com.soonfor.repository.ui.fragment.KnowledgeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：DC-DingYG on 2018-05-19 8:34
 * 邮箱：dingyg012655@126.com
 */
public class DataTools {

    public static String ServerAdr = "ServerAdr";//接口服務器地址
    public static String UUID = "UUID";
    /**
     * 知识相关口令
     */
    public static class Knowledge{
        public final static int CLICK_TO_DETAIL = 1101;//跳转至知识详情页
        public final static String BUTTON_TYPE = "button_type";
        public final static String EDIT_TEXT = "edit_text";

    }

    // Hex help
    private final static byte[] HEX_CHAR_TABLE = {(byte) '0', (byte) '1',
            (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
            (byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'};

    public static String getHexString(byte[] raw, int len) {
        byte[] hex = new byte[2 * len];
        int index = 0;
        int pos = 0;

        for (byte b : raw) {
            if (pos >= len)
                break;

            pos++;
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }

        return new String(hex);
    }

    public static String getUid(Intent intent) {
        byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        return myNFCID == null ? "" : getHexString(myNFCID, myNFCID.length);
    }

    public static String toInt(String a, int b) {
        long r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (long) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }

    private static int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equalsIgnoreCase(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equalsIgnoreCase("a")) {
            i = 10;
        }
        if (a.equalsIgnoreCase("b")) {
            i = 11;
        }
        if (a.equalsIgnoreCase("c")) {
            i = 12;
        }
        if (a.equalsIgnoreCase("d")) {
            i = 13;
        }
        if (a.equalsIgnoreCase("e")) {
            i = 14;
        }
        if (a.equalsIgnoreCase("f")) {
            i = 15;
        }
        return i;
    }

    /**
     * 知识
     */
    public static List<CategoryBean> fTypes = new ArrayList<>();//一级分类种类
    public static Map<String, List<CategoryBean>> sTypes = new HashMap<>();//二级分类种类

    public static RepPageTurn hotPage = null;//热门页码
    public static ArrayList<KnowledgeBean> hotList = new ArrayList<>();//知识类别数据集合
    public static Map<String,RepPageTurn> pageKlMap = new HashMap<>();//知识页码对象集合
    public static Map<String,ArrayList<KnowledgeBean>> ListKlMap = new HashMap<>();//知识列表数据集合
    public static Map<Integer, Boolean> refreshBoolean = new HashMap<>();//各界面是否需要刷新

    public static void clearAll(){
        if (KnowledgeFragment.fType != null) {
            KnowledgeFragment.fType = null;
        }
        if (KnowledgeFragment.sType != null) {
            KnowledgeFragment.sType = null;
        }
        if(hotPage !=null){
            hotPage = null;
        }
        if (sTypes != null) {
            sTypes.clear();
        }
        if (fTypes != null) {
            fTypes.clear();
        }
        if(hotList !=null){
            hotList.clear();
        }
        if(pageKlMap!=null){
            pageKlMap.clear();
        }
        if(ListKlMap!=null){
            ListKlMap.clear();
        }
        if(refreshBoolean!=null){
            refreshBoolean.clear();
        }
    }
    public static String getMapKey(CategoryBean sType){
        String key = sType.getId();
        if(key.equals("all")){
            key = sType.getParentId();
        }
        return key;
    }
}

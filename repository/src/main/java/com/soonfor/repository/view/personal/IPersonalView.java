package com.soonfor.repository.view.personal;

import com.soonfor.repository.base.IRepBaseView;
import com.soonfor.repository.model.person.PersonInfo;

/**
 * 作者：DC-DingYG on 2018-06-15 11:51
 * 邮箱：dingyg012655@126.com
 */
public interface IPersonalView extends IRepBaseView {
    void refreshInfo(PersonInfo personInfo);
}

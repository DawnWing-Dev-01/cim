package com.dxr.apply.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.apply.service.api.IHabitSay;

/**
 * @description: <流程审核常用意见Action层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class HabitSayAction extends BasalAction {

    private static final long serialVersionUID = 7278431614106702509L;

    @Autowired
    private IHabitSay habitSayMgr;

    /**
     * 获取常用意见列表
     */
    public void getHabitSayList() {
        try {
            String result = habitSayMgr.getHabitSayList();
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("HabitSayAction.getHabitSayList() is errror...", e);
            super.bracket();
        }
    }

    /**
     * 保存常用意见
     */
    public void saveHabitSay() {
        try {
            habitSayMgr.saveHabitSay(object);
        } catch (ServiceException e) {
            logger.error("HabitSayAction.saveHabitSay() is errror...", e);
        }
    }

    /**
     * 删除常用意见
     */
    public void deleteHabitSay() {
        try {
            habitSayMgr.deleteHabitSay(object);
        } catch (ServiceException e) {
            logger.error("HabitSayAction.deleteHabitSay() is errror...", e);
        }
    }
}

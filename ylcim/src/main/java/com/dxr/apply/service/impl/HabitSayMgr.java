package com.dxr.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.HabitSayDao;
import com.dxr.apply.entity.HabitSayInfo;
import com.dxr.apply.service.api.IHabitSay;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;

import freemarker.template.utility.StringUtil;

/**
 * @description: <流程审核常用意见服务接口实现层>
 * @author: w.xL
 * @date: 2018-4-17
 */
public class HabitSayMgr extends BasalMgr<HabitSayInfo> implements IHabitSay {

    @Autowired
    private HabitSayDao habitSayDao;

    @Override
    public HabitSayInfo getDbObject(String id) throws ServiceException {
        return habitSayDao.get(id);
    }

    @Override
    public String getHabitSayList() throws ServiceException {
        List<HabitSayInfo> habitSayList = habitSayDao.getHabitSayList();
        return JSONArray.toJSONString(habitSayList);
    }

    @Override
    public void saveHabitSay(String sayDetail) throws ServiceException {
        if (StringUtils.isEmpty(sayDetail)) {
            return;
        }
        long total = habitSayDao.getTotal(sayDetail);
        if (total > 0) {
            return;
        }

        HabitSayInfo habitSay = new HabitSayInfo();
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        habitSay.setUserId(shiroUser.getUserId());
        habitSay.setSayDetail(sayDetail);
        habitSayDao.save(habitSay);
    }

    @Override
    public void deleteHabitSay(String habitSayId) throws ServiceException {
        habitSayDao.deleteById(habitSayId);
    }

}

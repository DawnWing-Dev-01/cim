package com.dxr.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.FilterBean;
import com.dawnwing.framework.common.HqlSymbol;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.system.dao.RoleDao;
import com.dxr.system.dao.RoleMenuDao;
import com.dxr.system.dao.UserRoleDao;
import com.dxr.system.entity.MenuInfo;
import com.dxr.system.entity.RoleInfo;
import com.dxr.system.service.api.IRole;

/**
 * @author w.xL
 */
public class RoleMgr extends BasalMgr<RoleInfo> implements IRole {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public String getRolePage(int page, int rows, RoleInfo roleInfo)
            throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (ObjectUtils.isNotEmpty(roleInfo)) {
            if (StringUtils.isNotEmpty(roleInfo.getName())) {
                filterList.add(new FilterBean("role.name",
                        HqlSymbol.HQL_EQUAL, roleInfo.getName()));
            }
        }

        List<RoleInfo> roleList = roleDao.getRolePage((page - 1) * rows, rows,
                filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", roleDao.getTotal(filterList));
        result.put("rows", roleList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    @Override
    public String saveRole(RoleInfo roleInfo) throws ServiceException {
        String code = roleInfo.getCode();
        long count = roleDao.findRoleCountByCode(code);
        if (count > 0) {
            throw new ServiceException("角色编码【" + code + "】已存在，请重新输入!",
                    ErrorCode.ERRCODE_DATA_REPEAT);
        }
        roleDao.save(roleInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public Map<String, Set<String>> findResourceMapByUserId(String userId)
            throws ServiceException {
        Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
        Set<String> urlSet = new HashSet<String>();
        Set<String> roles = new HashSet<String>();

        List<String> roleIdList = userRoleDao.selectRoleIds(userId);
        for (String roleId : roleIdList) {
            List<MenuInfo> menuList = roleMenuDao.findMenuListByRoleId(roleId);
            if (menuList != null && !menuList.isEmpty()) {
                for (MenuInfo menuInfo : menuList) {
                    String action = menuInfo.getAction();
                    if (StringUtils.isNotEmpty(action)) {
                        urlSet.add(action);
                    }
                }
            }
            RoleInfo role = roleDao.get(roleId);
            if (role != null) {
                roles.add(role.getId());
            }
        }
        resourceMap.put("urls", urlSet);
        resourceMap.put("roles", roles);
        return resourceMap;
    }

    @Override
    public RoleInfo getDbObject(String id) throws ServiceException {
        return roleDao.get(id);
    }

    @Override
    public String updateRole(RoleInfo roleInfo) throws ServiceException {
        String code = roleInfo.getCode();
        RoleInfo roleDb = roleDao.get(roleInfo.getId());
        if (!code.equals(roleDb.getCode())) {
            Long count = roleDao.findRoleCountByCode(code);
            if (count > 0) {
                throw new ServiceException("角色编码【" + code + "】已存在，请重新输入!",
                        ErrorCode.ERRCODE_DATA_REPEAT);
            }
            roleDb.setCode(code);
        }
        roleDb.setName(roleInfo.getName());
        roleDb.setSort(roleInfo.getSort());
        roleDb.setRemark(roleInfo.getRemark());
        roleDb.setUpdateDate(new Date());
        roleDao.save(roleDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String deleteRole(String roleId) throws ServiceException {
        RoleInfo roleDb = roleDao.get(roleId);
        roleDb.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        roleDao.save(roleDb);
        return new Message("删除成功!").toString();
    }

    @Override
    public String getDetails(String roleId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        RoleInfo roleInfo = roleDao.get(roleId);
        detailsMap.put("roleInfo.id", roleInfo.getId());
        detailsMap.put("roleInfo.name", roleInfo.getName());
        detailsMap.put("roleInfo.code", roleInfo.getCode());
        detailsMap.put("roleInfo.sort", roleInfo.getSort());
        detailsMap.put("roleInfo.status", roleInfo.getStatus());
        detailsMap.put("roleInfo.remark", roleInfo.getRemark());
        detailsMap.put("roleInfo.createDate", roleInfo.getCreateDate());
        detailsMap.put("roleInfo.updateDate", roleInfo.getUpdateDate());
        return JSON.toJSONString(detailsMap);
    }

}

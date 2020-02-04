package com.dxr.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.shiro.SimplePwdHash;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.dao.UserDao;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.service.api.IUser;

/**
 * @author w.xL
 */
public class UserMgr extends BasalMgr<UserInfo> implements IUser {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SimplePwdHash simplePwdHash;

    @Autowired
    private SystemConstantCache scCache;

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#saveUser(com.dxr.system.entity.UserInfo)
     */
    @Override
    public String saveUser(UserInfo userInfo) throws ServiceException {
        // 先保存用户, 保存后才会有用户Id
        String username = userInfo.getUsername();
        long count = userDao.findUserCountByCode(username);
        if (count > 0) {
            throw new ServiceException("账号【" + username + "】已存在，请重新输入!",
                    ErrorCode.ERRCODE_DATA_REPEAT);
        }
        String defaultPwd = String.valueOf(scCache.get("UserDefaultPassword"));
        userInfo.setPassword(defaultPwd);
        userDao.save(userInfo);

        UserInfo userDb = userDao.get(userInfo.getId());
        // 使用md5加密用户密码, 用户id为加密盐值
        String newPwd = simplePwdHash.hashByShiro(userInfo.getPassword(),
                userInfo.getId());
        // 设置加密后的密码
        userDb.setPassword(newPwd);
        userDao.update(userDb);
        return new Message("保存成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#updateUser(com.dxr.system.entity.UserInfo)
     */
    @Override
    public String updateUser(UserInfo userInfo) throws ServiceException {
        String username = userInfo.getUsername();
        UserInfo userDb = userDao.get(userInfo.getId());
        if (!username.equals(userDb.getUsername())) {
            long count = userDao.findUserCountByCode(username);
            if (count > 0) {
                throw new ServiceException("账号【" + username + "】已存在，请重新输入!",
                        ErrorCode.ERRCODE_DATA_REPEAT);
            }
            userDb.setUsername(username);
        }

        userDb.setName(userInfo.getName());
        //userDb.setUsername(username);
        userDb.setOrgId(userInfo.getOrgId());
        userDb.setGender(userInfo.getGender());
        userDb.setBirthday(userInfo.getBirthday());
        userDb.setIphone(userInfo.getIphone());
        userDb.setMobile(userInfo.getMobile());
        userDb.setAddress(userInfo.getAddress());
        userDb.setWechatOpenId(userInfo.getWechatOpenId());
        userDb.setSort(userInfo.getSort());
        userDb.setRemark(userInfo.getRemark());
        userDb.setUpdateDate(new Date());
        userDao.update(userDb);
        return new Message("更新成功！").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#deleteUser(java.lang.String)
     */
    @Override
    public String deleteUser(String userId) throws ServiceException {
        // 逻辑删除
        UserInfo userInfo = userDao.get(userId);
        userInfo.setStatus(ConstGlobal.DATA_STATUS_DELETED);
        userDao.update(userInfo);

        // 清除该用户的身份认证缓存
        SecurityUser.clearCachedAuthenticationInfo(userInfo.getUsername());
        return new Message("删除成功！").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#findUser(com.dxr.system.entity.UserInfo)
     */
    @Override
    public UserInfo findUser(UserInfo userInfo) throws ServiceException {
        return userDao.findUser(userInfo.getUsername());
    }

    @Override
    public List<UserInfo> getAllUserList() throws ServiceException {
        return userDao.getAllUserList();
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#getUserPage(int, int, com.dxr.system.entity.UserInfo)
     */
    @Override
    public String getUserPage(int page, int rows, UserInfo userInfo,
            String orgIndexNum) throws ServiceException {
        List<FilterBean> filterList = new ArrayList<FilterBean>();

        if (ObjectUtils.isNotEmpty(userInfo)) {
            if (StringUtils.isNotEmpty(userInfo.getUsername())) {
                filterList.add(new FilterBean("user.username",
                        HqlSymbol.HQL_EQUAL, userInfo.getUsername()));
            }

            if (StringUtils.isNotEmpty(userInfo.getName())) {
                filterList.add(new FilterBean("user.name", HqlSymbol.HQL_LIKE,
                        "%" + userInfo.getName() + "%"));
            }
        }

        if (StringUtils.isNotEmpty(orgIndexNum)) {
            filterList.add(new FilterBean("org.indexNum", HqlSymbol.HQL_LIKE,
                    orgIndexNum + "%"));
        }

        List<UserInfo> userList = userDao.getUserPage((page - 1) * rows, rows,
                filterList);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", userDao.getTotal(filterList));
        result.put("rows", userList);
        return JSON.toJSONStringWithDateFormat(result, Property.yyyyMMdd);
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IUser#reserPwd(java.lang.String)
     * 重置密码, 恢复为默认密码
     */
    @Override
    public String modifyPwd(String userId) throws ServiceException {
        UserInfo userInfo = userDao.get(userId);
        String defaultPwd = String.valueOf(scCache.get("UserDefaultPassword"));
        String newPwd = simplePwdHash.hashByShiro(defaultPwd, userId);
        userInfo.setPassword(newPwd);
        userDao.update(userInfo);

        // 清除该用户的身份认证缓存
        SecurityUser.clearCachedAuthenticationInfo(userInfo.getUsername());
        return new Message("重置成功!").toString();
    }

    @Override
    public String getDetails(String userId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();
        UserInfo userInfo = userDao.get(userId);
        detailsMap.put("userInfo.id", userInfo.getId());
        detailsMap.put("userInfo.name", userInfo.getName());
        detailsMap.put("userInfo.username", userInfo.getUsername());
        detailsMap.put("userInfo.birthday", userInfo.getBirthday());
        detailsMap.put("userInfo.gender", userInfo.getGender());
        detailsMap.put("userInfo.orgId", userInfo.getOrgId());
        detailsMap.put("userInfo.iphone", userInfo.getIphone());
        detailsMap.put("userInfo.mobile", userInfo.getMobile());
        detailsMap.put("userInfo.address", userInfo.getAddress());
        detailsMap.put("userInfo.wechatOpenId", userInfo.getWechatOpenId());
        detailsMap.put("userInfo.sort", userInfo.getSort());
        detailsMap.put("userInfo.remark", userInfo.getRemark());
        return JSON.toJSONStringWithDateFormat(detailsMap, Property.yyyyMMdd);
    }

    @Override
    public UserInfo getDbObject(String id) throws ServiceException {
        return userDao.get(id);
    }

    @Override
    public String modifyPwd(UserInfo userInfo, String newPwd)
            throws ServiceException {
        String userId = userInfo.getId();
        UserInfo userDb = userDao.get(userId);
        String oldPwd = userInfo.getPassword();
        String hashOldPwd = simplePwdHash.hashByShiro(oldPwd, userId);
        if (!hashOldPwd.equals(userDb.getPassword())) {
            throw new ServiceException("您输入的原密码不正确，请重新输入！");
        }

        String hashNewPwd = simplePwdHash.hashByShiro(newPwd, userId);
        userDb.setPassword(hashNewPwd);
        userDao.update(userDb);

        // 清除该用户的身份认证缓存
        SecurityUser.clearCachedAuthenticationInfo(userDb.getUsername());
        return new Message("修改成功").toString();
    }

    @Override
    public List<UserInfo> findUserListForRoleId(String roleId)
            throws ServiceException {
        return userDao.findUserListForRoleId(roleId);
    }
}
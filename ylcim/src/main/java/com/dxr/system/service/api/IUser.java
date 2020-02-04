package com.dxr.system.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.UserInfo;

/**
 * @author w.xL
 */
public interface IUser extends IBasalMgr<UserInfo> {

    /**
     * @param userInfo
     * @return
     * @throws ServiceException
     */
    public String saveUser(UserInfo userInfo) throws ServiceException;

    /**
     * @param userInfo
     * @return
     * @throws ServiceException
     */
    public String updateUser(UserInfo userInfo) throws ServiceException;

    /**
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String deleteUser(String userId) throws ServiceException;

    /**
     * @param userInfo
     * @return
     * @throws ServiceException
     */
    public UserInfo findUser(UserInfo userInfo) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     */
    public List<UserInfo> getAllUserList() throws ServiceException;

    /**
     * @param page
     * @param rows
     * @param userInfo
     * @param orgIndexNum
     * @return
     * @throws ServiceException
     */
    public String getUserPage(int page, int rows, UserInfo userInfo,
            String orgIndexNum) throws ServiceException;

    /**
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String modifyPwd(String userId) throws ServiceException;

    /**
     * 获取详细信息
     * @param userId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String userId) throws ServiceException;

    /**
     * 修改密码
     * @param userInfo
     * @param newPwd
     * @return
     * @throws ServiceException
     */
    public String modifyPwd(UserInfo userInfo, String newPwd)
            throws ServiceException;

    /**
     * 获取用户该角色的用户
     * @param roleId
     * @return
     * @throws ServiceException
     */
    public List<UserInfo> findUserListForRoleId(String roleId)
            throws ServiceException;
}

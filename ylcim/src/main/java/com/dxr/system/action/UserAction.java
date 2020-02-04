package com.dxr.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ErrorCode;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.UserInfo;
import com.dxr.system.service.api.IUser;

/**
 * @author w.xL
 */
public class UserAction extends BasalAction {

    private static final long serialVersionUID = 7882681057584921751L;

    @Autowired
    private IUser userMgr;

    private UserInfo userInfo;

    private List<UserInfo> userList;

    /**
     * 表单视图
     */
    @Override
    public String formView() {
        super.putViewDeftData("genderMale", ConstGlobal.USER_GENDER_MALE);
        super.putViewDeftData("genderFemale", ConstGlobal.USER_GENDER_FEMALE);
        if ("showInfo".equals(viewType)) {
            return "showInfoView";
        }
        if ("choose".equals(viewType)) {
            return "chooseView";
        }
        return super.formView();
    }

    /**
     * 获取用户列表, EasyUI格式
     */
    public void getUserPage() {
        try {
            String result = userMgr.getUserPage(page, rows, userInfo, object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("saveUser.getUserPage() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 重置密码
     */
    public void resetPwd() {
        try {
            String result = userMgr.modifyPwd(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("saveUser.resetPwd() is error...", e);
            super.operateException();
        }
    }

    /**
     * 修改密码
     */
    public void modifyPwd() {
        try {
            String result = userMgr.modifyPwd(userInfo, object);
            super.writeToView(result);
        } catch (ServiceException e) {
            super.writeToView(new Message(false,
                    ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                    .toString());
        }
    }

    /**
     * 添加用户
     * @return
     */
    public void saveUser() {
        try {
            String result = userMgr.saveUser(userInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserAction.saveUser() user is error...", e);
            if (e.getErrorCode() == ErrorCode.ERRCODE_DATA_REPEAT) {
                super.writeToView(new Message(false,
                        ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                        .toString());
                return;
            }
            super.operateException();
        }
    }

    /**
     * 更新用户
     * @return
     */
    public void updateUser() {
        try {
            String result = userMgr.updateUser(userInfo);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserAction.updateUser() user is error...", e);
            if (e.getErrorCode() == ErrorCode.ERRCODE_DATA_REPEAT) {
                super.writeToView(new Message(false,
                        ConstGlobal.MESSAGE_TYPE_WARNING, e.getMessage())
                        .toString());
                return;
            }
            super.operateException();
        }
    }

    /**
     * 删除用户
     * @return
     */
    public void deleteUser() {
        try {
            String result = userMgr.deleteUser(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserAction.deleteUser() user is error...", e);
            super.operateException();
        }
    }

    /**
     * 查看详情
     */
    public void getDetails() {
        try {
            String result = userMgr.getDetails(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("UserAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }
}

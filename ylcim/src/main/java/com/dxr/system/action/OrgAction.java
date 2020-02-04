package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.Organization;
import com.dxr.system.service.api.IOrg;

/**
 * @author w.xL
 */
public class OrgAction extends BasalAction {

    private static final long serialVersionUID = -6249727038952404208L;

    @Autowired
    private IOrg orgMgr;

    private Organization orgInfo;

    /**
     * 展示组织机构树表格
     */
    public void getTreeGrid() {
        try {
            String result = orgMgr.getTreeGrid();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("./OrgAction!getTreeGrid is error...", e);
            super.empty();
        }
    }

    /**
     * 展示组织机构树
     */
    public void getTree() {
        try {
            String result = orgMgr.getTree();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("./OrgAction!getTree is error...", e);
            super.empty();
        }
    }

    /**
     * 保存组织机构
     */
    public void saveOrgan() {
        try {
            String result = orgMgr.saveOrgan(orgInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("./OrgAction!saveOrgan is error...", e);
            super.brace();
        }
    }

    /**
     * 更新组织机构
     */
    public void updateOrgan() {
        try {
            String result = orgMgr.updateOrgan(orgInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("./OrgAction!updateOrgan is error...", e);
            super.brace();
        }
    }

    /**
     * 删除组织机构
     */
    public void deleteOrgan() {
        try {
            String result = orgMgr.deleteOrgan(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("./OrgAction!deleteOrgan is error...", e);
            super.error(e.getMessage());
        }
    }

    /**
     * 获取组织机构详细
     */
    public void getDetails() {
        try {
            String result = orgMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("./OrgAction.getDetails() is error...", e);
            super.brace();
        }
    }

    public Organization getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(Organization orgInfo) {
        this.orgInfo = orgInfo;
    }
}

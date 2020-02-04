package com.dxr.system.service.api;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.system.entity.Organization;

/**
 * @author w.xL
 */
public interface IOrg extends IBasalMgr<Organization> {

    /**
     * @return
     * @throws ServiceException
     */
    public String getTreeGrid() throws ServiceException;

    /**
     * @param orgInfo
     * @return
     * @throws ServiceException
     */
    public String saveOrgan(Organization orgInfo) throws ServiceException;

    /**
     * @param orgInfo
     * @return
     * @throws ServiceException
     */
    public String updateOrgan(Organization orgInfo) throws ServiceException;

    /**
     * @param orgId
     * @param isLeaf
     * @return
     * @throws ServiceException
     */
    public String updateIsLeaf(String orgId, Integer isLeaf)
            throws ServiceException;

    /**
     * @param orgId
     * @return
     * @throws ServiceException
     */
    public String deleteOrgan(String orgId) throws ServiceException;

    /**
     * 查询详情
     * @param orgId
     * @return
     * @throws ServiceException
     */
    public String getDetails(String orgId) throws ServiceException;

    /**
     * 获取树结构
     * @return
     * @throws ServiceException
     */
    public String getTree() throws ServiceException;

    /**
     * 获取组织机构根据名称
     * @param name
     * @return
     * @throws ServiceException
     */
    public Organization getOrganizationByName(String name)
            throws ServiceException;
}

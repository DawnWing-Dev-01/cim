package com.dxr.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.impl.BasalMgr;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.dao.DealerDao;
import com.dxr.system.dao.OrgDao;
import com.dxr.system.dao.UserDao;
import com.dxr.system.entity.Organization;
import com.dxr.system.service.api.IOrg;

/**
 * @author w.xL
 */
public class OrgMgr extends BasalMgr<Organization> implements IOrg {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private UserDao userDao;

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IOrg#getTreeGrid()
     */
    @Override
    public String getTreeGrid() throws ServiceException {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        List<Organization> orgList = orgDao.getChildren(null);
        Map<String, Object> treeMap = null;
        for (Organization orgInfo : orgList) {
            treeMap = new HashMap<String, Object>();

            treeMap.put("id", orgInfo.getId());
            treeMap.put("name", orgInfo.getName());
            treeMap.put("code", orgInfo.getCode());
            treeMap.put("indexNum", orgInfo.getIndexNum());
            treeMap.put("sort", orgInfo.getSort());
            treeMap.put("iconCls", orgInfo.getIcon());
            treeMap.put("remark", orgInfo.getRemark());
            treeMap.put("isInit", orgInfo.getIsInit());
            treeMap.put("status", orgInfo.getStatus());
            // 获取孩子节点
            List<Map<String, Object>> childrenList = this.getChildren(orgInfo);
            treeMap.put("children", childrenList);

            treeList.add(treeMap);
        }

        return JSON.toJSONString(treeList);
    }

    /**
     * @param fatherOrgInfo
     * @return
     */
    private List<Map<String, Object>> getChildren(Organization fatherOrgInfo) {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        if (fatherOrgInfo == null) {
            return treeList;
        }

        List<Organization> orgList = orgDao.getChildren(fatherOrgInfo.getId());
        Map<String, Object> treeMap = null;
        for (Organization orgInfo : orgList) {
            treeMap = new HashMap<String, Object>();
            treeMap.put("id", orgInfo.getId());
            treeMap.put("name", orgInfo.getName());
            treeMap.put("code", orgInfo.getCode());
            treeMap.put("indexNum", orgInfo.getIndexNum());
            treeMap.put("sort", orgInfo.getSort());
            treeMap.put("iconCls", orgInfo.getIcon());
            treeMap.put("remark", orgInfo.getRemark());
            treeMap.put("isInit", orgInfo.getIsInit());
            treeMap.put("status", orgInfo.getStatus());
            if (orgInfo.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                // 获取孩子节点, 先判断是否有孩子节点
                List<Map<String, Object>> childrenList = getChildren(orgInfo);
                treeMap.put("children", childrenList);
            }

            treeList.add(treeMap);
        }
        return treeList;
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IOrg#updateIsLeaf(java.lang.String, java.lang.Integer)
     */
    @Override
    public String updateIsLeaf(String orgId, Integer isLeaf)
            throws ServiceException {
        Organization orgInfo = orgDao.get(orgId);
        orgInfo.setIsLeaf(isLeaf);
        orgDao.update(orgInfo);
        return new Message("更新成功!").toString();
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IOrg#saveOrgan(com.dxr.system.entity.Organization)
     */
    @Override
    public String saveOrgan(Organization orgInfo) throws ServiceException {
        if (StringUtils.isNotEmpty(orgInfo.getFatherId())) {
            // 更新父节点
            updateIsLeaf(orgInfo.getFatherId(), ConstGlobal.TREE_NO_LEAF);

            // 计算索引值
            String indexNum = calcIndexNum(orgInfo.getFatherId(),
                    orgInfo.getCode());
            orgInfo.setIndexNum(indexNum);
        } else {
            orgInfo.setFatherId(null);
            orgInfo.setIndexNum(orgInfo.getCode());
        }

        orgDao.save(orgInfo);
        return new Message("保存成功!").toString();
    }

    /**
     * 计算当前节点的索引值
     * @param fatherId
     * @param cusCode
     * @return
     */
    private String calcIndexNum(String fatherId, String cusCode) {
        String indexNum = null;
        Organization fatherOrg = orgDao.get(fatherId);
        if (ObjectUtils.isNotEmpty(fatherOrg)) {
            String fatherIndexNum = fatherOrg.getIndexNum();
            indexNum = String.format("%1$s%2$s%3$s", fatherIndexNum,
                    ConstGlobal.ORG_INDEXNUM_SPACEMARK, cusCode);
        }
        return indexNum;
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IOrg#updateOrgan(com.dxr.system.entity.Organization)
     */
    @Override
    public String updateOrgan(Organization orgInfo) throws ServiceException {
        Organization orgDb = orgDao.get(orgInfo.getId());
        orgDb.setName(orgInfo.getName());
        orgDb.setCode(orgInfo.getCode());
        orgDb.setIcon(orgInfo.getIcon());
        orgDb.setSort(orgInfo.getSort());
        orgDb.setRemark(orgInfo.getRemark());
        if (StringUtils.isNotEmpty(orgInfo.getFatherId())) {
            // 计算索引值
            String indexNum = calcIndexNum(orgInfo.getFatherId(),
                    orgInfo.getCode());
            orgDb.setIndexNum(indexNum);
        } else {
            orgDb.setIndexNum(orgInfo.getCode());
        }
        orgDao.update(orgDb);

        // 当前节点变更需更新所有子节点的索引值
        updateChildrenIndexNum(orgDb);
        return new Message("更新成功!").toString();
    }

    /**
     * 更新所有子节点的索引值
     * @param fatherOrgInfo
     */
    private void updateChildrenIndexNum(Organization fatherOrgInfo) {
        // 获取所有的子节点
        List<Organization> orgList = orgDao.getChildren(fatherOrgInfo.getId());
        for (Organization organization : orgList) {
            String indexNum = calcIndexNum(organization.getFatherId(),
                    organization.getCode());
            organization.setIndexNum(indexNum);
            orgDao.update(organization);

            if (organization.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                // 若还有子节点, 继续更新索引值
                updateChildrenIndexNum(organization);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.dxr.system.service.api.IOrg#deleteOrgan(java.lang.String)
     */
    @Override
    public String deleteOrgan(String orgId) throws ServiceException {
        Organization orgInfo = orgDao.get(orgId);

        if (orgInfo.getIsInit() == 1) {
            return new Message(true, ConstGlobal.MESSAGE_TYPE_WARNING,
                    "为保证系统安全, 此记录不可删除!").toString();
        }

        // 判断该组织机构(管辖单位)下是否存在经营者信息
        long dealerTotal = dealerDao.getTotalByOrg(orgId);
        if (dealerTotal > 0) {
            throw new ServiceException("该组织机构下存在经营者信息, 请移除到其他机构再删除该机构!");
        }

        long userTotal = userDao.getTotalByOrg(orgId);
        if (userTotal > 0) {
            throw new ServiceException("该组织机构下存在用户信息, 请移除到其他机构再删除该机构!");
        }

        List<Organization> orgList = orgDao.getChildren(orgInfo.getId());
        for (Organization organization : orgList) {
            if (organization.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                // 若还有子节点, 继续删除
                deleteOrgan(organization.getId());
            }

            orgDao.delete(organization);
        }

        if (orgInfo.isNotEmpty() && orgInfo.getIsInit() != 1) {
            orgDao.delete(orgInfo);
        }
        return new Message("删除成功!").toString();
    }

    @Override
    public Organization getDbObject(String id) throws ServiceException {
        return orgDao.get(id);
    }

    @Override
    public String getDetails(String orgId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        Organization orgInfo = orgDao.get(orgId);
        if (ObjectUtils.isEmpty(orgInfo)) {
            return JSON.toJSONString(detailsMap);
        }
        detailsMap.put("orgInfo.id", orgInfo.getId());
        detailsMap.put("orgInfo.fatherId", orgInfo.getFatherId());
        detailsMap.put("orgInfo.name", orgInfo.getName());
        detailsMap.put("orgInfo.icon", orgInfo.getIcon());
        detailsMap.put("orgInfo.code", orgInfo.getCode());
        detailsMap.put("orgInfo.sort", orgInfo.getSort());
        detailsMap.put("orgInfo.remark", orgInfo.getRemark());
        return JSON.toJSONString(detailsMap);
    }

    @Override
    public String getTree() throws ServiceException {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        List<Organization> orgList = orgDao.getChildren(null);
        Map<String, Object> treeMap = null;
        for (Organization orgInfo : orgList) {
            treeMap = new HashMap<String, Object>();

            treeMap.put("id", orgInfo.getId());
            treeMap.put("text", orgInfo.getName());
            treeMap.put("code", orgInfo.getCode());
            treeMap.put("indexNum", orgInfo.getIndexNum());
            treeMap.put("iconCls", orgInfo.getIcon());
            // 获取孩子节点
            List<Map<String, Object>> childrenList = this
                    .getChildrenTree(orgInfo);
            treeMap.put("children", childrenList);

            treeList.add(treeMap);
        }

        return JSON.toJSONString(treeList);
    }

    /**
     * @param fatherOrgInfo
     * @return
     */
    private List<Map<String, Object>> getChildrenTree(Organization fatherOrgInfo) {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        if (fatherOrgInfo == null) {
            return treeList;
        }

        List<Organization> orgList = orgDao.getChildren(fatherOrgInfo.getId());
        Map<String, Object> treeMap = null;
        for (Organization orgInfo : orgList) {
            treeMap = new HashMap<String, Object>();
            treeMap.put("id", orgInfo.getId());
            treeMap.put("text", orgInfo.getName());
            treeMap.put("code", orgInfo.getCode());
            treeMap.put("indexNum", orgInfo.getIndexNum());
            treeMap.put("iconCls", orgInfo.getIcon());

            if (orgInfo.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                // 获取孩子节点, 先判断是否有孩子节点
                List<Map<String, Object>> childrenList = getChildrenTree(orgInfo);
                treeMap.put("children", childrenList);
            }

            treeList.add(treeMap);
        }
        return treeList;
    }

    @Override
    public Organization getOrganizationByName(String name)
            throws ServiceException {
        return orgDao.getOrganizationByName(name);
    }

}

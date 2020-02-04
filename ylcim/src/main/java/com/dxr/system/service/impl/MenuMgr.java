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
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.comm.shiro.ShiroUser;
import com.dxr.comm.utils.SecurityUser;
import com.dxr.system.dao.MenuDao;
import com.dxr.system.entity.MenuInfo;
import com.dxr.system.entity.RoleMenu;
import com.dxr.system.service.api.IMenu;
import com.dxr.system.service.api.IRoleMenu;

/**
 * @author w.xL
 */
public class MenuMgr extends BasalMgr<MenuInfo> implements IMenu {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private IRoleMenu roleMenuMgr;

    @Autowired
    private SystemConstantCache scCache;

    @Override
    public String getNav() throws ServiceException {
        // 获取当前登录用户
        ShiroUser shiroUser = SecurityUser.getLoginUser();
        List<String> roleIds = new ArrayList<String>(shiroUser.getRoles());
        //用户的手风琴菜单
        List<MenuInfo> menuList = menuDao.getChildren(
                ConstGlobal.MENU_NAV_ROOT, roleIds);
        StringBuffer html = new StringBuffer();
        String selected = "selected=\"true\"";
        for (MenuInfo menuInfo : menuList) {
            List<Map<String, Object>> treeNode = getTreeNode(menuInfo, roleIds);
            html.append("<div title=\"" + menuInfo.getName() + "\" " + selected
                    + " data-options=\"iconCls:'" + menuInfo.getIcon() + "'\">");
            html.append("<ul id=\"" + menuInfo.getId() + "\"></ul>");
            html.append("<script type=\"text/javascript\">");
            html.append("$('#" + menuInfo.getId() + "').tree({");
            html.append("data: " + JSON.toJSONString(treeNode) + ",");
            html.append("lines: true,");
            html.append("onClick: function(node){");
            html.append("addTab(node);");
            html.append("}");
            html.append("});");
            html.append("</script>");
            html.append("</div>");
            selected = "";
        }
        return html.toString();
    }

    private List<Map<String, Object>> getTreeNode(MenuInfo fatherMenuInfo,
            List<String> roleIds) {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        if (fatherMenuInfo == null) {
            return treeList;
        }

        List<MenuInfo> menuList = menuDao.getChildren(fatherMenuInfo.getId(),
                roleIds);
        Map<String, Object> treeMap = null;
        for (MenuInfo menuInfo : menuList) {
            treeMap = new HashMap<String, Object>();
            treeMap.put("id", menuInfo.getId());
            treeMap.put("text", menuInfo.getName());
            treeMap.put("iconCls", menuInfo.getIcon());
            treeMap.put("action", menuInfo.getAction());
            treeMap.put("attributes", menuInfo);
            if (menuInfo.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                treeMap.put("children", getTreeNode(menuInfo, roleIds));
            }

            treeList.add(treeMap);
        }
        return treeList;
    }

    @Override
    public String getTree() throws ServiceException {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        List<String> roleIds = new ArrayList<String>();
        roleIds.add("R-01");
        List<MenuInfo> menuList = menuDao.getChildren(
                ConstGlobal.MENU_NAV_ROOT, roleIds);
        Map<String, Object> treeMap = null;
        for (MenuInfo menuInfo : menuList) {
            treeMap = new HashMap<String, Object>();

            treeMap.put("id", menuInfo.getId());
            treeMap.put("text", menuInfo.getName());
            treeMap.put("iconCls", menuInfo.getIcon());
            treeMap.put("action", menuInfo.getAction());
            treeMap.put("attributes", menuInfo);
            treeMap.put("children", getTreeNode(menuInfo, roleIds));
            treeList.add(treeMap);
        }
        return JSON.toJSONString(treeList);
    }

    @Override
    public String getTreeGrid() throws ServiceException {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        List<String> roleIds = new ArrayList<String>();
        roleIds.add("R-01");
        List<MenuInfo> menuList = menuDao.getChildren(
                ConstGlobal.MENU_NAV_ROOT, roleIds);
        Map<String, Object> treeMap = null;
        for (MenuInfo menuInfo : menuList) {
            treeMap = new HashMap<String, Object>();
            treeMap.put("id", menuInfo.getId());
            treeMap.put("name", menuInfo.getName());
            treeMap.put("action", menuInfo.getAction());
            treeMap.put("type", menuInfo.getType());
            treeMap.put("sort", menuInfo.getSort());
            treeMap.put("iconCls", menuInfo.getIcon());
            treeMap.put("remark", menuInfo.getRemark());
            treeMap.put("isInit", menuInfo.getIsInit());
            treeMap.put("children", this.getChildren(menuInfo, roleIds));

            treeList.add(treeMap);
        }

        return JSON.toJSONString(treeList);
    }

    private List<Map<String, Object>> getChildren(MenuInfo fatherMenuInfo,
            List<String> roleIds) {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

        if (fatherMenuInfo == null) {
            return treeList;
        }

        List<MenuInfo> menuList = menuDao.getChildren(fatherMenuInfo.getId(),
                roleIds);
        Map<String, Object> treeMap = null;
        for (MenuInfo menuInfo : menuList) {
            treeMap = new HashMap<String, Object>();
            treeMap.put("id", menuInfo.getId());
            treeMap.put("name", menuInfo.getName());
            treeMap.put("action", menuInfo.getAction());
            treeMap.put("type", menuInfo.getType());
            treeMap.put("sort", menuInfo.getSort());
            treeMap.put("iconCls", menuInfo.getIcon());
            treeMap.put("remark", menuInfo.getRemark());
            treeMap.put("isInit", menuInfo.getIsInit());
            if (menuInfo.getIsLeaf() != ConstGlobal.TREE_IS_LEAF) {
                treeMap.put("children", getChildren(menuInfo, roleIds));
            }

            treeList.add(treeMap);
        }
        return treeList;
    }

    @Override
    public String saveMenu(MenuInfo menuInfo) throws ServiceException {
        if (StringUtils.isEmpty(menuInfo.getFatherId())) {
            menuInfo.setFatherId(ConstGlobal.MENU_NAV_ROOT);
        } else {
            // 更新父节点
            updateIsLeaf(menuInfo.getFatherId(), ConstGlobal.TREE_NO_LEAF);
        }

        //menuInfo.setIcon(defaultIcon);
        menuDao.save(menuInfo);

        // 将新增的资源和管理员权限绑定
        String superAdminDefaultRoleId = String.valueOf(scCache
                .get("SuperAdminDefaultRoleId"));
        RoleMenu rmInfo = new RoleMenu();
        rmInfo.setRoleId(superAdminDefaultRoleId);
        rmInfo.setMenuId(menuInfo.getId());
        roleMenuMgr.saveRoleMenu(rmInfo);
        return new Message("保存成功!").toString();
    }

    @Override
    public String deleteMenu(String menuId) throws ServiceException {
        MenuInfo menuInfo = menuDao.get(menuId);

        if (menuInfo.getIsInit() == 1) {
            return new Message(true, ConstGlobal.MESSAGE_TYPE_WARNING,
                    "为保证系统安全, 此记录不可删除!").toString();
        }

        if (menuInfo.isNotEmpty() && menuInfo.getIsInit() != 1) {
            menuInfo.setStatus(ConstGlobal.DATA_STATUS_DELETED);
            menuDao.update(menuInfo);
        }
        return new Message("删除成功!").toString();
    }

    @Override
    public String updateMenu(MenuInfo menuInfo) throws ServiceException {
        MenuInfo menuDb = menuDao.get(menuInfo.getId());
        menuDb.setName(menuInfo.getName());
        menuDb.setAction(menuInfo.getAction());
        menuDb.setIcon(menuInfo.getIcon());
        menuDb.setType(menuInfo.getType());
        menuDb.setSort(menuInfo.getSort());
        menuDb.setRemark(menuInfo.getRemark());
        menuDao.update(menuDb);
        return new Message("更新成功!").toString();
    }

    @Override
    public String updateIsLeaf(String menuId, Integer isLeaf)
            throws ServiceException {
        MenuInfo menuInfo = menuDao.get(menuId);
        menuInfo.setIsLeaf(isLeaf);
        menuDao.update(menuInfo);
        return new Message("更新成功!").toString();
    }

    @Override
    public String getDetails(String menuId) throws ServiceException {
        Map<String, Object> detailsMap = new HashMap<String, Object>();

        MenuInfo menuInfo = menuDao.get(menuId);

        detailsMap.put("menuInfo.id", menuInfo.getId());
        detailsMap.put("menuInfo.fatherId", menuInfo.getFatherId());
        detailsMap.put("menuInfo.name", menuInfo.getName());
        detailsMap.put("menuInfo.icon", menuInfo.getIcon());
        detailsMap.put("menuInfo.action", menuInfo.getAction());
        detailsMap.put("menuInfo.type", menuInfo.getType());
        detailsMap.put("menuInfo.sort", menuInfo.getSort());
        detailsMap.put("menuInfo.remark", menuInfo.getRemark());

        return JSON.toJSONString(detailsMap);
    }

    @Override
    public MenuInfo getDbObject(String id) throws ServiceException {
        return menuDao.get(id);
    }
}

package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.MenuInfo;
import com.dxr.system.service.api.IMenu;

/**
 * @author w.xL
 */
public class MenuAction extends BasalAction {

    private static final long serialVersionUID = 7477757681538795891L;

    @Autowired
    private IMenu menuMgr;

    private MenuInfo menuInfo;

    /**
     * 表单视图
     */
    @Override
    public String formView() {
        super.putViewDeftData("navMenu", ConstGlobal.MENU_TYPE_NAV);
        super.putViewDeftData("optMenu", ConstGlobal.MENU_TYPE_OPT);
        return super.formView();
    }

    /**
     * 获取导航
     */
    public void getNav() {
        try {
            String result = menuMgr.getNav();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getNav", e);
            super.empty();
        }
    }

    /**
     * 获取菜单树表格
     */
    public void getTreeGrid() {
        try {
            String result = menuMgr.getTreeGrid();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getTreeGrid", e);
            super.empty();
        }
    }

    /**
     * 获取菜单树
     */
    public void getTree() {
        try {
            String result = menuMgr.getTree();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getTreeGrid", e);
            super.empty();
        }
    }

    public void saveMenu() {
        try {
            String result = menuMgr.saveMenu(menuInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getTreeGrid", e);
            super.brace();
        }
    }

    public void updateMenu() {
        try {
            String result = menuMgr.updateMenu(menuInfo);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("updateMenu", e);
            super.brace();
        }
    }

    public void deleteMenu() {
        try {
            String result = menuMgr.deleteMenu(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("deleteMenu", e);
            super.brace();
        }
    }

    public void getDetails() {
        try {
            String result = menuMgr.getDetails(object);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("getDetails", e);
            super.brace();
        }
    }

    public MenuInfo getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfo menuInfo) {
        this.menuInfo = menuInfo;
    }
}

package com.dxr.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.entity.Iconlibrary;
import com.dxr.system.service.api.IIconlibrary;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-3-20
 */
public class IconlibraryAction extends BasalAction {

    private static final long serialVersionUID = 1141309514996987924L;

    @Autowired
    private IIconlibrary iconlibraryMgr;

    private Iconlibrary iconlibrary;

    private List<Iconlibrary> caomeiIconlibs;

    private List<Iconlibrary> iconfontIconlibs;

    /**
     * @return
     */
    public String chooseIcon() {
        try {
            caomeiIconlibs = iconlibraryMgr.queryIconlibraryList("caomei");
            iconfontIconlibs = iconlibraryMgr.queryIconlibraryList("iconfont");
        } catch (ServiceException e) {
            logger.error("IconlibraryAction.chooseIcon() is error...", e);
        }
        return "chooseIcon";
    }

    /**
     * 
     */
    public void queryIconlibrary() {
        try {
            String result = iconlibraryMgr.queryIconlibrary(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("IconlibraryAction.queryIconlibrary() is error...", e);
            super.bracket();
        }
    }

    /**
     * 保存
     */
    public void saveIconlibrary() {
        try {
            String result = iconlibraryMgr.saveIconlibrary(iconlibrary);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("IconlibraryAction.saveIconlibrary() is error...", e);
            super.operateException();
        }
    }

    /**
     * 删除
     */
    public void deleteIconlibrary() {
        try {
            String result = iconlibraryMgr.deleteIconlibrary(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("IconlibraryAction.deleteIconlibrary() is error...", e);
            super.operateException();
        }
    }

    public Iconlibrary getIconlibrary() {
        return iconlibrary;
    }

    public void setIconlibrary(Iconlibrary iconlibrary) {
        this.iconlibrary = iconlibrary;
    }

    public List<Iconlibrary> getCaomeiIconlibs() {
        return caomeiIconlibs;
    }

    public void setCaomeiIconlibs(List<Iconlibrary> caomeiIconlibs) {
        this.caomeiIconlibs = caomeiIconlibs;
    }

    public List<Iconlibrary> getIconfontIconlibs() {
        return iconfontIconlibs;
    }

    public void setIconfontIconlibs(List<Iconlibrary> iconfontIconlibs) {
        this.iconfontIconlibs = iconfontIconlibs;
    }
}

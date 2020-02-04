package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.comm.cache.SystemConstantCache;

public class ConsoleAction extends BasalAction {

    private static final long serialVersionUID = -5106032445403815374L;

    @Autowired
    private SystemConstantCache scCache;

    @Override
    public String index() {
        String copyright = String.valueOf(scCache.get("Copyright"));
        super.putViewDeftData("copyright", copyright);
        return super.index();
    }

    public String developing() {
        return "developing";
    }
}

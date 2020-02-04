package com.dxr.system.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.supers.action.BasalAction;
import com.dxr.system.service.api.ISystemConstant;

/**
 * @description: <系统常量Action>
 * @author: w.xL
 * @date: 2018-3-28
 */
public class SystemConstantAction extends BasalAction {

    private static final long serialVersionUID = 6349840092407673482L;

    @Autowired
    private ISystemConstant systemConstantMgr;
    
    
}

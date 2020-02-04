package com.dxr.webui.wechat.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dxr.system.entity.WeChatViewInfo;
import com.dxr.system.service.api.IWeChatView;

/**
 * @description: <微信视图服务Action层>
 * @author: w.xL
 * @date: 2018-3-2
 */
public class WeChatViewAction extends BasalAction {

    private static final long serialVersionUID = 8547561328664116255L;

    private static final Logger logger = LoggerFactory
            .getLogger(WeChatViewAction.class);

    @Autowired
    private IWeChatView wechatViewMgr;

    /**
     * 视图Id
     */
    private String viewId;

    /**
     * 重定向地址
     */
    private String redirect;

    @Override
    public String execute() throws Exception {
        WeChatViewInfo wechatView = wechatViewMgr.getWeChatView(viewId);
        if (ObjectUtils.isNotEmpty(wechatView)
                && wechatView.getStatus() != ConstGlobal.DATA_STATUS_DELETED) {
            // 去缓存
            this.redirect = super.setNoCache(wechatView.getRedirect());
            logger.info("--------------------------------------------------------------------");
            logger.info("[WeChat viewMgr] Users visit WeChat page is : "
                    + redirect);
            logger.info("[WeChat viewMgr] The menu is : "
                    + wechatView.getName());
            // TODO 这块以后可以做微信访问统计分析
        }
        return super.execute();
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}

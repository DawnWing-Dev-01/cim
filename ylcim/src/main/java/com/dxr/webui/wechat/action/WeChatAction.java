package com.dxr.webui.wechat.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.action.BasalAction;
import com.dawnwing.framework.utils.ObjectUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.dxr.apply.entity.ComplaintSheetInfo;
import com.dxr.apply.entity.ConsumerTipsInfo;
import com.dxr.apply.entity.ConsumerWarningInfo;
import com.dxr.apply.entity.DealerInfo;
import com.dxr.apply.entity.IndustryInfo;
import com.dxr.apply.entity.WorkFlowExample;
import com.dxr.apply.entity.WorkFlowInfo;
import com.dxr.apply.service.api.IComplaintSheet;
import com.dxr.apply.service.api.IConsumerTips;
import com.dxr.apply.service.api.IConsumerWarning;
import com.dxr.apply.service.api.IDealer;
import com.dxr.apply.service.api.IFlowExample;
import com.dxr.apply.service.api.IIndustry;
import com.dxr.apply.service.api.IWorkFlow;
import com.dxr.cms.entity.ArticleInfo;
import com.dxr.cms.entity.ColumnInfo;
import com.dxr.cms.service.api.IArticle;
import com.dxr.cms.service.api.IColumn;
import com.dxr.comm.cache.SystemConstantCache;
import com.dxr.system.entity.Organization;
import com.dxr.system.service.api.IOrg;
import com.dxr.webui.wechat.service.api.IWeChat;
import com.dxr.webui.wechat.vo.WxUserInfo;

/**
 * @description: <微信Action层>
 * @author: w.xL
 * @date: 2018-2-28
 */
public class WeChatAction extends BasalAction {

    private static final long serialVersionUID = -8553829024400433203L;

    private static final Logger logger = LoggerFactory
            .getLogger(WeChatAction.class);

    @Autowired
    private IWeChat weChatMgr;
    @Autowired
    private IDealer dealerMgr;
    @Autowired
    private IIndustry industryMgr;
    @Autowired
    private IComplaintSheet complaintSheetMgr;
    @Autowired
    private IArticle articleMgr;
    @Autowired
    private IColumn columnMgr;
    @Autowired
    private IConsumerTips consumerTipsMgr;
    @Autowired
    private IConsumerWarning consumerWarningMgr;
    @Autowired
    private SystemConstantCache scCache;
    @Autowired
    private IWorkFlow workFlowMgr;
    @Autowired
    private IFlowExample flowExampleMgr;
    @Autowired
    private IOrg orgMgr;

    // 调用JS-SDK, 微信配置对象
    private Map<String, String> wxConfig;
    private Map<String, Object> authorizeConfig;
    // 微信授权后返回值
    private String code;
    private String state;

    // 经营者信用
    private Map<String, Object> dealerCredit;
    // 文章对象
    private ArticleInfo articleInfo;
    // 栏目对象
    private ColumnInfo columnInfo;
    // 12315消费提示月份列表
    private List<ConsumerTipsInfo> monthtipsList;
    // 文章列表封装Map
    private Map<String, Object> articleListMap;
    // 行业消费预警列表
    List<ConsumerWarningInfo> earlyWarningList;

    /**
     * 投诉登记表实体
     */
    private ComplaintSheetInfo complaintSheet;

    private IndustryInfo industryInfo;

    /**
     * 经营者Id
     */
    private String dealerId;

    /**
     * 行业Id
     */
    private String industryId;

    /**
     * 文章Id
     */
    private String articleId;

    // 搜索关键字
    private String keyworks;

    /**
     * 失信行为举报视图
     * @return
     */
    public String dishonestyEnterView() {
        try {
            String url = super.getBackUrl();
            wxConfig = weChatMgr.getPageConfig(url);
            // 网页授权
            authorizeConfig = weChatMgr.wechatAuthorize(super.getRequest(),
                    url, code, state);
            if (StringUtils.isNotEmpty(dealerId)) {
                DealerInfo dealerInfo = dealerMgr.getDbObject(dealerId);
                super.putViewDeftData("dealerInfo", dealerInfo);
                // 获取管辖单位名称
                String jurisdictionName = null;
                Organization orgInfo = orgMgr.getDbObject(dealerInfo.getJurisdiction());
                if (ObjectUtils.isNotEmpty(orgInfo)) {
                    jurisdictionName = orgInfo.getName();
                }
                super.putViewDeftData("jurisdiction", jurisdictionName);
            } else {
                super.putViewDeftData("dealerInfo", new DealerInfo());
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            super.newFormToken();
        }
        return "enterView";
    }

    /**
     * 失信行为查询
     * @return
     */
    public String dishonestyQueryView() {
        try {
            String url = super.getBackUrl();
            wxConfig = weChatMgr.getPageConfig(url);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
        return "queryView";
    }

    /**
     * 经营者信用查询
     * @return
     */
    public String dealerCreditQuery() {
        try {
            if ("ScanQRcode".equals(state)) {
                WxUserInfo wxUserInfo = weChatMgr.wechatSubscribe(
                        super.getRequest(), code, state);
                if (wxUserInfo != null) {
                    // 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
                    Integer subscribe = wxUserInfo.getSubscribe();
                    logger.info("wechat user subscribe status: "
                            + wxUserInfo.getSubscribe());
                    if (subscribe == 0) {
                        // 未关注的用户跳转到订阅引导页面
                        return "subscribeGuideView";
                    }
                }
            }

            dealerCredit = dealerMgr.queryDealerCredit(dealerId);
            dealerCredit.put("nowDate", new Date());
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        }
        return "showQueryView";
    }

    /**
     * 模糊查找经营者
     */
    public void fuzzyQueryDealerList() {
        try {
            String result = dealerMgr.fuzzyQueryDealerList(keyworks);
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WeChatAction.fuzzyQueryDealerList() is error...", e);
            super.bracket();
        }
    }

    /**
     * 消费助手视图
     * @return
     */
    public String helperView() {
        try {
            monthtipsList = consumerTipsMgr.getConsumerTipsList();
        } catch (ServiceException e) {
            logger.error("WeChatAction.getConsumerTipsList() is error...", e);
            monthtipsList = new ArrayList<ConsumerTipsInfo>();
        }

        return "helperView";
    }

    /**
     * 行业列表视图
     */
    public void industrylistView() {
        try {
            String result = industryMgr.getIndustryCommBox();
            super.writeToView(result);
        } catch (Exception e) {
            logger.error("WeChatAction.getIndustryCommBox() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * 行业消费预警视图
     * @return
     */
    public String earlywarning() {
        try {
            // 行业信息Entity
            industryInfo = industryMgr.getDbObject(industryId);
            // 根据行业Id查询行业消费预警
            earlyWarningList = consumerWarningMgr
                    .findIndustryConsumerWarning(industryId);
        } catch (ServiceException e) {
            logger.error("WeChatAction.earlywarning() is error...", e);
        }
        return "earlyWarningView";
    }

    /**
     * 文章列表
     * @return
     */
    public String showArticleListView() {
        try {
            String columnId = articleInfo.getColumnId();
            columnInfo = columnMgr.getDbObject(columnId);
            // 默认显示10条记录
            rows = 10;
            articleInfo.setSearchIndex(keyworks);
            articleListMap = articleMgr
                    .findArticleList(page, rows, articleInfo);
        } catch (ServiceException e) {
            logger.error("WeChatAction.showArticleListView() is error...", e);
            articleListMap = new HashMap<String, Object>();
        }
        return "articleListView";
    }

    /**
     * 展示文章
     * @return
     */
    public String showArticleView() {
        // 根据articleId查询消费法规/消费知识/ 消费提示
        try {
            // 文章信息
            articleInfo = articleMgr.getDbObject(articleId);
            // 栏目信息
            columnInfo = columnMgr.getDbObject(articleInfo.getColumnId());
            // 平台默认名称
            super.putViewDeftData("SystemName", scCache.get("SystemName"));
            // 版权信息
            super.putViewDeftData("Copyright", scCache.get("Copyright"));
        } catch (ServiceException e) {
            logger.error("WeChatAction.showArticleView() is error...", e);
            articleInfo = new ArticleInfo();
            columnInfo = new ColumnInfo();
        }
        return "showArticleView";
    }

    /**
     * 保存投诉单
     */
    public void saveComplaintSheet() {
        try {
            // 防止微信重复提交(发生2次Ajax请求)
            if (super.checkFormToken()) {
                String result = complaintSheetMgr
                        .saveComplaintSheet(complaintSheet);
                super.writeToView(result);
            }
        } catch (Exception e) {
            logger.error("WeChatAction.saveComplaintSheet() is error...", e);
            super.writeToView(new Message(false, null, "提交投诉单异常，请联系管理员。")
                    .toString());
        }
    }

    /**
     * 获取微信关注用户列表
     */
    public void getWeChatUserList() {
        try {
            String result = weChatMgr.getWeChatUserList(object);
            super.writeToView(result);
        } catch (ServiceException e) {
            logger.error("WeChatAction.getWeChatUserList() is error...", e);
            super.emptyGrid();
        }
    }

    /**
     * @return
     */
    public String showSchedule() {
        try {
            // 查询流程定义列表
            super.putViewDeftData("workflowList", workFlowMgr.getWorkFlowList());
            // 查询正在运行的流程
            super.putViewDeftData("workflowExample",
                    flowExampleMgr.loadFlowExample(object));
        } catch (ServiceException e) {
            super.putViewDeftData("workflowList", new ArrayList<WorkFlowInfo>());
            super.putViewDeftData("workflowExample", new WorkFlowExample());
        }
        return "scheduleView";
    }

    public Map<String, String> getWxConfig() {
        return wxConfig;
    }

    public void setWxConfig(Map<String, String> wxConfig) {
        this.wxConfig = wxConfig;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Map<String, Object> getDealerCredit() {
        return dealerCredit;
    }

    public void setDealerCredit(Map<String, Object> dealerCredit) {
        this.dealerCredit = dealerCredit;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public IndustryInfo getIndustryInfo() {
        return industryInfo;
    }

    public void setIndustryInfo(IndustryInfo industryInfo) {
        this.industryInfo = industryInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getAuthorizeConfig() {
        return authorizeConfig;
    }

    public void setAuthorizeConfig(Map<String, Object> authorizeConfig) {
        this.authorizeConfig = authorizeConfig;
    }

    public ComplaintSheetInfo getComplaintSheet() {
        return complaintSheet;
    }

    public void setComplaintSheet(ComplaintSheetInfo complaintSheet) {
        this.complaintSheet = complaintSheet;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }

    public List<ConsumerTipsInfo> getMonthtipsList() {
        return monthtipsList;
    }

    public void setMonthtipsList(List<ConsumerTipsInfo> monthtipsList) {
        this.monthtipsList = monthtipsList;
    }

    public Map<String, Object> getArticleListMap() {
        return articleListMap;
    }

    public void setArticleListMap(Map<String, Object> articleListMap) {
        this.articleListMap = articleListMap;
    }

    public String getKeyworks() {
        return keyworks;
    }

    public void setKeyworks(String keyworks) {
        this.keyworks = keyworks;
    }

    public List<ConsumerWarningInfo> getEarlyWarningList() {
        return earlyWarningList;
    }

    public void setEarlyWarningList(List<ConsumerWarningInfo> earlyWarningList) {
        this.earlyWarningList = earlyWarningList;
    }
}

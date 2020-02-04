package com.dawnwing.framework.supers.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dawnwing.framework.common.ConstGlobal;
import com.dawnwing.framework.common.Message;
import com.dawnwing.framework.common.Property;
import com.dawnwing.framework.utils.IoUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BasalAction extends ActionSupport {

    private static final long serialVersionUID = -3743179947036178754L;

    protected static final Logger logger = LoggerFactory
            .getLogger(BasalAction.class);

    protected String[] strArray;
    protected String object;

    /**
     * 视图类型
     */
    protected String viewType;

    protected int page = 1;
    protected int rows = Integer.MAX_VALUE;

    protected File file;

    protected String fileFileName;

    protected String fileContentType;

    protected int result;

    protected long random;

    protected String v;

    protected String formToken;

    /**
     * 页面/视图默认数据
     */
    protected Map<String, Object> viewDeftData = null;

    /**
     * 开始日期
     */
    protected Date startDate;

    /**
     * 结束日期
     */
    protected Date endDate;

    public String index() {
        random = (long) (Math.random() * 100000000);
        return ConstGlobal.ACTION_RESULT_NAME_INDEX;
    }

    public String formView() {
        return ConstGlobal.ACTION_RESULT_NAME_FORMVIEW;
    }

    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public ApplicationContext getApplicationContext() {
        ApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(ServletActionContext
                        .getServletContext());
        return applicationContext;
    }

    public void writeToView(String message) {
        PrintWriter writer = null;
        try {
            getResponse().setContentType("text/html;charset=utf-8");
            writer = getResponse().getWriter();
            writer.write(message.replaceAll("null", ""));
            writer.flush();
        } catch (Exception e) {
            logger.info("print", e);
        } finally {
            // 关闭输出流
            IoUtils.unifyClose(writer);
        }
    }

    /**
     * 获取当前地址
     * @return
     */
    public String getBackUrl() {
        HttpServletRequest request = getRequest();
        StringBuilder strBackUrl = new StringBuilder();
        strBackUrl.append(request.getScheme());
        strBackUrl.append("://");
        strBackUrl.append(request.getServerName());
        int serPort = request.getServerPort();
        if (serPort != 80) {
            strBackUrl.append(":");
            strBackUrl.append(serPort);
        }
        strBackUrl.append(request.getContextPath());
        strBackUrl.append(request.getServletPath());
        String queryString = request.getQueryString();
        if (StringUtils.isNotEmpty(queryString)) {
            strBackUrl.append("?");
            strBackUrl.append(queryString);
        }
        return strBackUrl.toString();
    }

    /**
     * 设置没有缓冲
     */
    public String setNoCache(String redirect) {
        StringBuilder url = new StringBuilder(redirect);
        url.append("?");
        url.append("_v");
        url.append("=");
        url.append(new Date().getTime());
        return url.toString();
    }

    /**
     * 产生表单唯一Token, 防止表单重复提交
     */
    public void newFormToken() {
        String uuid = UUID.randomUUID().toString();
        getSession().setAttribute("formToken", uuid);
    }

    /**
     * 检验表单Token是否相等, 相等后执行后续操作并且销毁Token, 不等直接返回
     * @param token
     * @return
     */
    public boolean checkFormToken() {
        boolean isEqual = false;
        String sToken = String.valueOf(getSession().getAttribute("formToken"));
        if (sToken.equals(formToken)) {
            isEqual = true;
            getSession().setAttribute("formToken", null);
        }
        return isEqual;
    }

    /**
     * 打印空 print ""
     */
    public void empty() {
        writeToView("");
    }

    /**
     * 打印中括号 print []
     */
    public void bracket() {
        writeToView("[]");
    }

    /**
     * 打印花括号 print {}
     */
    public void brace() {
        writeToView("{}");
    }

    public void operateException() {
        writeToView(new Message(false, Property.error, "操作异常！").toString());
    }

    /**
     * {"total": 0,"rows":[]}
     */
    public void emptyGrid() {
        writeToView("{\"total\": 0,\"rows\":[]}");
    }

    public void error(String message) {
        writeToView(new Message(false, ConstGlobal.MESSAGE_TYPE_ERROR, message)
                .toString());
    }

    public void putViewDeftData(String key, Object value) {
        if (viewDeftData == null) {
            viewDeftData = new HashMap<String, Object>();
        }
        viewDeftData.put(key, value);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getRandom() {
        return random;
    }

    public void setRandom(long random) {
        this.random = random;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getFormToken() {
        return formToken;
    }

    public void setFormToken(String formToken) {
        this.formToken = formToken;
    }

    public String[] getStrArray() {
        return strArray;
    }

    public void setStrArray(String[] strArray) {
        this.strArray = strArray;
    }

    public Map<String, Object> getViewDeftData() {
        return viewDeftData;
    }

    public void setViewDeftData(Map<String, Object> viewDeftData) {
        this.viewDeftData = viewDeftData;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

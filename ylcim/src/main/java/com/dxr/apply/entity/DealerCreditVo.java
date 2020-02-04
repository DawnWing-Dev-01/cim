package com.dxr.apply.entity;

import java.util.Date;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <经营者诚信经营记录VO>
 * @author: w.xL
 * @date: 2018-4-9
 */
public class DealerCreditVo extends AbstractEntity {

    private static final long serialVersionUID = 2437355781714806967L;

    public DealerCreditVo() {
        super();
    }

    public DealerCreditVo(String issueReported, Date reportDate,
            String penaltyResult) {
        setIssueReported(issueReported);
        setReportDate(reportDate);
        setPenaltyResult(penaltyResult);
    }

    /**
     * 被举报问题
     */
    private String issueReported;

    /**
     * 举报时间
     */
    private Date reportDate;

    /**
     * 处罚结果
     */
    private String penaltyResult;

    public String getIssueReported() {
        return issueReported;
    }

    public void setIssueReported(String issueReported) {
        this.issueReported = issueReported;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getPenaltyResult() {
        return penaltyResult;
    }

    public void setPenaltyResult(String penaltyResult) {
        this.penaltyResult = penaltyResult;
    }
}

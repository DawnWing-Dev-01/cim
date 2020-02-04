package com.dawnwing.framework.common;

public class FilterBean {

    private String property;

    private String factor;

    private Object parameter;

    /**
     * 是否使用命名传参, 否则是占位符形式
     * <br>默认使用命名传参形式
     */
    private Boolean isNamed = true;

    public FilterBean(String property, String factor, Object parameter) {
        this.property = property;
        this.factor = factor;
        this.parameter = parameter;
        this.isNamed = true;
    }

    public FilterBean(String property, String factor, Object parameter,
            Boolean isNamed) {
        this.property = property;
        this.factor = factor;
        this.parameter = parameter;
        this.isNamed = isNamed;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public Boolean getIsNamed() {
        return isNamed;
    }

    public void setIsNamed(Boolean isNamed) {
        this.isNamed = isNamed;
    }
}

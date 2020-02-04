package com.dawnwing.framework.common;

/**
 * @author wenxiaolong
 * 全局的常量
 */
public class ConstGlobal {

    /**
     * 数据状态：好的数据
     */
    public final static Integer DATA_STATUS_OKAY = 1;

    /**
     * 数据状态：删除的数据
     */
    public final static Integer DATA_STATUS_DELETED = 0;

    /**
     * 初始化数据, 不可删除数据
     */
    public final static Integer DATA_IS_INIT = 1;

    /**
     * 后添加的数据, 非初始化, 可删除
     */
    public final static Integer DATA_NO_INIT = 0;

    /**
     * 树型结构的叶子节点
     */
    public final static Integer TREE_IS_LEAF = 1;

    /**
     * 树型结构的分支节点（非叶子）
     */
    public final static Integer TREE_NO_LEAF = 0;

    /**
     * 导航菜单根节点
     */
    public final static String MENU_NAV_ROOT = "NAV";

    /**
     * 菜单类型之导航菜单
     */
    public final static String MENU_TYPE_NAV = "navigate";

    /**
     * 菜单类型之操作菜单
     */
    public final static String MENU_TYPE_OPT = "operate";

    /**
     * 用户性别：男
     */
    public final static Integer USER_GENDER_MALE = 1;

    /**
     * 用户性别：女
     */
    public final static Integer USER_GENDER_FEMALE = 2;

    /**
     * 消息类型之提示信息
     */
    public final static String MESSAGE_TYPE_INFO = "info";

    /**
     * 消息类型之警告信息
     */
    public final static String MESSAGE_TYPE_WARNING = "warning";

    /**
     * 消息类型之错误信息
     */
    public final static String MESSAGE_TYPE_ERROR = "error";

    /**
     * 消息类型之成功
     */
    public final static String MESSAGE_TYPE_SUCCESS = "success";

    /**
     * 组织机构索引号间隔符, -
     */
    public final static String ORG_INDEXNUM_SPACEMARK = "-";

    /**
     * Action返回值之, formView
     */
    public static final String ACTION_RESULT_NAME_FORMVIEW = "formView";

    /**
     * Action返回值之, index
     */
    public static final String ACTION_RESULT_NAME_INDEX = "index";

    /**
     * 上传附件类型之图片
     */
    public static final String UPLOAD_ATTACH_TYPE_IMAGE = "image";

    /**
     * 上传附件类型之flash
     */
    public static final String UPLOAD_ATTACH_TYPE_FLASH = "flash";

    /**
     * 上传附件类型之媒体
     */
    public static final String UPLOAD_ATTACH_TYPE_MEDIA = "media";

    /**
     * 上传附件类型之文件
     */
    public static final String UPLOAD_ATTACH_TYPE_FILES = "file";

    /**
     * 上传附件类型之其他
     */
    public static final String UPLOAD_ATTACH_TYPE_OTHER = "other";

    /**
     * 举报投诉来源：微信举报
     */
    public static final String COMPLAINT_SOURCE_WECHAT_ENTRY = "weChatEntry";

    /**
     * 举报投诉来源：系统录入
     */
    public static final String COMPLAINT_SOURCE_SYSTEM_ENTRY = "systemEntry";

    /**
     * 举报投诉类型：匿名举报
     */
    public static final String COMPLAINT_TYPE_ANONYMOU = "anonymou";

    /**
     * 举报投诉类型：实名举报
     */
    public static final String COMPLAINT_TYPE_REALNAME = "realname";

    /**
     * 投诉单状态：已起草, 待提交
     */
    public static final String COMPLAINT_FLOW_STATUS_DRAFT = "draft";

    /**
     * 投诉单状态：已提交, 待受理
     */
    public static final String COMPLAINT_FLOW_STATUS_SUBMITTED = "submitted";

    /**
     * 投诉单状态：已受理, 待审核
     */
    public static final String COMPLAINT_FLOW_STATUS_ACCEPTED = "accepted";

    /**
     * 投诉单状态：已审核, 待分流
     */
    public static final String COMPLAINT_FLOW_STATUS_AUDITED = "audited";

    /**
     * 投诉单状态：已分流, 待办理
     */
    public static final String COMPLAINT_FLOW_STATUS_SHUNTED = "shunted";

    /**
     * 投诉单状态：已办理, 待审核
     */
    public static final String COMPLAINT_FLOW_STATUS_ALREADYHANDLED = "alreadyHandled";

    /**
     * 投诉单状态：已审核（分局主管）, 待终审
     */
    public static final String COMPLAINT_FLOW_STATUS_SUPERVISORAUDITED = "supervisorAudited";
    
    /**
     * 投诉单状态：已终审, 结束
     */
    public static final String COMPLAINT_FLOW_STATUS_CONFIRMED = "confirmed";

    /**
     * 工作流之角色处理
     */
    public final static Integer WORKFLOW_HANDLE_MODE_ROLE_HANDLE = 1;

    /**
     * 工作流之用户处理
     */
    public final static Integer WORKFLOW_HANDLE_MODE_USER_HANDLE = 2;

    /**
     * 工作流之不用处理
     */
    public final static Integer WORKFLOW_HANDLE_MODE_NOT_HANDLE = 0;

    /**
     * 流程实例状态之已结束
     */
    public final static Integer FLOW_EXAMPLE_STATUS_OVER = 0;

    /**
     * 流程实例状态之正在执行
     */
    public final static Integer FLOW_EXAMPLE_STATUS_RUN = 1;

    /**
     * 投诉处理 类型之最终处理(处罚结果)
     */
    public final static String COMPLAINT_HANDLE_TYPE_FINALLY = "finally";

    /**
     * 投诉处理 类型之处理过程(过程记录)
     */
    public final static String COMPLAINT_HANDLE_TYPE_PROCESS = "process";

    /**
     * 投诉需要公示
     */
    public final static Integer COMPLAINT_IS_PUBLICITY_TRUE = 1;

    /**
     * 投诉不需要公示
     */
    public final static Integer COMPLAINT_IS_PUBLICITY_FALSE = 0;

    /**
     * 流程实例类型之非投诉举报流程
     */
    public final static String WORKFLOW_EXAMPLE_TYPE_UN_COMPLAIN = "unComplain";

    /**
     * 流程实例类型之正常受理流程
     */
    public final static String WORKFLOW_EXAMPLE_TYPE_NORMAL_ACCEPT = "normalAccept";

    /**
     * 流程实例类型之非正常受理
     */
    public final static String WORKFLOW_EXAMPLE_TYPE_UNUSUAL_ACCEPT = "unusualAccept";
    
    /**
     * 流程审核同意
     */
    public final static Integer WORKFLOW_HANDLE_RESULT_AGREE = 1;
    
    /**
     * 流程审核驳回
     */
    public final static Integer WORKFLOW_HANDLE_RESULT_REJECT = 0;
    
    /**
     * 流程审核结束
     */
    public final static Integer WORKFLOW_HANDLE_RESULT_END = -1;
    
}

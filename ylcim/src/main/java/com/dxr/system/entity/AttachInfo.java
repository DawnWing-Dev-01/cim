package com.dxr.system.entity;

import com.dawnwing.framework.supers.entity.AbstractEntity;

/**
 * @description: <附件实体类>
 * @author: w.xL
 * @date: 2018-3-23
 */
public class AttachInfo extends AbstractEntity {

    private static final long serialVersionUID = -6249737329497227276L;

	/**
	 * 附件类型
	 * 1：图片-image；2：文档-doc；3：媒体-media；4:其他-other；
	 */
	private String attachType;
	
	/**
	 * 附件名称(原始名)
	 */
	private String attachName;
	
	/**
	 * 附件大小
	 */
	private Float attachSize;
	
	/**
	 * 文件类型
	 */
	private String fileType;
	
	/**
	 * 附件在磁盘上的地址
	 */
	private String attachPath;
	
	/**
	 * 创建人
	 */
	private String creatorId;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public Float getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Float attachSize) {
		this.attachSize = attachSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}

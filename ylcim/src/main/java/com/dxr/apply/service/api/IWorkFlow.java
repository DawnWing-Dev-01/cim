package com.dxr.apply.service.api;

import java.util.List;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.supers.service.api.IBasalMgr;
import com.dxr.apply.entity.WorkFlowInfo;

/**
 * @description: <简易工作流服务接口层>
 * @author: w.xL
 * @date: 2018-4-2
 */
public interface IWorkFlow extends IBasalMgr<WorkFlowInfo> {

	/**
	 * @param page
	 * @param rows
	 * @param workFlowInfo
	 * @return
	 * @throws ServiceException
	 */
	public String getWorkFlowPage(int page, int rows, WorkFlowInfo workFlowInfo)
			throws ServiceException;

	/**
	 * @param page
	 * @param rows
	 * @param workFlowInfo
	 * @return
	 * @throws ServiceException
	 */
	public List<WorkFlowInfo> getWorkFlowList() throws ServiceException;

	/**
	 * @param workFlowInfo
	 * @return
	 * @throws ServiceException
	 */
	public String saveWorkFlow(WorkFlowInfo workFlowInfo)
			throws ServiceException;

	/**
	 * @param workFlowInfo
	 * @return
	 * @throws ServiceException
	 */
	public String updateWorkFlow(WorkFlowInfo workFlowInfo)
			throws ServiceException;

	/**
	 * @param workFlowId
	 * @return
	 * @throws ServiceException
	 */
	public String deleteWorkFlow(String workFlowId) throws ServiceException;

	/**
	 * @param workFlowId
	 * @return
	 * @throws ServiceException
	 */
	public String getDetails(String workFlowId) throws ServiceException;

	/**
	 * 获取当前的流程节点
	 * @param flowCode
	 * @return
	 * @throws ServiceException
	 */
	public WorkFlowInfo loadNow(String flowCode) throws ServiceException;
	
	/**
	 * 获取第一条记录, 根据sort排序
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public WorkFlowInfo loadFirst() throws ServiceException;

	/**
	 * 获取最后一条记录, 根据sort排序
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public WorkFlowInfo loadLast() throws ServiceException;

	/**
	 * 根据flowCode获取下一个节点
	 * 
	 * @param flowCode
	 * @return
	 * @throws ServiceException
	 */
	public WorkFlowInfo loadNext(String flowCode) throws ServiceException;

	/**
	 * 根据flowCode获取上一个节点
	 * 
	 * @param flowCode
	 * @return
	 * @throws ServiceException
	 */
	public WorkFlowInfo loadPrev(String flowCode) throws ServiceException;
}
package com.centaline.trans.eloan.service;

import java.util.List;
import java.util.Map;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.vo.ELoanVo;

public interface ToEloanCaseService {

	ToEloanCase getToEloanCaseByPkId(Long pkid);

	void saveEloanApply(SessionUser user, ToEloanCase tEloanCase);

	int updateEloanApply(SessionUser user, ToEloanCase tEloanCase);

	int updateEloanByCaseCode(User user ,ToEloanCase tEloanCase);

	void deleteById(Long pkid);

	void saveEloanSign(String taskId, ToEloanCase tEloanCase);

	List<ToEloanCase> getToEloanCaseListByProperty(ToEloanCase tEloanCase);

	// void eloanProcessConfirm(String taskId, String approved,ToEloanCase
	// toEloanCase);

	void eloanProcessConfirm(String taskId, Map<String, Object> map,
			ToEloanCase toEloanCase, boolean isUpdate);

	List<String> validateEloanApply(ToEloanCase tEloanCase);

	AjaxResponse<Boolean> validateIsFinishRelease(String eloanCode,
			Double sumAmount);

	void eloanInfoForUpdate(ToEloanCase toEloanCase);

	void abanById(ToEloanCase eloanCase);

	ToEloanCase selectByEloanCode(String eloanCode);

	/**
	 * E+����Ŵ�Ա�ӵ��ʹ��
	 * 
	 * @param eLoanVo
	 *            E+����ǰ̨��ֵ����
	 * @param map
	 *            ���̲���
	 * @param taskId
	 *            ����id
	 * @return ����true,�����ɹ�;����false,����ʧ�ܡ�
	 */
	public boolean accept(ELoanVo eLoanVo, Map<String, Object> map,
			String taskId);

	/**
	 * E+�������
	 * 
	 * @param eLoanVo
	 *            E+����ǰ̨��ֵ����
	 * @return ����true,�����ɹ�;����false,����ʧ�ܡ�
	 */
	public boolean followUp(ELoanVo eLoanVo);

	/**
	 * selectBackKaCountByTime:(���ʱ���ѯ��̨������������). <br/>
	 * 
	 * @author gongjd
	 * @param endWeekDay
	 * @return
	 * @since JDK 1.8
	 */
	int selectBackKaCountByTime(int endWeekDay);

	/**
	 * selectBackKaCountByTime:(���ʱ���ѯ��̨�������������). <br/>
	 * 
	 * @author gongjd
	 * @param endWeekDay
	 * @return
	 * @since JDK 1.8
	 */
	int selectBackAppCountByTime(int endWeekDay);

	void batchChangeOwner(String[] eloanCode, String newConsultantId,
			String newManagerId);

	void changeOwner(String eloanCode, String newConsultantId,
			String newManagerId);

	List<String> selectConsAndManager(Long pkId);

	/**
	 * 信息补充、补建
	 * 
	 * @param eLoanVo
	 *            E+信息对象
	 */
	public void suppleInfo(ELoanVo eLoanVo);
}

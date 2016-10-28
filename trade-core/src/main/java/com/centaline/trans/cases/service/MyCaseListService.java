package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;

public interface MyCaseListService {

	public ToCase findToCaseByCaseCode(String caseCode);
	public ToCaseInfo findToCaseInfoByCaseCode(String caseCode);
	public List<TgGuestInfo> findTgGuestInfoByCaseCode(String caseCode);
	public ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode);
	public List<TgServItemAndProcessor> findTgServItemAndProcessorByUserId(String userId);
	public List<String> findCaseCodesByUserId(String userId);
}

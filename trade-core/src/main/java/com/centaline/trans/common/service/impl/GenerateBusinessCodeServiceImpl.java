package com.centaline.trans.common.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.service.GenerateBusinessCodeService;
import com.centaline.trans.utils.DateUtil;

@Service
public class GenerateBusinessCodeServiceImpl implements GenerateBusinessCodeService {

	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public String createItemCode() {
		return createCodeByMonthPattern(GenerateBusinessCodeService.ITEM_CODE);
	}

	@Override
	public String createCodeByMonthPattern(String code) {
		String dateStr = DateUtil.getFormatDate(new Date(), "yyyyMMdd");
		String month = dateStr.substring(0, 6);

		return uamBasedataService.nextSeqVal(code, month);
	}

}

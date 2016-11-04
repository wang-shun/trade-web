package com.centaline.trans.remote.service;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.centaline.trans.mortgage.vo.ToEvaReportVo;
import com.centaline.trans.remote.vo.BankSearchVo;
import com.centaline.trans.remote.vo.DisagreeApplyVo;
import com.centaline.trans.remote.vo.HouseInfoVo;
import com.centaline.trans.remote.vo.MortgageAttamentVo;
import com.centaline.trans.remote.vo.PricingConfirmVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface EguService {

	String getAttachmentInfo(MortgageAttamentVo mortgageAttament);

	String saveAttachment(MortgageAttamentVo mortgageAttament);

	void assess(HouseInfoVo houseInfo) throws JsonParseException,
			JsonMappingException, IOException;

	void disagree(DisagreeApplyVo disagreeApply)
			throws ClientProtocolException, IOException;

	void confirm(PricingConfirmVo pricingConfirm)
			throws JsonParseException, JsonMappingException, ParseException,
			IOException;

	void prereport(ToEvaReportVo evaReport, MortgageAttamentVo mortgageAttament)
			throws JsonParseException, JsonMappingException, ParseException,
			IOException;

	void inquiryreport(ToEvaReportVo evaReport,
			MortgageAttamentVo mortgageAttament) throws JsonParseException,
			JsonMappingException, ParseException, IOException;

	void report(ToEvaReportVo evaReport, MortgageAttamentVo mortgageAttament)
			throws ClientProtocolException, IOException;

	void saveEguBank(BankSearchVo bankSearch) throws JsonParseException,
			JsonMappingException, ParseException, IOException;
}

package com.centaline.trans.income.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.income.service.ProfitService;

@Controller
@RequestMapping(value = "/profit")
public class ProfitController {

	@Autowired
	private ProfitService profitService;

	/**
	 * 功能：收益 作者：zhangxb16
	 */
	@RequestMapping(value = "selectTemplate")
	public void selectTemplate(HttpServletRequest request,
			HttpServletResponse response) {

		/*
		 * 
		 * try { // int overInsert=profitService.profitOperate(); if (overInsert
		 * > 0) { } else { throw new BusinessException(
		 * "往[T_TS_OVERRIDING]表中写入数据失败, 请刷新后再次尝试！"); } } catch
		 * (BusinessException ex) { ex.printStackTrace(); }
		 */
	}

}

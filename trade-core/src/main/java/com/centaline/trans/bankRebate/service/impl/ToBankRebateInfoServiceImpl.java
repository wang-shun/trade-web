package com.centaline.trans.bankRebate.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.repository.ToBankRebateInfoMapper;
import com.centaline.trans.bankRebate.service.ToBankRebateInfoService;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;

@Service
public class ToBankRebateInfoServiceImpl implements ToBankRebateInfoService {
	
	@Autowired(required = true)
	private ToBankRebateInfoMapper toBankRebateInfoMapper;
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int insert(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.insertSelective(record);
	}

	@Override
	public ToBankRebateInfo selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToBankRebateInfo record) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteRebateInfoByGuaranteeCompId(String guaCompId) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.deleteRebateInfoByGuaranteeCompId(guaCompId);
	}

	@Override
	public List<ToBankRebateInfo> selectRebateInfoByGuaranteeCompId(String guaranteeCompId) {
		// TODO Auto-generated method stub
		return toBankRebateInfoMapper.selectRebateInfoByGuaranteeCompId(guaranteeCompId);
	}
	
	/**
	 * 保存修改的银行返利批次号
	 */
	@Override
	public void saveToBankRebateInfoVO(ToBankRebateInfoVO toBankRebateInfoVO) {
		// TODO Auto-generated method stub
		List<ToBankRebateInfo> toBankRebateInfoList = toBankRebateInfoVO.getToBankRebateInfoList();
		for (ToBankRebateInfo toBankRebateInfo : toBankRebateInfoList) {
			toBankRebateInfoMapper.updateByPrimaryKeySelective(toBankRebateInfo);
		}
		
	}
	
	/**
	 * 保存新增的银行返利批次号
	 */
	@Override
	public void saveBankRebateInfoVO(ToBankRebateInfoVO info,String guaranteeCompId) {
		// TODO Auto-generated method stub
		List<ToBankRebateInfo> toBankRebateInfoList = info.getToBankRebateInfoList();
		for (ToBankRebateInfo toBankRebateInfo : toBankRebateInfoList) {
			ToBankRebateInfo record = new ToBankRebateInfo();
			record.setBankName(toBankRebateInfo.getBankName());
			record.setCcaiCode(toBankRebateInfo.getCcaiCode());
			record.setGuaranteeCompId(guaranteeCompId);
			record.setRebateMoney(toBankRebateInfo.getRebateMoney());
			record.setRebateWarrant(toBankRebateInfo.getRebateWarrant());
			record.setRebateBusiness(toBankRebateInfo.getRebateBusiness());
			if(!StringUtils.isEmpty(record.getCcaiCode())) {
				toBankRebateInfoMapper.insertSelective(record);
			}
		}
		
	}
	
	/**
	 * 返利导入模板
	 */
	@Override
	@SuppressWarnings("resource")
	public void exportMatrixLeaderSheet(OutputStream out) {
		// TODO Auto-generated method stub
		
		HSSFWorkbook workbook = new HSSFWorkbook(); // 声明一个工作薄  
	    HSSFSheet sheet = workbook.createSheet("Sheet1");  // 生成一个表格     
	    sheet.setDefaultColumnWidth(20);// 设置表格默认列宽度为30个字节     
	    HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short)(15.625*40));  
	      
	    HSSFCell cell = row.createCell(0);  
	    cell.setCellValue("成交编号");  
	      
	    cell = row.createCell(1);  
	    cell.setCellValue("银行");  
	      
	    cell = row.createCell(2);  
	    cell.setCellValue("返利金额");  
	      
	    cell = row.createCell(3);  
	    cell.setCellValue("权证返利金额");  
	      
	    cell = row.createCell(4);  
	    cell.setCellValue("业务返利金额");  
	      
	    HSSFCellStyle style = workbook.createCellStyle();  
	    style.setWrapText(true);  
	      
	    try {  
	        workbook.write(out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
		
	}
	
	
	
	
}

package com.centaline.trans.task.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.task.entity.ToApproveRecord;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface ToApproveRecordMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToApproveRecord record);

    int insertSelective(ToApproveRecord record);

    ToApproveRecord selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToApproveRecord record);

    int updateByPrimaryKeyWithBLOBs(ToApproveRecord record);

    int updateByPrimaryKey(ToApproveRecord record);
    
    ToApproveRecord findApproveRecordByRecord(ToApproveRecord record);
    
    ToApproveRecord findApproveRecordByRecordForSpvApply(ToApproveRecord record);   
        
    List<String> findApproveRecordByList(ToApproveRecord record);

	ToApproveRecord findLastApproveRecord(ToApproveRecord toApproveRecord);

    /**
     * 根据案件编号和approve_type删除审批记录
     * @param caseCode
     * @param approveType
     * @author yinchao 2017-10-18
     */
	void deleteByCaseCodeAndType(@Param("caseCode") String caseCode,@Param("approveType") String approveType);
}
package com.centaline.trans.common.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;

/**
 * 分页数据  单个子段批量快速查询接口
 * @author zhoujp7
 *
 */
@MyBatisRepository
public interface KeyValueMapper {
	
	public List<Map<String, Object>> queryToPropertyInfoByCaseCode(@Param("keys") List<Map<String, Object>> keys,@Param("dictType") String dictType);
	List<Map<String, Object>> queryGuestInfoCustomDict(@Param("keys") List<Map<String, Object>> keys,@Param("dictType") String dictType);
	public List<Map<String, Object>> queryGuestInfoPhoneCustomDict(@Param("keys") List<Map<String, Object>> keys,@Param("dictType") String dictType);
	public List<Map<String, Object>> queryProcessorNameCustomDict(@Param("keys") List<Map<String, Object>> keys);

}

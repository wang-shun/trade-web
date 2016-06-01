package com.centaline.trans.mgr.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.mgr.entity.TsFinOrg;

@MyBatisRepository
public interface TsFinOrgMapper {
    int insert(TsFinOrg record);

    int insertSelective(TsFinOrg record);
    
    int update(TsFinOrg record);
    
    int delete(Long pkid);
    
    TsFinOrg findBankByFinOrg(String orgCode);
    
    TsFinOrg findByPkid(Long pkid);

    TsFinOrg findTsFinOrgByFinOrgCode(@Param("finOrgCode")String finOrgCode,@Param("pkid")Long pkid);

    /**
     * 查询egu分行列表
     * @param evaCompanyCode
     * @return
     */
    List<TsFinOrg> findEguParentBankList(@Param("evaCompanyCode") String evaCompanyCode,@Param("tag")String tag,@Param("nowCode") String nowCode);
    
    /**
     * 查询egu支行列表
     * @param faFinOrgCode
     * @param evaCompanyCode
     * @return
     */
    List<TsFinOrg> findEguBankListByParentCode(@Param("faFinOrgCode")String faFinOrgCode,@Param("evaCompanyCode") String evaCompanyCode,@Param("tag")String tag,@Param("nowCode") String nowCode);
    
    /**
     * 查询所有分行下拉列表
     * @param evaCompanyCode
     * @return
     */
    List<TsFinOrg> findParentBankList(@Param("tag")String tag,@Param("nowCode")String nowCode);
    
    /**
     * 查询支行下拉列表
     * @param faFinOrgCode
     * @param evaCompanyCode
     * @return
     */
    List<TsFinOrg> findBankListByParentCode(@Param("faFinOrgCode")String faFinOrgCode,@Param("tag")String tag,@Param("nowCode") String q);
    
    /**
     * 根据类型查询评估公司
     * @param supType
     * @return
     */
    List<TsFinOrg> findBySupCat(String supType);
    
    /**
     * 根据支行编码查询分行
     * @param finOrgCode
     * @return
     */
    TsFinOrg findParentBank(String finOrgCode);
    
    /**
     * 查询所有支行
     * @return
     */
    List<TsFinOrg> findBranchBank();
    
    /**
     * 查询所有供应商
     * @return
     */
    List<TsFinOrg> findAllFinOrg();
    
    List<TsFinOrg> queryFinOrgNameLike(@Param("finOrgName")String finOrgName) ;
}
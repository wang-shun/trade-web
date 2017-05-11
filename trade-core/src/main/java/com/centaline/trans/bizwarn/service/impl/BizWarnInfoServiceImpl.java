/**
 * 
 */
package com.centaline.trans.bizwarn.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.repository.BizWarnInfoMapper;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;

/**
 * 商贷预警业务接口实现
 * 
 * @author yinjf2
 * @date 2016年8月5日
 */
@Service
public class BizWarnInfoServiceImpl implements BizWarnInfoService
{

    @Autowired
    private BizWarnInfoMapper bizWarnInfoMapper;

    @Override
    public BizWarnInfo getBizWarnInfoByCaseCode(String caseCode)
    {
        BizWarnInfo bizWarnInfo = bizWarnInfoMapper.selectByCaseCode(caseCode);

        return bizWarnInfo;
    }

    @Override
    public int insertSelective(BizWarnInfo bizWarnInfo)
    {

        return bizWarnInfoMapper.insertSelective(bizWarnInfo);
    }

    @Override
    public int updateByCaseCode(BizWarnInfo bizWarnInfo)
    {
        return bizWarnInfoMapper.updateByCaseCode(bizWarnInfo);
    }

    @Override
    public int deleteByCaseCode(String caseCode)
    {
        return bizWarnInfoMapper.deleteByCaseCode(caseCode);
    }

    @Override
    public int updateStatusByCaseCode(BizWarnInfo bizWarnInfo)
    {
        return bizWarnInfoMapper.updateStatusByCaseCode(bizWarnInfo);
    }

    @Override
    public int getAllBizwarnCountByTeam(String userLoginName)
    {
        return bizWarnInfoMapper.getAllBizwarnCountByTeam(userLoginName);
    }

    @Override
    public int getAllBizwarnCountByDistinct(String currentOrgId)
    {
        return bizWarnInfoMapper.getAllBizwarnCountByDistinct(currentOrgId);
    }

    @Override
    public int relieveWarn(Map<String, Object> map)
    {
        int k = 0;
        try
        {
            BizWarnInfo bizWarnInfo = bizWarnInfoMapper.selectBizWarnInfoByMap(map);

            bizWarnInfo.setStatus("1");
            bizWarnInfo.setRelieveTime(new Date());

            k = bizWarnInfoMapper.updateStatusByCaseCode(bizWarnInfo);

        }
        catch (BusinessException e)
        {
            throw new BusinessException("案件取消预警异常！");
        }
        return k;
    }

    @Override
    public void updateStatusByCaseCodeAndWarnType(BizWarnInfo bizWarnInfo)
    {
        bizWarnInfoMapper.updateStatusByCaseCodeAndWarnType(bizWarnInfo);
    }

}

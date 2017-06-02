package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TgGuestInfo;

@MyBatisRepository
public interface TgGuestInfoMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TgGuestInfo record);

    int insertSelective(TgGuestInfo record);

    TgGuestInfo selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TgGuestInfo record);

    int updateByPrimaryKey(TgGuestInfo record);
    
    List<TgGuestInfo> findTgGuestInfoByCaseCode(String caseCode);
    
    List<TgGuestInfo> findTgGuestInfosByCaseCode(TgGuestInfo record);
    
    TgGuestInfo findTgGuestInfoByGuestCode(String guestCode);
    
    
}
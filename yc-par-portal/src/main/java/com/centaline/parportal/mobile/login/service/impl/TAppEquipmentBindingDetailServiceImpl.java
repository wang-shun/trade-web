package com.centaline.parportal.mobile.login.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;
import com.centaline.parportal.mobile.login.repository.TAppEquipmentBindingDetailMapper;
import com.centaline.parportal.mobile.login.service.TAppEquipmentBindingDetailService;

@Service
public class TAppEquipmentBindingDetailServiceImpl implements TAppEquipmentBindingDetailService {

    @Autowired
    private TAppEquipmentBindingDetailMapper tAppEquipmentBindingDetailMapper;

    //    @Autowired
    //    //    private QuerysParseService               querysParseService;
    //
    //    @Resource(name = "quickGridService")
    //    private QuickGridService                 quickGridService;

    @Autowired
    private SqlSessionTemplate               session;

    @Override
    public TAppEquipmentBindingDetail selectByPrimaryKey(Long id) {
        TAppEquipmentBindingDetail tAppEquipmentBindingDetail = tAppEquipmentBindingDetailMapper
            .selectByPrimaryKey(id);
        return tAppEquipmentBindingDetail;
    }

    @Override
    public TAppEquipmentBindingDetail selectByUserName(String appUserName) {
        //        if (StringUtils.isNotEmpty(appUserName)) {
        //            Map<String, Object> paramater = new HashMap<String, Object>();
        //            paramater.put("appUserName", appUserName);
        //            paramater.put("status", 1); //1有效 ，0无效
        //            JQGridParam gp = new JQGridParam();
        //            gp.setPagination(false);
        //            gp.setQueryId("loginUnbindingList");
        //            querysParseService.reloadFile();
        //            Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp,
        //                paramater, null);
        //            List<Map<String, Object>> contentList = returnPage.getContent();
        //            TAppEquipmentBindingDetail tAppEquipmentBindingDetail = new TAppEquipmentBindingDetail();
        //            if (CollectionUtils.isNotEmpty(contentList)) {
        //                Map<String, Object> mp = contentList.get(0);
        //                String equipmentId = mp.get("equipmentId").toString();
        //                String username = mp.get("userName").toString();
        //                tAppEquipmentBindingDetail.setEquipmentId(equipmentId);
        //                tAppEquipmentBindingDetail.setUserName(username);
        //                return tAppEquipmentBindingDetail;
        //            }
        //        }
        return null;
    }

    @Override
    public TAppEquipmentBindingDetail selectByEquipmentId(String equipmentId) {
        //        if (StringUtils.isNotEmpty(equipmentId)) {
        //            Map<String, Object> paramater = new HashMap<String, Object>();
        //            paramater.put("equipmentId", equipmentId);
        //            paramater.put("status", 1); //1有效 ，0无效
        //            JQGridParam gp = new JQGridParam();
        //            gp.setPagination(false);
        //            gp.setQueryId("loginUnbindingList");
        //            querysParseService.reloadFile();
        //            Page<Map<String, Object>> returnPage = quickGridService.findPageForSqlServer(gp,
        //                paramater, null);
        //            List<Map<String, Object>> contentList = returnPage.getContent();
        //            TAppEquipmentBindingDetail tAppEquipmentBindingDetail = new TAppEquipmentBindingDetail();
        //            if (CollectionUtils.isNotEmpty(contentList)) {
        //                Map<String, Object> mp = contentList.get(0);
        //                String userName = mp.get("userName").toString();
        //                tAppEquipmentBindingDetail.setUserName(userName);
        //                return tAppEquipmentBindingDetail;
        //            }
        //        }
        return null;
    }

    @Override
    public boolean insert(TAppEquipmentBindingDetail tAppEquipmentBindingDetail) {
        int result = tAppEquipmentBindingDetailMapper.insert(tAppEquipmentBindingDetail);
        boolean bool = false;
        if (result > 0) {
            bool = true;
        }
        return bool;
    }

    @Override
    public boolean updateStatusByPrimaryKey(TAppEquipmentBindingDetail tAppEquipmentBindingDetail) {
        int result = tAppEquipmentBindingDetailMapper
            .updateStatusByPrimaryKey(tAppEquipmentBindingDetail);
        boolean bool = false;
        if (result > 0) {
            bool = true;
        }
        return bool;
    }

}

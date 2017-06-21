package com.centaline.trans.common.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;

/**
 * 根据案件编号获取不同环节的时效信息
 * 
 * @author yinjf
 *
 */
public class QuickQueryCaseEfficientInfoServiceImpl implements CustomDictService
{
    @Autowired
    private QuickQueryJdbcTemplate jdbcTemplate;

    private static String sql = "SELECT DISPATCH_TIME AS dispatchTime,FIRSTFOLLOW_TIME AS firstFollowTime,FIRSTFOLLOW_EFF AS firstFollowEff,FIRSTFOLLOW_DLY AS firstFollowDly,"
            + "SIGN_TIME AS signTime,SIGN_EFF AS signEff,SIGN_DLY AS signDly," + "GUOHU_TIME AS guohuTime,GUOHU_EFF AS guohuEff,GUOHU_DLY AS guohuDly,"
            + "CASECLOSE_TIME AS caseCloseTime,CASECLOSE_EFF AS caseCloseEff,CASECLOSE_DLY AS caseCloseDly"
            + " FROM sctrans.T_TS_CASE_EFFICIENT WHERE CASE_CODE = :caseCode";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys)
    {

        Map<String, Object> params = null;

        for (Map<String, Object> key : keys)
        {
            if (key == null)
            {
                continue;
            }

            Object caseCodeObj = key.get("caseCode");

            if (caseCodeObj != null)
            {
                // 首次跟进实际操作时间
                Date firstFollowDateTime = null;
                // 签约实际操作时间
                Date signDateTime = null;
                // 过户实际操作时间
                Date guohuDateTime = null;
                // 结案实际操作时间
                Date caseCloseDateTime = null;

                // 格式化首次跟进实际操作时间
                String formatFirstFollowDateTime = null;
                // 格式化签约实际操作时间
                String formatSignDateTime = null;
                // 格式化过户实际操作时间
                String formatGuohuDateTime = null;
                // 格式化结案实际操作时间
                String formatCaseCloseDateTime = null;

                // 签约时效信息
                String signEffInfo = "";
                // 过户时效信息
                String guohuEffInfo = "";
                // 结案时效信息
                String caseCloseEffInfo = "";
                // 处于哪个环节,默认是首次跟进
                String inProgress = "firstFollow";

                params = new HashedMap<String, Object>();

                String caseCode = caseCodeObj.toString();
                params.put("caseCode", caseCode);
                Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, params);

                // 首次跟进实际操作时间obj对象
                Object firstFollowTimeObj = resultMap.get("firstFollowTime");
                // 签约实际操作时间
                Object signTimeObj = resultMap.get("signTime");
                // 过户实际操作时间
                Object guohuTimeObj = resultMap.get("guohuTime");
                // 结案实际操作时间
                Object caseCloseTimeObj = resultMap.get("caseCloseTime");

                if (firstFollowTimeObj != null)
                {
                    inProgress = "sign";
                    firstFollowDateTime = (Date) firstFollowTimeObj;
                    formatFirstFollowDateTime = sdf.format(firstFollowDateTime);
                }

                if (signTimeObj != null)
                {
                    inProgress = "guohu";
                    signDateTime = (Date) signTimeObj;
                    formatSignDateTime = sdf.format(signDateTime);
                }

                if (guohuTimeObj != null)
                {
                    inProgress = "caseClose";
                    guohuDateTime = (Date) guohuTimeObj;
                    formatGuohuDateTime = sdf.format(guohuDateTime);
                }

                if (caseCloseTimeObj != null)
                {
                    inProgress = "completed";
                    caseCloseDateTime = (Date) caseCloseTimeObj;
                    formatCaseCloseDateTime = sdf.format(caseCloseDateTime);
                }

                // 当前时间
                Date currentTime = new Date();
                // 派单时间
                Date dispatchTime = (Date) resultMap.get("dispatchTime");

                // 获取首次跟进时效信息
                String firstFollowEffInfo = getFirstFollowEffInfo(resultMap, currentTime, dispatchTime);

                if (firstFollowDateTime != null)
                    // 获取签约环节时效信息
                    signEffInfo = getSignEffInfo(resultMap, currentTime, firstFollowDateTime);

                if (signTimeObj != null)
                    // 获取过户环节时效信息
                    guohuEffInfo = getGuohuEffInfo(resultMap, currentTime, signDateTime);

                // 如果过户环节没完成,直接跳转到下一个案件
                if (guohuTimeObj != null)
                    // 获取结案环节时效信息
                    caseCloseEffInfo = getCaseCloseEffInfo(resultMap, currentTime, guohuDateTime);

                key.put("firstFollowEffInfo", firstFollowEffInfo);
                key.put("signEffInfo", signEffInfo);
                key.put("guohuEffInfo", guohuEffInfo);
                key.put("caseCloseEffInfo", caseCloseEffInfo);
                key.put("firstFollowDateTime", formatFirstFollowDateTime);
                key.put("signDateTime", formatSignDateTime);
                key.put("guohuDateTime", formatGuohuDateTime);
                key.put("caseCloseDateTime", formatCaseCloseDateTime);
                key.put("inProgress", inProgress);
                key.put("totalEff",
                        resultMap.get("totalTime") + "-" + resultMap.get("totalOverdueTime") + "-" + resultMap.get("totalEff") + "-" + resultMap.get("totalDly"));
                key.put("isCaseCloseOverdue", (Boolean) resultMap.get("isCaseCloseOverdue"));
                key.put("isGuohuOverdue", (Boolean) resultMap.get("isGuohuOverdue"));
                key.put("isSignOverdue", (Boolean) resultMap.get("isSignOverdue"));
                key.put("isFirstFollowOverdue", (Boolean) resultMap.get("isFirstFollowOverdue"));
            }

        }
        return keys;
    }

    /**
     * 获取结案环节时效信息
     * 
     * @param resultMap
     *            结果集
     * @param currentTime
     *            当前时间
     * @param guohuDateTime
     *            过户实际操作时间
     * @return 结案环节时效信息
     */
    private String getCaseCloseEffInfo(Map<String, Object> resultMap, Date currentTime, Date guohuDateTime)
    {
        // 结案环节实际操作时间
        Object caseCloseTimeObj = resultMap.get("caseCloseDateTime");

        // 结案环节用时
        int caseCloseTime = 0;
        // 结案是否逾期
        boolean isCaseCloseOverdue = false;

        // 如果结案环节有实际操作时间,则结案环节用时=结案环节实际操作时间-过户实际操作时间
        // 如果结案环节无实际操作时间,则结案环节用时=当前时间-过户实际操作时间
        if (caseCloseTimeObj != null)
        {
            Date caseCloseDateTime = (Date) caseCloseTimeObj;

            caseCloseTime = Integer.parseInt(String.valueOf(calDay(caseCloseDateTime, guohuDateTime)));
        }
        else
        {
            caseCloseTime = Integer.parseInt(String.valueOf(calDay(currentTime, guohuDateTime)));
        }

        // 结案环节时效标准
        int caseCloseEff = (Integer) resultMap.get("caseCloseEff");
        // 结案环节延期次数
        int caseCloseDly = (Integer) resultMap.get("caseCloseDly");
        // 结案环节逾期时间
        int caseCloseOverdueTime = caseCloseEff - caseCloseTime;

        if (caseCloseOverdueTime < 0)
        {
            isCaseCloseOverdue = true;
            caseCloseOverdueTime = Math.abs(caseCloseOverdueTime);
        }
        else
        {
            caseCloseOverdueTime = 0;
        }

        String caseCloseEffInfo = caseCloseTime + "-" + caseCloseOverdueTime + "-" + caseCloseEff + "-" + caseCloseDly;

        // 累计操作
        int totalTime = (Integer) resultMap.get("totalTime") + caseCloseTime;
        int totalOverdueTime = (Integer) resultMap.get("totalOverdueTime") + caseCloseOverdueTime;
        int totalEff = (Integer) resultMap.get("totalEff") + caseCloseEff;
        int totalDly = (Integer) resultMap.get("totalDly") + caseCloseDly;

        resultMap.put("totalTime", totalTime);
        resultMap.put("totalOverdueTime", totalOverdueTime);
        resultMap.put("totalEff", totalEff);
        resultMap.put("totalDly", totalDly);
        resultMap.put("isCaseCloseOverdue", isCaseCloseOverdue);

        return caseCloseEffInfo;
    }

    /**
     * 获取过户环节时效信息
     * 
     * @param resultMap
     *            结果集
     * @param currentTime
     *            当前时间
     * @param signDateTime
     *            签约实际操作时间
     * @return 过户环节时效信息
     */
    private String getGuohuEffInfo(Map<String, Object> resultMap, Date currentTime, Date signDateTime)
    {
        // 过户环节实际操作时间
        Object guohuTimeObj = resultMap.get("guohuTime");

        // 过户环节用时
        int guohuTime = 0;
        // 过户是否逾期
        boolean isGuohuOverdue = false;

        // 如果过户环节有实际操作时间,则过户环节用时=过户环节实际操作时间-签约实际操作时间
        // 如果过户环节无实际操作时间,则过户环节用时=当前时间-签约实际操作时间
        if (guohuTimeObj != null)
        {
            Date guohuDateTime = (Date) guohuTimeObj;

            guohuTime = Integer.parseInt(String.valueOf(calDay(guohuDateTime, signDateTime)));
        }
        else
        {
            guohuTime = Integer.parseInt(String.valueOf(calDay(currentTime, signDateTime)));
        }

        // 过户环节时效标准
        int guohuEff = (Integer) resultMap.get("guohuEff");
        // 过户环节延期次数
        int guohuDly = (Integer) resultMap.get("guohuDly");
        // 过户环节逾期时间
        int guohuOverdueTime = guohuEff - guohuTime;

        if (guohuOverdueTime < 0)
        {
            isGuohuOverdue = true;
            guohuOverdueTime = Math.abs(guohuOverdueTime);
        }
        else
        {
            guohuOverdueTime = 0;
        }

        String guohuEffInfo = guohuTime + "-" + guohuOverdueTime + "-" + guohuEff + "-" + guohuDly;

        // 累计操作
        int totalTime = (Integer) resultMap.get("totalTime") + guohuTime;
        int totalOverdueTime = (Integer) resultMap.get("totalOverdueTime") + guohuOverdueTime;
        int totalEff = (Integer) resultMap.get("totalEff") + guohuEff;
        int totalDly = (Integer) resultMap.get("totalDly") + guohuDly;

        resultMap.put("totalTime", totalTime);
        resultMap.put("totalOverdueTime", totalOverdueTime);
        resultMap.put("totalEff", totalEff);
        resultMap.put("totalDly", totalDly);
        resultMap.put("isGuohuOverdue", isGuohuOverdue);

        return guohuEffInfo;
    }

    /**
     * 获取签约环节时效信息
     * 
     * @param resultMap
     *            结果集
     * @param currentTime
     *            当前时间
     * @param firstFollowTime
     *            首次跟进实际操作时间
     * @return 签约环节时效信息
     */
    private String getSignEffInfo(Map<String, Object> resultMap, Date currentTime, Date firstFollowTime)
    {
        // 签约实际操作时间
        Object signTimeObj = resultMap.get("signTime");

        // 签约环节用时
        int signTime = 0;
        // 签约是否逾期
        boolean isSignOverdue = false;

        // 如果签约环节有实际操作时间,则签约环节用时=签约环节实际操作时间-首次跟进实际操作时间
        // 如果签约环节无实际操作时间,则签约环节用时=当前时间-首次跟进实际操作时间
        if (signTimeObj != null)
        {
            Date signDateTime = (Date) signTimeObj;

            signTime = Integer.parseInt(String.valueOf(calDay(signDateTime, firstFollowTime)));
        }
        else
        {
            signTime = Integer.parseInt(String.valueOf(calDay(currentTime, firstFollowTime)));
        }

        // 签约环节时效标准
        int signEff = (Integer) resultMap.get("signEff");
        // 签约环节延期次数
        int signDly = (Integer) resultMap.get("signDly");

        // 签约环节逾期时间
        int signOverdueTime = signEff - signTime;

        if (signOverdueTime < 0)
        {
            isSignOverdue = true;
            signOverdueTime = Math.abs(signOverdueTime);
        }
        else
        {
            signOverdueTime = 0;
        }

        String signEffInfo = signTime + "-" + signOverdueTime + "-" + signEff + "-" + signDly;

        // 累计操作
        int totalTime = (Integer) resultMap.get("totalTime") + signTime;
        int totalOverdueTime = (Integer) resultMap.get("totalOverdueTime") + signOverdueTime;
        int totalEff = (Integer) resultMap.get("totalEff") + signEff;
        int totalDly = (Integer) resultMap.get("totalDly") + signDly;

        resultMap.put("totalTime", totalTime);
        resultMap.put("totalOverdueTime", totalOverdueTime);
        resultMap.put("totalEff", totalEff);
        resultMap.put("totalDly", totalDly);
        resultMap.put("isSignOverdue", isSignOverdue);

        return signEffInfo;
    }

    /**
     * 获取首次跟进环节时效信息
     * 
     * @param resultMap
     *            结果集信息
     * @param currentTime
     *            当前时间
     * @param dispatchTime
     *            分单时间
     * @return 首次跟进时效信息
     */
    private String getFirstFollowEffInfo(Map<String, Object> resultMap, Date currentTime, Date dispatchTime)
    {
        // 首次跟进实际时间
        Object firstFollowTimeObj = resultMap.get("firstFollowTime");

        // 首次跟进环节用时
        int firstFollowTime = 0;
        // 首次跟进是否逾期
        boolean isFirstFollowOverdue = false;

        // 如果首次跟进有实际操作时间,则首次跟进环节用时=首次跟进实际操作时间-派单时间
        // 如果首次跟进无实际操作时间,则首次跟进环节用时=当前时间-派单时间
        if (firstFollowTimeObj != null)
        {
            Date firstFollowDateTime = (Date) firstFollowTimeObj;

            firstFollowTime = Integer.parseInt(String.valueOf(calDay(firstFollowDateTime, dispatchTime)));
        }
        else
        {
            firstFollowTime = Integer.parseInt(String.valueOf(calDay(currentTime, dispatchTime)));
        }

        // 首次跟进时效标准
        int firstFollowEff = (Integer) resultMap.get("firstFollowEff");
        // 首次跟进延期次数
        int firstFollowDly = (Integer) resultMap.get("firstFollowDly");

        // 首次跟进逾期时间
        int firstFollowOverdueTime = firstFollowEff - firstFollowTime;

        if (firstFollowOverdueTime < 0)
        {
            isFirstFollowOverdue = true;
            firstFollowOverdueTime = Math.abs(firstFollowOverdueTime);
        }
        else
        {
            firstFollowOverdueTime = 0;
        }

        String firstFollowEffInfo = firstFollowTime + "-" + firstFollowOverdueTime + "-" + firstFollowEff + "-" + firstFollowDly;

        // 累计操作
        resultMap.put("totalTime", firstFollowTime);
        resultMap.put("totalOverdueTime", firstFollowOverdueTime);
        resultMap.put("totalEff", firstFollowEff);
        resultMap.put("totalDly", firstFollowDly);
        resultMap.put("isFirstFollowOverdue", isFirstFollowOverdue);

        return firstFollowEffInfo;
    }

    /**
     * 计算俩时间相差天数
     * 
     * @param beginTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return 相差天数
     */
    public long calDay(Date beginTime, Date endTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long days = 0;

        try
        {
            Date beginDateTime = sdf.parse(sdf.format(beginTime));
            Date endDateTime = sdf.parse(sdf.format(endTime));

            long diff = beginDateTime.getTime() - endDateTime.getTime();// 这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return days;
    }

    @Override
    public Boolean getIsBatch()
    {
        return true;
    }

    public Boolean isCacheable()
    {
        return false;
    }

}

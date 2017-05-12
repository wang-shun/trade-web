package com.centaline.trans.common.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.centaline.trans.common.enums.LampEnum;

@Service
public class QuickQueryLightServiceImpl implements CustomDictService
{
    @Autowired
    private QuickQueryJdbcTemplate jdbcTemplate;

    // 查询红绿灯规则sql语句
    private static final String lightSql = "SELECT COLOR,DELAYTIME FROM sctrans.LAMP_RULE";

    public List<Map<String, Object>> findDicts(List<Map<String, Object>> keys)
    {
        if (keys == null)
        {
            return null;
        }

        // 查询红绿灯规则
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Map<String, Object>> lightMapList = jdbcTemplate.queryForList(lightSql, paramMap);

        // 红灯延迟天数
        int redDay = getDelayDaysByLight(lightMapList, "0");
        // 黄灯延迟天数
        int yellowDay = getDelayDaysByLight(lightMapList, "1");
        // 绿灯延迟天数
        int greenDay = getDelayDaysByLight(lightMapList, "2");

        String val = "";
        for (Map<String, Object> map : keys)
        {
            Object todoTime = map.get("todoTime");
            if (null == todoTime || !(todoTime instanceof Date))
            {
                map.put(DICTVALCOL, val);
                continue;
            }

            // 预计执行时间
            Date date = (Date) todoTime;

            // 当前时间
            Date currentTime = new Date();

            // 计算当前时间跟预计执行时间相差天数
            long days = calDay(currentTime, date);

            // 根据相差天数显示对应的红绿灯
            val = getLightByDelayDays(days, redDay, yellowDay, greenDay);

            map.put(DICTVALCOL, val);
        }

        return keys;
    }

    /**
     * 根据延迟的天数判断出亮哪种类型的灯
     * 
     * @param delayDays
     *            延迟天数
     * @param redDay
     *            红灯延迟天数
     * @param yellowDay
     *            黄灯延迟天数
     * @return 红绿灯
     */
    public String getLightByDelayDays(long delayDays, int redDay, int yellowDay, int greenDay)
    {
        String light = "";

        if (delayDays >= redDay)
        {
            light = LampEnum.RED.toString();
        }
        else if (delayDays > yellowDay)
        {
            light = LampEnum.YELLOW.toString();
        }
        else if (delayDays >= greenDay)
        {
            light = LampEnum.GREEN.toString();
        }

        return light;
    }

    /**
     * 计算俩时间相差天数
     * 
     * @param currentTime
     *            当前时间
     * @param expectTime
     *            预计执行时间
     * @return 相差天数
     */
    public long calDay(Date currentTime, Date expectTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long days = 0;

        try
        {
            Date currentDateTime = sdf.parse(sdf.format(currentTime));
            Date expectedDateTime = sdf.parse(sdf.format(expectTime));

            long diff = currentDateTime.getTime() - expectedDateTime.getTime();// 这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return days;
    }

    /**
     * 根据红绿灯类型获取对应的延迟天数
     * 
     * @param lightMapList
     *            红绿灯规则列表
     * @param lightType
     *            红绿灯类型,0:红灯,1:黄灯,2:绿灯
     * @return 延迟天数
     */
    private int getDelayDaysByLight(List<Map<String, Object>> lightMapList, String lightType)
    {
        int delayDays = 0;

        if (lightMapList != null && lightMapList.size() > 0)
        {
            for (Map<String, Object> lightMap : lightMapList)
            {
                String color = (String) lightMap.get("COLOR");
                int delayDay = Integer.parseInt((String) lightMap.get("DELAYTIME"));

                if (lightType.equals(color))
                {
                    delayDays = delayDay;
                    break;
                }
            }
        }

        return delayDays;
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

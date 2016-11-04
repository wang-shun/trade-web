package com.centaline.trans.common.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuan7 on 2016/10/19.
 */
public class BaseVo {

    private Boolean status;
    private Object data;
    private String info;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static BaseVo success() {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(true);
        baseVo.setInfo("请求成功");
        return baseVo;
    }

    public static BaseVo success(String str) {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(true);
        baseVo.setInfo(str);
        return baseVo;
    }

    public static BaseVo fail(String s) {
        BaseVo baseVo = new BaseVo();
        baseVo.setStatus(false);
        baseVo.setInfo(s);
        return baseVo;
    }
}

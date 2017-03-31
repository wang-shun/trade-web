package com.centaline.trans.common.entity;

public class LampRule {
    private Long pkId;

    private String color;

    private Integer delaytime;

    public Long getPkId() {
        return pkId;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Integer getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(Integer delaytime) {
        this.delaytime = delaytime;
    }
}
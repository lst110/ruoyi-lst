package com.ruoyi.wx.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预警处理对象 wx_warn
 * 
 * @author lst
 * @date 2023-05-11
 */
public class WxWarn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 预警二维码id */
    @Excel(name = "预警二维码id")
    private Long warn_qrid;

    /** 预警扫码次数 */
    @Excel(name = "预警扫码次数")
    private Long warn_num;

    /** 预警扫码ip */
    @Excel(name = "预警扫码ip")
    private String warn_ip;

    /** 预警状态（是否已处理） */
    @Excel(name = "预警状态", readConverterExp = "是=否已处理")
    private String warn_state;

    /** 发生预警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生预警时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warn_time;

    /** 预警触发用户id */
    @Excel(name = "预警触发用户id")
    private Long warn_user;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setwarn_qrid(Long warn_qrid) 
    {
        this.warn_qrid = warn_qrid;
    }

    public Long getwarn_qrid() 
    {
        return warn_qrid;
    }
    public void setwarn_num(Long warn_num) 
    {
        this.warn_num = warn_num;
    }

    public Long getwarn_num() 
    {
        return warn_num;
    }
    public void setwarn_ip(String warn_ip) 
    {
        this.warn_ip = warn_ip;
    }

    public String getwarn_ip() 
    {
        return warn_ip;
    }
    public void setwarn_state(String warn_state) 
    {
        this.warn_state = warn_state;
    }

    public String getwarn_state() 
    {
        return warn_state;
    }
    public void setwarn_time(Date warn_time) 
    {
        this.warn_time = warn_time;
    }

    public Date getwarn_time() 
    {
        return warn_time;
    }
    public void setwarn_user(Long warn_user) 
    {
        this.warn_user = warn_user;
    }

    public Long getwarn_user() 
    {
        return warn_user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warn_qrid", getwarn_qrid())
            .append("warn_num", getwarn_num())
            .append("warn_ip", getwarn_ip())
            .append("warn_state", getwarn_state())
            .append("warn_time", getwarn_time())
            .append("warn_user", getwarn_user())
            .toString();
    }
}

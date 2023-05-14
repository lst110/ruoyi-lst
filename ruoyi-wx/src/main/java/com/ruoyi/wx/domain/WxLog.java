package com.ruoyi.wx.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 扫码记录对象 wx_log
 * 
 * @author lst
 * @date 2023-05-12
 */
public class WxLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 码id */
    @Excel(name = "码id")
    private String code_id;

    /** 扫码用户id */
    @Excel(name = "扫码用户id")
    private String userId;

    /** 扫码ip */
    @Excel(name = "扫码ip")
    private String ip;

    /** 扫码次数 */
    @Excel(name = "扫码次数")
    private Long number;

    /** 扫码时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "扫码时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 首次扫码ip */
    @Excel(name = "首次扫码ip")
    private String createIp;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCode_id(String long1)
    {
        this.code_id = long1;
    }

    public String getCode_id()
    {
        return code_id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setCreateIp(String createIp) 
    {
        this.createIp = createIp;
    }

    public String getCreateIp() 
    {
        return createIp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code_id", getCode_id())
            .append("userId", getUserId())
            .append("ip", getIp())
            .append("number", getNumber())
            .append("time", getTime())
            .append("createIp", getCreateIp())
            .append("createTime", getCreateTime())
            .toString();
    }
}

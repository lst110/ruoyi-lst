package com.ruoyi.wx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 防伪码管理对象 wx_code
 * 
 * @author lst
 * @date 2023-05-12
 */
public class WxCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 码id */
    private String id;

    /** 图书id */
    @Excel(name = "图书id")
    private String book_id;

    /** 提交申请用户id */
    @Excel(name = "提交申请用户id")
    private String createUser;

    /** 码状态 */
    @Excel(name = "码状态")
    private String codeStatus;

    /** 首次扫码记录id */
    @Excel(name = "首次扫码记录id")
    private String createLog;

    /** 本次扫码记录id */
    @Excel(name = "本次扫码记录id")
    private String logId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setBook_id(String book_id)
    {
        this.book_id = book_id;
    }

    public String getBook_id()
    {
        return book_id;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setCodeStatus(String codeStatus) 
    {
        this.codeStatus = codeStatus;
    }

    public String getCodeStatus() 
    {
        return codeStatus;
    }
    public void setCreateLog(String createLog) 
    {
        this.createLog = createLog;
    }

    public String getCreateLog() 
    {
        return createLog;
    }
    public void setLogId(String logId) 
    {
        this.logId = logId;
    }

    public String getLogId() 
    {
        return logId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("book_id", getBook_id())
            .append("createUser", getCreateUser())
            .append("codeStatus", getCodeStatus())
            .append("createLog", getCreateLog())
            .append("logId", getLogId())
            .append("remark", getRemark())
            .toString();
    }
}

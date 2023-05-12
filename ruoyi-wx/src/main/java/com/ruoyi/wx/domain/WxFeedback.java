package com.ruoyi.wx.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 反馈处理对象 wx_feedback
 * 
 * @author lst
 * @date 2023-05-11
 */
public class WxFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedback_content;

    /** 反馈用户id */
    @Excel(name = "反馈用户id")
    private String feedback_user;

    /** 反馈时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedback_time;

    /** 是否处理 */
    @Excel(name = "是否处理")
    private String feedback_state;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setfeedback_content(String feedback_content) 
    {
        this.feedback_content = feedback_content;
    }

    public String getfeedback_content() 
    {
        return feedback_content;
    }
    public void setfeedback_user(String feedback_user) 
    {
        this.feedback_user = feedback_user;
    }

    public String getfeedback_user() 
    {
        return feedback_user;
    }
    public void setfeedback_time(Date feedback_time) 
    {
        this.feedback_time = feedback_time;
    }

    public Date getfeedback_time() 
    {
        return feedback_time;
    }
    public void setfeedback_state(String feedback_state) 
    {
        this.feedback_state = feedback_state;
    }

    public String getfeedback_state() 
    {
        return feedback_state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("feedback_content", getfeedback_content())
            .append("feedback_user", getfeedback_user())
            .append("feedback_time", getfeedback_time())
            .append("feedback_state", getfeedback_state())
            .toString();
    }
}

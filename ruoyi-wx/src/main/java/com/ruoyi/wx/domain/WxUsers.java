package com.ruoyi.wx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小程序用户对象 wx_users
 * 
 * @author lst
 * @date 2023-05-15
 */
public class WxUsers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 微信名称 */
    @Excel(name = "微信名称")
    private String nickame;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 用户角色 */
    @Excel(name = "用户角色")
    private Long role;

    /** 微信用户唯一ID */
    @Excel(name = "微信用户唯一ID")
    private String openid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNickame(String nickame) 
    {
        this.nickame = nickame;
    }

    public String getNickame() 
    {
        return nickame;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setRole(Long role) 
    {
        this.role = role;
    }

    public Long getRole() 
    {
        return role;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nickame", getNickame())
            .append("avatar", getAvatar())
            .append("role", getRole())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("openid", getOpenid())
            .toString();
    }
}

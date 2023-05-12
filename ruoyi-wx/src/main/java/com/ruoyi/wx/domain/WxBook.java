package com.ruoyi.wx.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书信息管理对象 wx_book
 * 
 * @author lst
 * @date 2023-05-12
 */
public class WxBook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图书id */
    private String id;

    /** 图书名称 */
    @Excel(name = "图书名称")
    private String bookName;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** ISBN */
    @Excel(name = "ISBN")
    private String isbn;

    /** 出版社 */
    @Excel(name = "出版社")
    private String publishing;

    /** 版次 */
    @Excel(name = "版次")
    private String edition;

    /** 出版时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** CIP */
    @Excel(name = "CIP")
    private String cip;

    /** 出版数量 */
    @Excel(name = "出版数量")
    private Long publishNumber;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setBookName(String bookName) 
    {
        this.bookName = bookName;
    }

    public String getBookName() 
    {
        return bookName;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setIsbn(String isbn) 
    {
        this.isbn = isbn;
    }

    public String getIsbn() 
    {
        return isbn;
    }
    public void setPublishing(String publishing) 
    {
        this.publishing = publishing;
    }

    public String getPublishing() 
    {
        return publishing;
    }
    public void setEdition(String edition) 
    {
        this.edition = edition;
    }

    public String getEdition() 
    {
        return edition;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setCip(String cip) 
    {
        this.cip = cip;
    }

    public String getCip() 
    {
        return cip;
    }
    public void setPublishNumber(Long publishNumber) 
    {
        this.publishNumber = publishNumber;
    }

    public Long getPublishNumber() 
    {
        return publishNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bookName", getBookName())
            .append("author", getAuthor())
            .append("isbn", getIsbn())
            .append("publishing", getPublishing())
            .append("edition", getEdition())
            .append("publishTime", getPublishTime())
            .append("cip", getCip())
            .append("publishNumber", getPublishNumber())
            .toString();
    }
}

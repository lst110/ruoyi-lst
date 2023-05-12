package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxBook;

/**
 * 图书信息管理Mapper接口
 * 
 * @author lst
 * @date 2023-05-12
 */
public interface WxBookMapper 
{
    /**
     * 查询图书信息管理
     * 
     * @param id 图书信息管理主键
     * @return 图书信息管理
     */
    public WxBook selectWxBookById(String id);

    /**
     * 查询图书信息管理列表
     * 
     * @param wxBook 图书信息管理
     * @return 图书信息管理集合
     */
    public List<WxBook> selectWxBookList(WxBook wxBook);

    /**
     * 新增图书信息管理
     * 
     * @param wxBook 图书信息管理
     * @return 结果
     */
    public int insertWxBook(WxBook wxBook);

    /**
     * 修改图书信息管理
     * 
     * @param wxBook 图书信息管理
     * @return 结果
     */
    public int updateWxBook(WxBook wxBook);

    /**
     * 删除图书信息管理
     * 
     * @param id 图书信息管理主键
     * @return 结果
     */
    public int deleteWxBookById(String id);

    /**
     * 批量删除图书信息管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxBookByIds(String[] ids);
}

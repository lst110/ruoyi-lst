package com.ruoyi.wx.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxBookMapper;
import com.ruoyi.wx.domain.WxBook;
import com.ruoyi.wx.service.IWxBookService;

/**
 * 图书信息管理Service业务层处理
 * 
 * @author lst
 * @date 2023-05-12
 */
@Service
public class WxBookServiceImpl implements IWxBookService 
{
    @Autowired
    private WxBookMapper wxBookMapper;

    /**
     * 查询图书信息管理
     * 
     * @param id 图书信息管理主键
     * @return 图书信息管理
     */
    @Override
    public WxBook selectWxBookById(String id)
    {
        return wxBookMapper.selectWxBookById(id);
    }

    /**
     * 查询图书信息管理列表
     * 
     * @param wxBook 图书信息管理
     * @return 图书信息管理
     */
    @Override
    public List<WxBook> selectWxBookList(WxBook wxBook)
    {
        return wxBookMapper.selectWxBookList(wxBook);
    }

    /**
     * 新增图书信息管理
     * 
     * @param wxBook 图书信息管理
     * @return 结果
     */
    @Override
    public int insertWxBook(WxBook wxBook)
    {
        return wxBookMapper.insertWxBook(wxBook);
    }

    /**
     * 修改图书信息管理
     * 
     * @param wxBook 图书信息管理
     * @return 结果
     */
    @Override
    public int updateWxBook(WxBook wxBook)
    {
        return wxBookMapper.updateWxBook(wxBook);
    }

    /**
     * 批量删除图书信息管理
     * 
     * @param ids 需要删除的图书信息管理主键
     * @return 结果
     */
    @Override
    public int deleteWxBookByIds(String[] ids)
    {
        return wxBookMapper.deleteWxBookByIds(ids);
    }

    /**
     * 删除图书信息管理信息
     * 
     * @param id 图书信息管理主键
     * @return 结果
     */
    @Override
    public int deleteWxBookById(String id)
    {
        return wxBookMapper.deleteWxBookById(id);
    }
}

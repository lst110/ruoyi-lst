package com.ruoyi.wx.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxCodeMapper;
import com.ruoyi.wx.domain.WxCode;
import com.ruoyi.wx.service.IWxCodeService;

/**
 * 防伪码管理Service业务层处理
 * 
 * @author lst
 * @date 2023-05-12
 */
@Service
public class WxCodeServiceImpl implements IWxCodeService 
{
    @Autowired
    private WxCodeMapper wxCodeMapper;

    /**
     * 查询防伪码管理
     * 
     * @param id 防伪码管理主键
     * @return 防伪码管理
     */
    @Override
    public WxCode selectWxCodeById(String id)
    {
        return wxCodeMapper.selectWxCodeById(id);
    }

    /**
     * 查询防伪码管理
     * 
     * @param remark 防伪码code
     * @return 防伪码管理
     */
    @Override
    public WxCode selectWxCodeByRemark(String remark){
        return wxCodeMapper.selectWxCodeByRemark(remark);
    }
    /**
     * 查询防伪码管理列表
     * 
     * @param wxCode 防伪码管理
     * @return 防伪码管理
     */
    @Override
    public List<WxCode> selectWxCodeList(WxCode wxCode)
    {
        return wxCodeMapper.selectWxCodeList(wxCode);
    }

    /**
     * 新增防伪码管理
     * 
     * @param wxCode 防伪码管理
     * @return 结果
     */
    @Override
    public int insertWxCode(WxCode wxCode)
    {
        return wxCodeMapper.insertWxCode(wxCode);
    }

    /**
     * 修改防伪码管理
     * 
     * @param wxCode 防伪码管理
     * @return 结果
     */
    @Override
    public int updateWxCode(WxCode wxCode)
    {
        return wxCodeMapper.updateWxCode(wxCode);
    }

    /**
     * 批量删除防伪码管理
     * 
     * @param ids 需要删除的防伪码管理主键
     * @return 结果
     */
    @Override
    public int deleteWxCodeByIds(String[] ids)
    {
        return wxCodeMapper.deleteWxCodeByIds(ids);
    }

    /**
     * 删除防伪码管理信息
     * 
     * @param id 防伪码管理主键
     * @return 结果
     */
    @Override
    public int deleteWxCodeById(String id)
    {
        return wxCodeMapper.deleteWxCodeById(id);
    }
}

package com.ruoyi.wx.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxWarnMapper;
import com.ruoyi.wx.domain.WxWarn;
import com.ruoyi.wx.service.IWxWarnService;

/**
 * 预警处理Service业务层处理
 * 
 * @author lst
 * @date 2023-05-11
 */
@Service
public class WxWarnServiceImpl implements IWxWarnService 
{
    @Autowired
    private WxWarnMapper wxWarnMapper;

    /**
     * 查询预警处理
     * 
     * @param id 预警处理主键
     * @return 预警处理
     */
    @Override
    public WxWarn selectWxWarnById(Long id)
    {
        return wxWarnMapper.selectWxWarnById(id);
    }

    /**
     * 查询预警处理列表
     * 
     * @param wxWarn 预警处理
     * @return 预警处理
     */
    @Override
    public List<WxWarn> selectWxWarnList(WxWarn wxWarn)
    {
        return wxWarnMapper.selectWxWarnList(wxWarn);
    }

    /**
     * 新增预警处理
     * 
     * @param wxWarn 预警处理
     * @return 结果
     */
    @Override
    public int insertWxWarn(WxWarn wxWarn)
    {
        return wxWarnMapper.insertWxWarn(wxWarn);
    }

    /**
     * 修改预警处理
     * 
     * @param wxWarn 预警处理
     * @return 结果
     */
    @Override
    public int updateWxWarn(WxWarn wxWarn)
    {
        return wxWarnMapper.updateWxWarn(wxWarn);
    }

    /**
     * 批量删除预警处理
     * 
     * @param ids 需要删除的预警处理主键
     * @return 结果
     */
    @Override
    public int deleteWxWarnByIds(Long[] ids)
    {
        return wxWarnMapper.deleteWxWarnByIds(ids);
    }

    /**
     * 删除预警处理信息
     * 
     * @param id 预警处理主键
     * @return 结果
     */
    @Override
    public int deleteWxWarnById(Long id)
    {
        return wxWarnMapper.deleteWxWarnById(id);
    }
}

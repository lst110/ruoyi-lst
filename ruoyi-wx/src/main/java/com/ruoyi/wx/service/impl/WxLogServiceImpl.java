package com.ruoyi.wx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxLogMapper;
import com.ruoyi.wx.domain.WxLog;
import com.ruoyi.wx.service.IWxLogService;

/**
 * 扫码记录Service业务层处理
 * 
 * @author lst
 * @date 2023-05-12
 */
@Service
public class WxLogServiceImpl implements IWxLogService 
{
    @Autowired
    private WxLogMapper wxLogMapper;

    /**
     * 查询扫码记录
     * 
     * @param id 扫码记录主键
     * @return 扫码记录
     */
    @Override
    public WxLog selectWxLogById(String id)
    {
        return wxLogMapper.selectWxLogById(id);
    }

    /**
     * 查询扫码记录列表
     * 
     * @param wxLog 扫码记录
     * @return 扫码记录
     */
    @Override
    public List<WxLog> selectWxLogList(WxLog wxLog)
    {
        return wxLogMapper.selectWxLogList(wxLog);
    }

    /**
     * 新增扫码记录
     * 
     * @param wxLog 扫码记录
     * @return 结果
     */
    @Override
    public int insertWxLog(WxLog wxLog)
    {
        wxLog.setCreateTime(DateUtils.getNowDate());
        return wxLogMapper.insertWxLog(wxLog);
    }

    /**
     * 修改扫码记录
     * 
     * @param wxLog 扫码记录
     * @return 结果
     */
    @Override
    public int updateWxLog(WxLog wxLog)
    {
        return wxLogMapper.updateWxLog(wxLog);
    }

    /**
     * 批量删除扫码记录
     * 
     * @param ids 需要删除的扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteWxLogByIds(String[] ids)
    {
        return wxLogMapper.deleteWxLogByIds(ids);
    }

    /**
     * 删除扫码记录信息
     * 
     * @param id 扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteWxLogById(String id)
    {
        return wxLogMapper.deleteWxLogById(id);
    }
}

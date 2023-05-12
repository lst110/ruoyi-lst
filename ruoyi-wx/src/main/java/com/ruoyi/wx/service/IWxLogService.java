package com.ruoyi.wx.service;

import java.util.List;
import com.ruoyi.wx.domain.WxLog;

/**
 * 扫码记录Service接口
 * 
 * @author lst
 * @date 2023-05-12
 */
public interface IWxLogService 
{
    /**
     * 查询扫码记录
     * 
     * @param id 扫码记录主键
     * @return 扫码记录
     */
    public WxLog selectWxLogById(String id);

    /**
     * 查询扫码记录列表
     * 
     * @param wxLog 扫码记录
     * @return 扫码记录集合
     */
    public List<WxLog> selectWxLogList(WxLog wxLog);

    /**
     * 新增扫码记录
     * 
     * @param wxLog 扫码记录
     * @return 结果
     */
    public int insertWxLog(WxLog wxLog);

    /**
     * 修改扫码记录
     * 
     * @param wxLog 扫码记录
     * @return 结果
     */
    public int updateWxLog(WxLog wxLog);

    /**
     * 批量删除扫码记录
     * 
     * @param ids 需要删除的扫码记录主键集合
     * @return 结果
     */
    public int deleteWxLogByIds(String[] ids);

    /**
     * 删除扫码记录信息
     * 
     * @param id 扫码记录主键
     * @return 结果
     */
    public int deleteWxLogById(String id);
}

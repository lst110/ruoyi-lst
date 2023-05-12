package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxWarn;

/**
 * 预警处理Mapper接口
 * 
 * @author lst
 * @date 2023-05-11
 */
public interface WxWarnMapper 
{
    /**
     * 查询预警处理
     * 
     * @param id 预警处理主键
     * @return 预警处理
     */
    public WxWarn selectWxWarnById(Long id);

    /**
     * 查询预警处理列表
     * 
     * @param wxWarn 预警处理
     * @return 预警处理集合
     */
    public List<WxWarn> selectWxWarnList(WxWarn wxWarn);

    /**
     * 新增预警处理
     * 
     * @param wxWarn 预警处理
     * @return 结果
     */
    public int insertWxWarn(WxWarn wxWarn);

    /**
     * 修改预警处理
     * 
     * @param wxWarn 预警处理
     * @return 结果
     */
    public int updateWxWarn(WxWarn wxWarn);

    /**
     * 删除预警处理
     * 
     * @param id 预警处理主键
     * @return 结果
     */
    public int deleteWxWarnById(Long id);

    /**
     * 批量删除预警处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxWarnByIds(Long[] ids);
}

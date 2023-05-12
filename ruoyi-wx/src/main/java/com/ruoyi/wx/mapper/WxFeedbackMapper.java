package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxFeedback;

/**
 * 反馈处理Mapper接口
 * 
 * @author lst
 * @date 2023-05-11
 */
public interface WxFeedbackMapper 
{
    /**
     * 查询反馈处理
     * 
     * @param id 反馈处理主键
     * @return 反馈处理
     */
    public WxFeedback selectWxFeedbackById(String id);

    /**
     * 查询反馈处理列表
     * 
     * @param wxFeedback 反馈处理
     * @return 反馈处理集合
     */
    public List<WxFeedback> selectWxFeedbackList(WxFeedback wxFeedback);

    /**
     * 新增反馈处理
     * 
     * @param wxFeedback 反馈处理
     * @return 结果
     */
    public int insertWxFeedback(WxFeedback wxFeedback);

    /**
     * 修改反馈处理
     * 
     * @param wxFeedback 反馈处理
     * @return 结果
     */
    public int updateWxFeedback(WxFeedback wxFeedback);

    /**
     * 删除反馈处理
     * 
     * @param id 反馈处理主键
     * @return 结果
     */
    public int deleteWxFeedbackById(String id);

    /**
     * 批量删除反馈处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxFeedbackByIds(String[] ids);
}

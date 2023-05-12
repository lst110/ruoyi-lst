package com.ruoyi.wx.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxFeedbackMapper;
import com.ruoyi.wx.domain.WxFeedback;
import com.ruoyi.wx.service.IWxFeedbackService;

/**
 * 反馈处理Service业务层处理
 * 
 * @author lst
 * @date 2023-05-11
 */
@Service
public class WxFeedbackServiceImpl implements IWxFeedbackService 
{
    @Autowired
    private WxFeedbackMapper wxFeedbackMapper;

    /**
     * 查询反馈处理
     * 
     * @param id 反馈处理主键
     * @return 反馈处理
     */
    @Override
    public WxFeedback selectWxFeedbackById(String id)
    {
        return wxFeedbackMapper.selectWxFeedbackById(id);
    }

    /**
     * 查询反馈处理列表
     * 
     * @param wxFeedback 反馈处理
     * @return 反馈处理
     */
    @Override
    public List<WxFeedback> selectWxFeedbackList(WxFeedback wxFeedback)
    {
        return wxFeedbackMapper.selectWxFeedbackList(wxFeedback);
    }

    /**
     * 新增反馈处理
     * 
     * @param wxFeedback 反馈处理
     * @return 结果
     */
    @Override
    public int insertWxFeedback(WxFeedback wxFeedback)
    {
        return wxFeedbackMapper.insertWxFeedback(wxFeedback);
    }

    /**
     * 修改反馈处理
     * 
     * @param wxFeedback 反馈处理
     * @return 结果
     */
    @Override
    public int updateWxFeedback(WxFeedback wxFeedback)
    {
        return wxFeedbackMapper.updateWxFeedback(wxFeedback);
    }

    /**
     * 批量删除反馈处理
     * 
     * @param ids 需要删除的反馈处理主键
     * @return 结果
     */
    @Override
    public int deleteWxFeedbackByIds(String[] ids)
    {
        return wxFeedbackMapper.deleteWxFeedbackByIds(ids);
    }

    /**
     * 删除反馈处理信息
     * 
     * @param id 反馈处理主键
     * @return 结果
     */
    @Override
    public int deleteWxFeedbackById(String id)
    {
        return wxFeedbackMapper.deleteWxFeedbackById(id);
    }
}

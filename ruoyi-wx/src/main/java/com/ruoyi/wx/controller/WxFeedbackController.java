package com.ruoyi.wx.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wx.domain.WxFeedback;
import com.ruoyi.wx.service.IWxFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 反馈处理Controller
 * 
 * @author lst
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/wx/feedback")
public class WxFeedbackController extends BaseController
{
    @Autowired
    private IWxFeedbackService wxFeedbackService;

    /**
     * 查询反馈处理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxFeedback wxFeedback)
    {
        startPage();
        List<WxFeedback> list = wxFeedbackService.selectWxFeedbackList(wxFeedback);
        return getDataTable(list);
    }

    /**
     * 导出反馈处理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:export')")
    @Log(title = "反馈处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxFeedback wxFeedback)
    {
        List<WxFeedback> list = wxFeedbackService.selectWxFeedbackList(wxFeedback);
        ExcelUtil<WxFeedback> util = new ExcelUtil<WxFeedback>(WxFeedback.class);
        util.exportExcel(response, list, "反馈处理数据");
    }

    /**
     * 获取反馈处理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(wxFeedbackService.selectWxFeedbackById(id));
    }

    /**
     * 新增反馈处理
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:add')")
    @Log(title = "反馈处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxFeedback wxFeedback)
    {
        return toAjax(wxFeedbackService.insertWxFeedback(wxFeedback));
    }

    /**
     * 修改反馈处理
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:edit')")
    @Log(title = "反馈处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxFeedback wxFeedback)
    {
        return toAjax(wxFeedbackService.updateWxFeedback(wxFeedback));
    }

    /**
     * 删除反馈处理
     */
    @PreAuthorize("@ss.hasPermi('wx:feedback:remove')")
    @Log(title = "反馈处理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxFeedbackService.deleteWxFeedbackByIds(ids));
    }
}

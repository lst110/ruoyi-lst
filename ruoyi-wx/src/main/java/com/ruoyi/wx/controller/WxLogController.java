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
import com.ruoyi.wx.domain.WxLog;
import com.ruoyi.wx.service.IWxLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 扫码记录Controller
 * 
 * @author lst
 * @date 2023-05-12
 */
@RestController
@RequestMapping("/wx/log")
public class WxLogController extends BaseController
{
    @Autowired
    private IWxLogService wxLogService;

    /**
     * 查询扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('wx:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxLog wxLog)
    {
        startPage();
        List<WxLog> list = wxLogService.selectWxLogList(wxLog);
        return getDataTable(list);
    }

    /**
     * 导出扫码记录列表
     */
    @PreAuthorize("@ss.hasPermi('wx:log:export')")
    @Log(title = "扫码记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxLog wxLog)
    {
        List<WxLog> list = wxLogService.selectWxLogList(wxLog);
        ExcelUtil<WxLog> util = new ExcelUtil<WxLog>(WxLog.class);
        util.exportExcel(response, list, "扫码记录数据");
    }

    /**
     * 获取扫码记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(wxLogService.selectWxLogById(id));
    }

    /**
     * 新增扫码记录
     */
    @PreAuthorize("@ss.hasPermi('wx:log:add')")
    @Log(title = "扫码记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxLog wxLog)
    {
        return toAjax(wxLogService.insertWxLog(wxLog));
    }

    /**
     * 修改扫码记录
     */
    @PreAuthorize("@ss.hasPermi('wx:log:edit')")
    @Log(title = "扫码记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxLog wxLog)
    {
        return toAjax(wxLogService.updateWxLog(wxLog));
    }

    /**
     * 删除扫码记录
     */
    @PreAuthorize("@ss.hasPermi('wx:log:remove')")
    @Log(title = "扫码记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxLogService.deleteWxLogByIds(ids));
    }
}

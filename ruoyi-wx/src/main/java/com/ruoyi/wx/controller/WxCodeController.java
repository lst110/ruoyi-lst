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
import com.ruoyi.wx.domain.WxCode;
import com.ruoyi.wx.service.IWxCodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 防伪码管理Controller
 * 
 * @author lst
 * @date 2023-05-12
 */
@RestController
@RequestMapping("/wx/code")
public class WxCodeController extends BaseController
{
    @Autowired
    private IWxCodeService wxCodeService;

    /**
     * 查询防伪码管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:code:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxCode wxCode)
    {
        startPage();
        List<WxCode> list = wxCodeService.selectWxCodeList(wxCode);
        return getDataTable(list);
    }

    /**
     * 导出防伪码管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:code:export')")
    @Log(title = "防伪码管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxCode wxCode)
    {
        List<WxCode> list = wxCodeService.selectWxCodeList(wxCode);
        ExcelUtil<WxCode> util = new ExcelUtil<WxCode>(WxCode.class);
        util.exportExcel(response, list, "防伪码管理数据");
    }

    /**
     * 获取防伪码管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:code:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(wxCodeService.selectWxCodeById(id));
    }

    /**
     * 新增防伪码管理
     */
    @PreAuthorize("@ss.hasPermi('wx:code:add')")
    @Log(title = "防伪码管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxCode wxCode)
    {
        return toAjax(wxCodeService.insertWxCode(wxCode));
    }

    /**
     * 修改防伪码管理
     */
    @PreAuthorize("@ss.hasPermi('wx:code:edit')")
    @Log(title = "防伪码管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxCode wxCode)
    {
        return toAjax(wxCodeService.updateWxCode(wxCode));
    }

    /**
     * 删除防伪码管理
     */
    @PreAuthorize("@ss.hasPermi('wx:code:remove')")
    @Log(title = "防伪码管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxCodeService.deleteWxCodeByIds(ids));
    }
}

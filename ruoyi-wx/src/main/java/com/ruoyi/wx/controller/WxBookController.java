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
import com.ruoyi.wx.domain.WxBook;
import com.ruoyi.wx.service.IWxBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书信息管理Controller
 * 
 * @author lst
 * @date 2023-05-12
 */
@RestController
@RequestMapping("/wx/book")
public class WxBookController extends BaseController
{
    @Autowired
    private IWxBookService wxBookService;

    /**
     * 查询图书信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxBook wxBook)
    {
        startPage();
        List<WxBook> list = wxBookService.selectWxBookList(wxBook);
        return getDataTable(list);
    }

    /**
     * 导出图书信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:book:export')")
    @Log(title = "图书信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxBook wxBook)
    {
        List<WxBook> list = wxBookService.selectWxBookList(wxBook);
        ExcelUtil<WxBook> util = new ExcelUtil<WxBook>(WxBook.class);
        util.exportExcel(response, list, "图书信息管理数据");
    }

    /**
     * 获取图书信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(wxBookService.selectWxBookById(id));
    }

    /**
     * 新增图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:add')")
    @Log(title = "图书信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxBook wxBook)
    {
        return toAjax(wxBookService.insertWxBook(wxBook));
    }

    /**
     * 修改图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:edit')")
    @Log(title = "图书信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxBook wxBook)
    {
        return toAjax(wxBookService.updateWxBook(wxBook));
    }

    /**
     * 删除图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:remove')")
    @Log(title = "图书信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxBookService.deleteWxBookByIds(ids));
    }
}

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
import com.ruoyi.wx.domain.WxUsers;
import com.ruoyi.wx.service.IWxUsersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小程序用户Controller
 * 
 * @author lst
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/wx/users")
public class WxUsersController extends BaseController
{
    @Autowired
    private IWxUsersService wxUsersService;

    /**
     * 查询小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('wx:users:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxUsers wxUsers)
    {
        startPage();
        List<WxUsers> list = wxUsersService.selectWxUsersList(wxUsers);
        return getDataTable(list);
    }

    /**
     * 导出小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('wx:users:export')")
    @Log(title = "小程序用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxUsers wxUsers)
    {
        List<WxUsers> list = wxUsersService.selectWxUsersList(wxUsers);
        ExcelUtil<WxUsers> util = new ExcelUtil<WxUsers>(WxUsers.class);
        util.exportExcel(response, list, "小程序用户数据");
    }

    /**
     * 获取小程序用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:users:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wxUsersService.selectWxUsersById(id));
    }

    /**
     * 新增小程序用户
     */
    @PreAuthorize("@ss.hasPermi('wx:users:add')")
    @Log(title = "小程序用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxUsers wxUsers)
    {
        return toAjax(wxUsersService.insertWxUsers(wxUsers));
    }

    /**
     * 修改小程序用户
     */
    @PreAuthorize("@ss.hasPermi('wx:users:edit')")
    @Log(title = "小程序用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxUsers wxUsers)
    {
        return toAjax(wxUsersService.updateWxUsers(wxUsers));
    }

    /**
     * 删除小程序用户
     */
    @PreAuthorize("@ss.hasPermi('wx:users:remove')")
    @Log(title = "小程序用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wxUsersService.deleteWxUsersByIds(ids));
    }
}

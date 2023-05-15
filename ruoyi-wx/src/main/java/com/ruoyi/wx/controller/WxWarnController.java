package com.ruoyi.wx.controller;

import java.util.List;
import java.util.Map;

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

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wx.domain.WxCode;
import com.ruoyi.wx.domain.WxUsers;
import com.ruoyi.wx.domain.WxWarn;
import com.ruoyi.wx.service.IWxCodeService;
import com.ruoyi.wx.service.IWxUsersService;
import com.ruoyi.wx.service.IWxWarnService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预警处理Controller
 * 
 * @author lst
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/wx/warn")
public class WxWarnController extends BaseController
{
    @Autowired
    private IWxWarnService wxWarnService;
    @Autowired
    private IWxUsersService wxUsersService;
    @Autowired
    private IWxCodeService wxCodeService;
    /**
     * 查询预警处理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxWarn wxWarn)
    {
        startPage();
        List<WxWarn> list = wxWarnService.selectWxWarnList(wxWarn);
        return getDataTable(list);
    }

    /**
     * 导出预警处理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:export')")
    @Log(title = "预警处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxWarn wxWarn)
    {
        List<WxWarn> list = wxWarnService.selectWxWarnList(wxWarn);
        ExcelUtil<WxWarn> util = new ExcelUtil<WxWarn>(WxWarn.class);
        util.exportExcel(response, list, "预警处理数据");
    }

    /**
     * 获取预警处理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wxWarnService.selectWxWarnById(id));
    }

    /**
     * 新增预警处理
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:add')")
    @Log(title = "预警处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxWarn wxWarn)
    {
        return toAjax(wxWarnService.insertWxWarn(wxWarn));
    }

    /**
     * 修改预警处理
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:edit')")
    @Log(title = "预警处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxWarn wxWarn)
    {
        return toAjax(wxWarnService.updateWxWarn(wxWarn));
    }

    /**
     * 删除预警处理
     */
    @PreAuthorize("@ss.hasPermi('wx:warn:remove')")
    @Log(title = "预警处理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wxWarnService.deleteWxWarnByIds(ids));
    }

    /**
     * 修改防伪码状态
     */
    @Log(title = "处理告警", businessType = BusinessType.UPDATE)
    @Anonymous
    @PostMapping("/change_state")
    public AjaxResult changeState(@RequestBody Map<String,Object> map)
    {
        String openid = (String) map.get("openid");
        String warn_id = (String) map.get("warn_id");
        String state = (String) map.get("state");
        if(openid == null || warn_id == null || state == null) {
            return error("参数错误");
        }
        WxUsers user = wxUsersService.selectWxUsersByOpenId(openid);
        if(user == null) {
            return error("用户不存在");
        }
        WxWarn warn = wxWarnService.selectWxWarnById(Long.parseLong(warn_id));
        if(warn == null) {
            return error("告警信息不存在");
        }
        WxCode code = wxCodeService.selectWxCodeById(String.valueOf(warn.getwarn_qrid()));
        if(code == null) {
            return error("防伪码信息不存在");
        }
        if(!code.getCreateUser().equals(String.valueOf(user.getId()))) {
            return error("权限验证失败");
        }
        code.setCodeStatus(state);
        warn.setwarn_state("2");
        try {
            wxCodeService.updateWxCode(code);
            wxWarnService.updateWxWarn(warn);
        } catch (Exception e) {
            e.printStackTrace();
            return error("处理失败");
        }
        return success("处理成功");
    }
}

package com.ruoyi.wx.controller;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
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


    @Log(title = "小程序用户登录")
    @RequestMapping("/wechat/login")
    @Anonymous
    public AjaxResult wechatLogin(@RequestBody Map<String, Object> map) {
        Map<String,Object> resp = new HashMap<String,Object>();
        System.out.println("========" + map.get("code"));
        RestTemplate restTemplate = new RestTemplate();
        //请求微信开发平台判断用户
        String wechatAppid = "wxb6f841676594c1f2";
        String wechatSecret = "39357557899671b926a65a33f59ccbd8";
        //向微信服务器发送恳求获取用户信息
        System.out.println(RuoYiConfig.getWechatAppid());
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wechatAppid + "&secret=" + wechatSecret + "&js_code=" + map.get("code") + "&grant_type=authorization_code";
        System.out.println(url);
        String res = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        if(jsonObject.containsKey("errcode")) {
            System.out.println(jsonObject.toJSONString());
            return error("获取openid失败");
        }
        // 获取openid
        String openid = jsonObject.getString("openid");
        WxUsers user = wxUsersService.selectWxUsersByOpenId(openid);
        if(user != null) {
            resp.put("avatar", user.getAvatar());
            resp.put("nickname", user.getNickame());
            resp.put("openid", user.getOpenid());
            return success(resp);
        }
        // url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + wechatAppid + "&secret=" + wechatSecret + "&grant_type=client_credential";
        // res = restTemplate.getForObject(url, String.class);
        // jsonObject = JSONObject.parseObject(res);
        // System.out.println(jsonObject);
        // if(!jsonObject.containsKey("access_token")) {
        //     System.out.println(jsonObject.toJSONString());
        //     return error("获取access_token失败");
        // }
        // String access_token = jsonObject.getString("access_token");
        // url = "https://api.weixin.qq.com/sns/userinfo?access_token="+ access_token + "&openid=" + openid + "&lang=zh_CN";
        // System.out.println(url);
        // res = restTemplate.getForObject(url, String.class);
        // jsonObject = JSONObject.parseObject(res);
        // if(!jsonObject.containsKey("nickname")) {
        //     System.out.println(jsonObject.toJSONString());
        //     return error("获取userinfo失败");
        // }
        user = new WxUsers();
        user.setNickame((String)map.get("nickname"));
        user.setOpenid(openid);
        user.setAvatar((String)map.get("headimgurl"));
        user.setCreateTime(new Date());
        try {
            wxUsersService.insertWxUsers(user);
        } catch (Exception e) {
            System.out.println("保存用户信息失败");
            e.printStackTrace();
            return error("保存用户信息失败");
        }
        resp.put("avatar", user.getAvatar());
        resp.put("nickname", user.getNickame());
        resp.put("openid", user.getOpenid());
        return success(resp);
    }
}

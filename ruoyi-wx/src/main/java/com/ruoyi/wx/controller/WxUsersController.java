package com.ruoyi.wx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wx.domain.WxCode;
import com.ruoyi.wx.domain.WxLog;
import com.ruoyi.wx.domain.WxUsers;
import com.ruoyi.wx.domain.WxWarn;
import com.ruoyi.wx.service.IWxCodeService;
import com.ruoyi.wx.service.IWxLogService;
import com.ruoyi.wx.service.IWxUsersService;
import com.ruoyi.wx.service.IWxWarnService;
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
    @Autowired
    private IWxCodeService wxCodeService;
    @Autowired
    private IWxWarnService wxWarnService;
    @Autowired
    private IWxLogService wxLogService;
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
        RestTemplate restTemplate = new RestTemplate();
        //请求微信开发平台判断用户
        String wechatAppid = "wxb6f841676594c1f2";
        String wechatSecret = "39357557899671b926a65a33f59ccbd8";
        //向微信服务器发送恳求获取用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wechatAppid + "&secret=" + wechatSecret + "&js_code=" + map.get("code") + "&grant_type=authorization_code";
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
            //查询是否需要预警
            WxCode code = new WxCode();
            code.setCreateUser(String.valueOf(user.getId()));
            code.setCodeStatus("2");
            List<WxCode> record = wxCodeService.selectWxCodeList(code);
            resp.put("avatar", user.getAvatar());
            resp.put("nickname", user.getNickame());
            resp.put("openid", user.getOpenid());
            resp.put("status", record.isEmpty() ? "1" : "2");
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
        resp.put("status", "1");
        return success(resp);
    }

    @Log(title = "小程序用户扫码")
    @PostMapping("/wechat/scan_qr")
    @Anonymous
    public AjaxResult UserScanQr(@RequestBody Map<String, Object> map,HttpServletRequest request){
        String openid = (String)map.get("openid");
        String qr = (String)map.get("qr_val");
        if(openid == null || qr == null) {
            return error("参数错误");
        }
        WxUsers user = wxUsersService.selectWxUsersByOpenId(openid);
        if(user == null) {
            return error("用户不存在");
        }
        WxCode code = wxCodeService.selectWxCodeByRemark(qr);
        if(code == null) {
            return error("防伪码不存在");
        }
        // 查询是否有扫码记录
        WxLog wxLog = new WxLog();
        wxLog.setCode_id(String.valueOf(code.getId()));
        List<WxLog> logList = wxLogService.selectWxLogList(wxLog);
        if(logList.size() > 0) {
            // 这里进入告警逻辑
            WxLog firstLog = new WxLog();
            code.setLogId(String.valueOf(user.getId()));
            for (WxLog logList2 : logList) {
                if(firstLog.getCreateTime() == null) {
                    firstLog = logList2;
                } else if(logList2.getCreateTime().before(firstLog.getCreateTime())) {
                    firstLog = logList2;
                }
                // if(!logList2.getUserId().equals(String.valueOf(user.getId()))){
                //     // 如果
                // }
            };
            firstLog.setId(null);
            if(!code.getCreateUser().equals(String.valueOf(user.getId()))) {
                // 当前扫码id和第一次扫码id不一致，触发告警
                code.setCodeStatus("2");
                try {
                    //更改防盗码状态
                    wxCodeService.updateWxCode(code);
                    //添加扫码日志
                    firstLog.setUserId(String.valueOf(user.getId()));
                    firstLog.setIp(request.getRemoteHost());
                    firstLog.setTime(new Date());
                    wxLogService.insertWxLog(firstLog);
                    //添加预警日志
                    WxWarn warn = new WxWarn();
                    warn.setwarn_num(1L);
                    warn.setwarn_time(new Date());
                    warn.setwarn_ip(request.getRemoteHost());
                    warn.setwarn_qrid(code.getId());
                    warn.setwarn_state("1");
                    warn.setwarn_user(user.getId());
                    wxWarnService.insertWxWarn(warn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    //更改防盗码状态
                    wxCodeService.updateWxCode(code);
                    //添加扫码日志
                    firstLog.setId(user.getId());
                    firstLog.setIp(request.getRemoteHost());
                    firstLog.setTime(new Date());
                    wxLogService.insertWxLog(firstLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 首次扫码
            code.setCodeStatus("1");
            code.setCreateUser(String.valueOf(user.getId()));
            code.setCreateLog(String.valueOf(user.getId()));
            code.setLogId(String.valueOf(user.getId()));
            wxLog.setCreateIp(request.getRemoteHost());
            wxLog.setCreateTime(new Date());
            wxLog.setIp(request.getRemoteHost());
            wxLog.setTime(new Date());
            wxLog.setUserId(String.valueOf(user.getId()));
            wxLog.setNumber(1L);
            try {
                wxLogService.insertWxLog(wxLog);
                wxCodeService.updateWxCode(code);
            } catch (Exception e) {
                e.printStackTrace();
                return error("扫码记录添加失败");
            }
        }
        return success("扫码成功");
    }

    @Log(title = "我的防伪码")
    @PostMapping("/wechat/my_code")
    @Anonymous
    public AjaxResult myCode(@RequestBody Map<String, Object> map){
        String openid = (String)map.get("openid");
        if(openid == null) {
            return error("参数错误");
        }
        WxUsers user = wxUsersService.selectWxUsersByOpenId(openid);
        if(user == null) {
            return error("用户不存在");
        }
        WxCode code = new WxCode();
        code.setCreateUser(String.valueOf(user.getId()));
        List<WxCode> codes = wxCodeService.selectWxCodeList(code);
        return success(codes);
    }

    @Log(title = "防伪码图片")
    @GetMapping("/wechat/code/{content}")
    @Anonymous
    public void myCode(@PathVariable String content,HttpServletResponse response){
        response.setContentType("image/png");
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //边框距
        hints.put(EncodeHintType.MARGIN, 0);
        String format = "png";// 图像类型
        BitMatrix bitMatrix;
        try {
            //参数分别为：编码内容、编码类型、图片宽度、图片高度，设置参数
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);
            // BufferedImage buffImg = MatrixToImageWriter.toBufferedImage(bitMatrix);
            // ImageIO.write(buffImg, format, zos);
            // zos.flush();
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
            bitMatrix.clear();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("-------------------------" + content);
        }
    }

    @Log(title = "我的预警记录")
    @PostMapping("/wechat/my_warn")
    @Anonymous
    public AjaxResult myWarn(@RequestBody Map<String, Object> map){
        String openid = (String)map.get("openid");
        if(openid == null) {
            return error("参数错误");
        }
        WxUsers user = wxUsersService.selectWxUsersByOpenId(openid);
        if(user == null) {
            return error("用户不存在");
        }
        WxCode code = new WxCode();
        code.setCreateUser(String.valueOf(user.getId()));
        List<WxCode> codes = wxCodeService.selectWxCodeList(code);
        List<WxWarn> warns = new ArrayList<WxWarn>();
        for (WxCode wxCode : codes) {
            WxWarn warn = new WxWarn();
            warn.setwarn_qrid(wxCode.getId());
            List<WxWarn> tmps = wxWarnService.selectWxWarnList(warn);
            if(tmps.size() > 0) {
                warns.addAll(tmps);
            }
        }
        return success(warns);
    }

}

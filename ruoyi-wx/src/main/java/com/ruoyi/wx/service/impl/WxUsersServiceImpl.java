package com.ruoyi.wx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxUsersMapper;
import com.ruoyi.wx.domain.WxUsers;
import com.ruoyi.wx.service.IWxUsersService;

/**
 * 小程序用户Service业务层处理
 * 
 * @author lst
 * @date 2023-05-15
 */
@Service
public class WxUsersServiceImpl implements IWxUsersService 
{
    @Autowired
    private WxUsersMapper wxUsersMapper;

    /**
     * 查询小程序用户
     * 
     * @param id 小程序用户主键
     * @return 小程序用户
     */
    @Override
    public WxUsers selectWxUsersById(Long id)
    {
        return wxUsersMapper.selectWxUsersById(id);
    }

    /**
     * 查询小程序用户
     * 
     * @param OpenId 小程序用户OpenId
     * @return 小程序用户
     */
    @Override
    public WxUsers selectWxUsersByOpenId(String openid) {
        return wxUsersMapper.selectWxUsersByOpenId(openid);
    }

    /**
     * 查询小程序用户列表
     * 
     * @param wxUsers 小程序用户
     * @return 小程序用户
     */
    @Override
    public List<WxUsers> selectWxUsersList(WxUsers wxUsers)
    {
        return wxUsersMapper.selectWxUsersList(wxUsers);
    }

    /**
     * 新增小程序用户
     * 
     * @param wxUsers 小程序用户
     * @return 结果
     */
    @Override
    public int insertWxUsers(WxUsers wxUsers)
    {
        wxUsers.setCreateTime(DateUtils.getNowDate());
        return wxUsersMapper.insertWxUsers(wxUsers);
    }

    /**
     * 修改小程序用户
     * 
     * @param wxUsers 小程序用户
     * @return 结果
     */
    @Override
    public int updateWxUsers(WxUsers wxUsers)
    {
        wxUsers.setUpdateTime(DateUtils.getNowDate());
        return wxUsersMapper.updateWxUsers(wxUsers);
    }

    /**
     * 批量删除小程序用户
     * 
     * @param ids 需要删除的小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUsersByIds(Long[] ids)
    {
        return wxUsersMapper.deleteWxUsersByIds(ids);
    }

    /**
     * 删除小程序用户信息
     * 
     * @param id 小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUsersById(Long id)
    {
        return wxUsersMapper.deleteWxUsersById(id);
    }
}

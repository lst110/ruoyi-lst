package com.ruoyi.wx.service;

import java.util.List;
import com.ruoyi.wx.domain.WxUsers;

/**
 * 小程序用户Service接口
 * 
 * @author lst
 * @date 2023-05-15
 */
public interface IWxUsersService 
{
    /**
     * 查询小程序用户
     * 
     * @param id 小程序用户主键
     * @return 小程序用户
     */
    public WxUsers selectWxUsersById(Long id);

    /**
     * 查询小程序用户列表
     * 
     * @param wxUsers 小程序用户
     * @return 小程序用户集合
     */
    public List<WxUsers> selectWxUsersList(WxUsers wxUsers);

    /**
     * 新增小程序用户
     * 
     * @param wxUsers 小程序用户
     * @return 结果
     */
    public int insertWxUsers(WxUsers wxUsers);

    /**
     * 修改小程序用户
     * 
     * @param wxUsers 小程序用户
     * @return 结果
     */
    public int updateWxUsers(WxUsers wxUsers);

    /**
     * 批量删除小程序用户
     * 
     * @param ids 需要删除的小程序用户主键集合
     * @return 结果
     */
    public int deleteWxUsersByIds(Long[] ids);

    /**
     * 删除小程序用户信息
     * 
     * @param id 小程序用户主键
     * @return 结果
     */
    public int deleteWxUsersById(Long id);
}

package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxUsers;

/**
 * 小程序用户Mapper接口
 * 
 * @author lst
 * @date 2023-05-15
 */
public interface WxUsersMapper 
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
     * 删除小程序用户
     * 
     * @param id 小程序用户主键
     * @return 结果
     */
    public int deleteWxUsersById(Long id);

    /**
     * 批量删除小程序用户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxUsersByIds(Long[] ids);
}

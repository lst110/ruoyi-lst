package com.ruoyi.wx.service;

import java.util.List;
import com.ruoyi.wx.domain.WxCode;

/**
 * 防伪码管理Service接口
 * 
 * @author lst
 * @date 2023-05-12
 */
public interface IWxCodeService 
{
    /**
     * 查询防伪码管理
     * 
     * @param id 防伪码管理主键
     * @return 防伪码管理
     */
    public WxCode selectWxCodeById(String id);

    /**
     * 查询防伪码管理列表
     * 
     * @param wxCode 防伪码管理
     * @return 防伪码管理集合
     */
    public List<WxCode> selectWxCodeList(WxCode wxCode);

    /**
     * 新增防伪码管理
     * 
     * @param wxCode 防伪码管理
     * @return 结果
     */
    public int insertWxCode(WxCode wxCode);

    /**
     * 修改防伪码管理
     * 
     * @param wxCode 防伪码管理
     * @return 结果
     */
    public int updateWxCode(WxCode wxCode);

    /**
     * 批量删除防伪码管理
     * 
     * @param ids 需要删除的防伪码管理主键集合
     * @return 结果
     */
    public int deleteWxCodeByIds(String[] ids);

    /**
     * 删除防伪码管理信息
     * 
     * @param id 防伪码管理主键
     * @return 结果
     */
    public int deleteWxCodeById(String id);
}

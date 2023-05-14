package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxCode;

/**
 * 防伪码管理Mapper接口
 * 
 * @author lst
 * @date 2023-05-12
 */
public interface WxCodeMapper 
{
    /**
     * 查询防伪码管理
     * 
     * @param id 防伪码管理主键
     * @return 防伪码管理
     */
    public WxCode selectWxCodeById(String id);

    /**
     * 查询防伪码管理
     * 
     * @param remark 防伪码code
     * @return 防伪码管理
     */
    public WxCode selectWxCodeByRemark(String remark);

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
     * 删除防伪码管理
     * 
     * @param id 防伪码管理主键
     * @return 结果
     */
    public int deleteWxCodeById(String id);

    /**
     * 批量删除防伪码管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxCodeByIds(String[] ids);
}

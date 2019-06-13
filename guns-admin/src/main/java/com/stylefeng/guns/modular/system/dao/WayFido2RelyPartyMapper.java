package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.system.model.WayFido2RelyParty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:shengquan
 */
public interface WayFido2RelyPartyMapper extends BaseMapper<WayFido2RelyParty> {

    /**
     * 查询全部依赖方列表(包括模糊查询).
     *
     * @param rpName
     * @return
     */
    List<WayFido2RelyParty> selectAllRelyParty(@Param("page") Page<WayFido2RelyParty> page,
                                               @Param("rpName") String rpName,
                                               @Param("orderByField") String orderByField,
                                               @Param("isAsc") boolean isAsc);

    void addRelyParty(WayFido2RelyParty wayFido2RelyParty);
    
    Integer selectRpByRpName(@Param("rpName") String rpName);
    
    WayFido2RelyParty selectById(@Param("id") Integer id);
}

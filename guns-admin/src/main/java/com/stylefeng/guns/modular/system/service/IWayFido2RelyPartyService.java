package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.WayFido2RelyParty;

import java.util.List;

public interface IWayFido2RelyPartyService extends IService<WayFido2RelyParty> {

    List<WayFido2RelyParty> selectAllRelyParty(Page<WayFido2RelyParty> page, String rpName, String orderByField,
                                               boolean asc);
    void addRelyParty(WayFido2RelyParty wayFido2RelyParty);

    Integer selectRpByRpName(String rpName);
    
    WayFido2RelyParty selectById(Integer id);
}

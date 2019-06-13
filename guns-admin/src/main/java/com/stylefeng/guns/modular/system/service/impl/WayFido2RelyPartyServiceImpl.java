package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.common.constant.DatasourceEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.modular.system.dao.WayFido2RelyPartyMapper;
import com.stylefeng.guns.modular.system.model.WayFido2RelyParty;
import com.stylefeng.guns.modular.system.service.IWayFido2RelyPartyService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class WayFido2RelyPartyServiceImpl extends ServiceImpl<WayFido2RelyPartyMapper, WayFido2RelyParty>
        implements IWayFido2RelyPartyService {
	@Override
	@DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
	public WayFido2RelyParty selectById(Integer id) {
		return this.baseMapper.selectById(id);
	}
	
    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public boolean insert(WayFido2RelyParty entity) {
        return super.insert(entity);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public boolean updateById(WayFido2RelyParty entity) {
        return super.updateById(entity);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public List<WayFido2RelyParty> selectAllRelyParty(Page<WayFido2RelyParty> page, String rpName, String orderByField,
                                                      boolean asc) {
        return this.baseMapper.selectAllRelyParty(page, rpName, orderByField, asc);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public void addRelyParty(WayFido2RelyParty wayFido2RelyParty) {
        this.baseMapper.addRelyParty(wayFido2RelyParty);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    public Integer selectRpByRpName(String rpName) {
        return this.baseMapper.selectRpByRpName(rpName);
    }
}

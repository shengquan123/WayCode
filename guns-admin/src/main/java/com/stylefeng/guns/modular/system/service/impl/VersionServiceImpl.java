package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.system.dao.WayAccessorySoftwareVersionMapper;
import com.stylefeng.guns.modular.system.model.OperationLog;
import com.stylefeng.guns.modular.system.model.WayAccessorySoftwareVersion;
import com.stylefeng.guns.modular.system.service.IVersionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 版本管理 服务实现类
 * </p>
 *
 * @author shengquan
 * @since 2018-03-12
 */
@Service
public class VersionServiceImpl extends ServiceImpl<WayAccessorySoftwareVersionMapper, WayAccessorySoftwareVersion>
        implements IVersionService {

    @Override
    public List<Map<String, Object>> getVersions(Page<OperationLog> page, Integer type, String orderByField,
                                                 boolean isAsc) {
        return this.baseMapper.getVersions(page, type, orderByField, isAsc);
    }

    @Override
    public void addVersion(WayAccessorySoftwareVersion version) {
        this.baseMapper.addVersion(version);
    }

    @Override
    public void deleteVersionById(Integer versionId) {
        this.baseMapper.deleteVersionById(versionId);
    }

}

package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.OperationLog;
import com.stylefeng.guns.modular.system.model.WayAccessorySoftwareVersion;

/**
 * <p>
 * 版本管理 服务类
 * </p>
 *
 * @author shengquan
 * @since 2018-03-12
 */
public interface IVersionService extends IService<WayAccessorySoftwareVersion> {

    /**
     * 1.获取软件版本列表
     * @param page
     * @param orderByField
     * @return
     */
    List<Map<String, Object>> getVersions(@Param("page") Page<OperationLog> page, @Param("type") Integer type,
            @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 2.添加软件
     * @param version
     */
    void addVersion(WayAccessorySoftwareVersion version);

    /**
     * 3.删除软件 根据软件Id删除一条记录
     * @param versionId
     * @return
     */
    void deleteVersionById(Integer versionId);
}

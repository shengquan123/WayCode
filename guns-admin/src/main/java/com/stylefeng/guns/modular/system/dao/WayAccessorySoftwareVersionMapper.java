package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.system.model.OperationLog;
import com.stylefeng.guns.modular.system.model.WayAccessorySoftwareVersion;

/**
 * 版本表Mapper接口 com.baomidou.mybatisplus.mapper.BaseMapper<Version> Mapper 继承BaseMapper<T>该接口后，无需编写 mapper.xml
 * 文件，即可获得CRUD功能,这个 Mapper支持id泛型
 * @author shengquan
 * @since 2018-02-27
 */
public interface WayAccessorySoftwareVersionMapper extends BaseMapper<WayAccessorySoftwareVersion> {

    /**
     * 1.获取软件版本列表
     */
    List<Map<String, Object>> getVersions(@Param("page") Page<OperationLog> page, @Param("type") Integer type,
            @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 2.添加软件
     */
    void addVersion(WayAccessorySoftwareVersion version);

    /**
     * 3.删除软件
     */
    void deleteVersionById(Integer versionId);

}

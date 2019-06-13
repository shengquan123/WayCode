package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

public class WayAccessorySoftwareVersionWarpper extends BaseControllerWarpper {

    public WayAccessorySoftwareVersionWarpper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        // 将type由int通过查询字典转换为1:客户端App-SAFE,2:客户端App-NORMAL,3:设备固件-SAFE,4:设备固件-NORMAL1,5:设备固件-NORMAL3
        map.put("type", ConstantFactory.me().getSoftName((Integer) map.get("type")));
    }

}

package com.stylefeng.guns.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.core.base.controller.BaseController;

/**
 * 数据统计控制器
 *
 * @author fengshuonan
 * @Date 2017-08-22 18:04:25
 */
@Controller
@RequestMapping("/count")
public class CountController extends BaseController {

    private String PREFIX = "/count/";


    /**
     * 跳转到新增用户查询
     */
    @RequestMapping("/new_user")
    public String count_new_user() {
        return PREFIX + "new_user.html";
    }
    
    /**
     * 跳转到用户列表
     */
    @RequestMapping("/user_list")
    public String user_list() {
        return PREFIX + "user_list.html";
    }
    
    /**
     * 跳转到用户活跃度
     */
    @RequestMapping("/user_vitality")
    public String user_vitality() {
        return PREFIX + "user_vitality.html";
    }
    
    /**
     * 跳转到指纹注册占比
     */
    @RequestMapping("/fp_reg_pr")
    public String fp_reg_pr() {
        return PREFIX + "fp_reg_pr.html";
    }
    
    /**
     * 跳转到用户行为数据
     */
    @RequestMapping("/user_action")
    public String user_action() {
        return PREFIX + "user_action.html";
    }
    
    /**
     * 跳转到操作系统占比
     */
    @RequestMapping("/wind_pr")
    public String wind_pr() {
        return PREFIX + "wind_pr.html";
    }
    
    /**
     * 跳转到直达排序
     */
    @RequestMapping("/direct_sort")
    public String direct_sort() {
        return PREFIX + "direct_sort.html";
    }
    
    
}

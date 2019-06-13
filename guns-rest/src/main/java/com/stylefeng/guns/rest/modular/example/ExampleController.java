package com.stylefeng.guns.rest.modular.example;

import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.rest.common.SimpleObject;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 常规控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/hello")
public class ExampleController {

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Autowired
	    private JwtProperties jwtProperties;
	    
    @RequestMapping("")
    public ResponseEntity hello(@RequestBody SimpleObject simpleObject) {
        String token = HttpKit.getRequest().getHeader(jwtProperties.getHeader()).substring(7);
        String md5KeyFromToken = jwtTokenUtil.getMd5KeyFromToken(token);
        System.out.println(simpleObject.getUser()+md5KeyFromToken);
        return ResponseEntity.ok("请求成功!");
    }
}

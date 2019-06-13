package com.stylefeng.guns.config.web;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Configuration;

import com.stylefeng.guns.core.shiro.EasyTypeToken;
import com.stylefeng.guns.core.shiro.ShiroLoginType;

@Configuration
public class MyRetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        EasyTypeToken tk = (EasyTypeToken) authcToken;
        if(tk.getType().equals(ShiroLoginType.NOPASSWD)){
            return true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }
}
package com.stylefeng.guns.rest.modular.auth.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogMailCheckMapper;
import com.stylefeng.guns.rest.common.persistence.dao.WayAccessoryLogMobileCheckMapper;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMailCheck;
import com.stylefeng.guns.rest.common.persistence.model.WayAccessoryLogMobileCheck;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {
	//
	@Autowired
	WayAccessoryLogMobileCheckMapper wayAccessoryLogMobileCheckMapper;
	@Autowired
	WayAccessoryLogMailCheckMapper wayAccessoryLogMailCheckMapper;

	@Override
	public boolean validate(Credence credence) {
		String mailOrPhone = credence.getCredenceName();
		if (mailOrPhone.contains("@")) { // 邮箱
			List<WayAccessoryLogMailCheck> checks = wayAccessoryLogMailCheckMapper
					.selectWayAccessoryLogMailCheckByCodeAndMail(credence.getCredenceCode(),
							credence.getCredenceName());
			if (checks != null && checks.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else { // 其他默认手机
			List<WayAccessoryLogMobileCheck> checks = wayAccessoryLogMobileCheckMapper
					.selectWayAccessoryLogMobileCheckByCodeAndPhone(credence.getCredenceCode(),
							credence.getCredenceName());
			if (checks != null && checks.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
	}
}

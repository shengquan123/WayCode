package com.stylefeng.guns.modular.system.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WayFido2Device {
	private Integer id;
    private String userId;
    private String userName;
    private Integer rpId;
    private String rpName;
    private String deviceName;
    private String publicKeyCose;
    private String credentialId;
    private String userHandle;
    private Long signatureCount;
    private String version;
    private Date registrationTime;
    private Date lastAuthTime;
    private Date updateTime;
}

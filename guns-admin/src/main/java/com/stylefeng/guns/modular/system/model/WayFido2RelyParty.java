package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
@TableName("way_fido2_rely_party")
public class WayFido2RelyParty extends Model<WayFido2RelyParty> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("rp_id")
    private String rpId;
    @TableField("rp_name")
    private String rpName;
    private String origins;
    @TableField("app_id")
    private String appId;
    private String token;
    @TableField("add_time")
    private Date addTime;
    @TableField("expire_time")
    private Date expireTime;
    @TableField("update_time")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

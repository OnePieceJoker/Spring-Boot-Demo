package com.lizhihong.moduleboot.vo;

import lombok.Data;

/**
 *
 * @author Mr.Joker
 * @date 2020/04/06
 * @time 13:24:51
 * @description 数据库连接信息
 */
@Data
public class DbInfo {

    private String ip;
    private String port;
    private String dbName;
    private String driveClassName;
    private String username;
    private String password;
}
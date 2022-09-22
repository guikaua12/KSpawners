package me.approximations.spawners.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MConfig {
    private String dbType;
    private String dbIp;
    private String dbDb;
    private String dbUser;
    private String dbPassword;
    private String dbTable;
}

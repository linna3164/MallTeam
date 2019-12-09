package com.xmu.log.domain;

import com.xmu.log.standard.Admin;
import com.xmu.log.standard.Log;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 */
@Alias("olog")
public class LogDto extends Log {

    private Admin admin;

    public LogDto(Log log){
        super();
        super.setAction(log.getAction());
        super.setAdminId(log.getAdminId());
        super.setGmtCreate(LocalDateTime.now());
        super.setGmtModified(LocalDateTime.now());
        super.setId(log.getId());
        super.setIp(log.getIp());
        super.setStatusCode(log.getStatusCode());
        super.setType(log.getType());
        super.setActionId(log.getActionId());
    }



    @Override
    public String toString() {
        return "OLog{" +
                "admin=" + admin +
                "} " + super.toString();
    }


    /****************************************************
     * 生成代码
     ****************************************************/

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}

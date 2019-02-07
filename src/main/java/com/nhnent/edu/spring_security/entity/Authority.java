package com.nhnent.edu.spring_security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORITIES")
public class Authority {
    @Id
    @Column(name = "MNAME")
    private String name;

    @Column(name = "AUTHORITY")
    private String authority;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}

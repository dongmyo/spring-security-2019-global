package com.nhnent.edu.spring_security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBERS")
public class Member {
    @Id
    @Column(name = "MNAME")
    private String name;

    @Column(name = "PWD")
    private String password;


    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Authority authority;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

}

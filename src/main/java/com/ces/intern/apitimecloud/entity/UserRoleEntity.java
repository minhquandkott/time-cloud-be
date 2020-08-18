package com.ces.intern.apitimecloud.entity;

import com.ces.intern.apitimecloud.util.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_company")
public class UserRoleEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    private CompanyEntity company;


    private Role role;

    public UserRoleEntity(){}

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static class Id implements Serializable {
        private static final long serialVersionUID = -5535178767632113317L;
        @Column(name = "userId")
        private Integer userId;

        @Column(name = "companyId")
        private Integer companyId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }
    }
}

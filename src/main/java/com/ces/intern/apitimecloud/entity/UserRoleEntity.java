package com.ces.intern.apitimecloud.entity;

import com.ces.intern.apitimecloud.util.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_company", schema = "public")
public class UserRoleEntity {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST,CascadeType.REFRESH
            })
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
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

    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = -5535178767632113317L;
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "company_id")
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return userId.equals(id.userId) &&
                    companyId.equals(id.companyId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, companyId);
        }
    }
}

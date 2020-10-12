package com.ces.intern.apitimecloud.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "company_user", schema = "public")
public class UserRoleEntity  implements Serializable{

    private static final long serialVersionUID = 2710222671666735997L;

    @EmbeddedId
    private EmbedId embedId = new EmbedId();

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public UserRoleEntity(UserEntity user, CompanyEntity company, RoleEntity role) {
        this.user = user;
        this.company = company;
        this.role = role;
    }

    @Column(name = "create_at", nullable = false)
    private Date createAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    public void setBasicInfo(Date createAt, Integer createdBy){
        this.createAt = createAt;
        this.createdBy = createdBy;

    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbedId implements Serializable {
        private static final long serialVersionUID = -5535178767632113317L;
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "company_id")
        private Integer companyId;

        @Column(name="role_id")
        private Integer roleId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmbedId id = (EmbedId) o;
            return userId.equals(id.userId) &&
                    companyId.equals(id.companyId) &&
                    roleId.equals(id.roleId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, companyId, roleId);
        }
    }
}

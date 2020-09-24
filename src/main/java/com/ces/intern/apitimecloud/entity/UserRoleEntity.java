package com.ces.intern.apitimecloud.entity;

import com.ces.intern.apitimecloud.util.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_company", schema = "public")
public class UserRoleEntity {

    @EmbeddedId
    private Id id = new Id();

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

    @ManyToOne( cascade = {
            CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Embedded
    private EmbedEntity embedEntity;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Id implements Serializable {
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
            Id id = (Id) o;
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

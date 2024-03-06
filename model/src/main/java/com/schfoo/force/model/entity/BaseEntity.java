package com.schfoo.force.model.entity;

import com.schfoo.force.model.constant.CommonConstant;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @CreatedBy
    @JoinColumn(name = "created_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMainEntity createdUser;

    @LastModifiedBy
    @JoinColumn(name = "updated_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMainEntity updatedUser;

    // CommonConstant.Status
    @Column(name = "status_data")
    private String statusData = CommonConstant.Status.active;

}

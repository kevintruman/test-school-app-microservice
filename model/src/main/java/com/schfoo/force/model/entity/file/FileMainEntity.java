package com.schfoo.force.model.entity.file;

import com.schfoo.force.model.entity.BaseEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "file_main")
public class FileMainEntity extends BaseEntity {

    @Column(name = "table_id")
    private Long tableId;

    // FileConstant.Type
    private String type;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_user_id")
    private UserMainEntity uploadUser;

}

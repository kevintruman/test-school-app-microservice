package com.schfoo.force.model.entity.attendance;

import com.schfoo.force.model.entity.BaseEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "attendance_main")
public class AttendanceMainEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserMainEntity user;

    @Column(name = "clock_in_server")
    private Date clockInServer;

    @Column(name = "clock_out_server")
    private Date clockOutServer;

    @Column(name = "clock_in_client")
    private Date clockInClient;

    @Column(name = "clock_out_client")
    private Date clockOutClient;

    private Double latIn;
    private Double lonIn;

    private Double latOut;
    private Double lonOut;

}

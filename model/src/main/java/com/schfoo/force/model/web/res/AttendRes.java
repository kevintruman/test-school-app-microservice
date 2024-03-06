package com.schfoo.force.model.web.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.attendance.AttendanceMainEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class AttendRes implements Serializable {

    private Long id;

    private Date clockInServer;
    private Date clockOutServer;
    private Date clockInClient;
    private Date clockOutClient;

    private Double latIn;
    private Double lonIn;
    private Double latOut;
    private Double lonOut;

    @JsonIgnore
    public static AttendRes build(AttendanceMainEntity attendanceMain) {
        return AttendRes.builder()
                .id(attendanceMain.getId())
                .clockInServer(attendanceMain.getClockInServer())
                .clockOutServer(attendanceMain.getClockOutServer())
                .clockInClient(attendanceMain.getClockInClient())
                .clockOutClient(attendanceMain.getClockOutClient())
                .latIn(attendanceMain.getLatIn())
                .lonIn(attendanceMain.getLonIn())
                .latOut(attendanceMain.getLatOut())
                .lonOut(attendanceMain.getLonOut())
                .build();
    }

}

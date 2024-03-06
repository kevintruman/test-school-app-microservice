package com.schfoo.force.model.web.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schfoo.force.model.entity.attendance.AttendanceMainEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
public class AttendReq implements Serializable {

    @NotNull(message = "date client is mandatory")
    private Long date;

    private Double lat;
    private Double lon;

    @JsonIgnore
    public AttendanceMainEntity toNewAttendanceMainEntity(UserMainEntity userMain) {
        AttendanceMainEntity attendanceMain = new AttendanceMainEntity();
        attendanceMain.setUser(userMain);

        Instant instant = Instant.ofEpochSecond(date);
        attendanceMain.setClockInClient(Date.from(instant));
        attendanceMain.setClockInServer(new Date());
        attendanceMain.setLatIn(lat);
        attendanceMain.setLonIn(lon);
        return attendanceMain;
    }

    @JsonIgnore
    public AttendanceMainEntity toUpdateAttendanceMainEntity(AttendanceMainEntity attendanceMain) {
        Instant instant = Instant.ofEpochSecond(date);
        attendanceMain.setClockOutClient(Date.from(instant));
        attendanceMain.setClockOutServer(new Date());
        attendanceMain.setLatOut(lat);
        attendanceMain.setLonOut(lon);
        return attendanceMain;
    }

}

package com.schfoo.force.belesson.service;

import com.schfoo.force.belesson.repo.LessonScheduleRepo;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.model.constant.DateConstant;
import com.schfoo.force.model.constant.UserConstant;
import com.schfoo.force.model.entity.lesson.LessonScheduleEntity;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.web.res.LessonScheduleRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class ScheduleService {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private LessonScheduleRepo lessonScheduleRepo;

    public List<LessonScheduleRes> getTeacherLessonNow() {
        UserMainEntity user = sessionService.getUserLocal(false);
        privilegeService.checkByUserType(user, List.of(UserConstant.Type.teacher), false);

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayName = DateConstant.Day.getDayName(dayOfWeek);

        List<LessonScheduleEntity> lessonScheduleEntityList = lessonScheduleRepo
                .getListByTeacherUserIdAndDayAndIsActive(user.getId(), dayName);

        return lessonScheduleEntityList.stream().map(LessonScheduleRes::build).toList();
    }

}

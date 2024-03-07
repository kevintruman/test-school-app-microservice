package com.schfoo.force.beuser.service;

import com.schfoo.force.beuser.repo.UserMainRepo;
import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.model.PageDataResponse;
import com.schfoo.force.model.entity.user.UserMainEntity;
import com.schfoo.force.model.web.res.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class UserService {

    @Autowired
    private UserMainRepo userMainRepo;

    public PageDataResponse<UserRes> getStudentsByCorporateId(
            Long corporateId, String fullName, int page, int size) {
        Page<UserMainEntity> userMainEntityPage = userMainRepo
                .getPageByCorporateIdAndFullNameLikeAndIsStudentTypeAndIsActive(
                        corporateId, fullName, PageRequest.of(page - 1, size));
        List<UserRes> list = userMainEntityPage.map(UserRes::build).toList();

        return new PageDataResponse<>(userMainEntityPage, list);
    }

    public UserRes getUserById(Long userId) {
        UserMainEntity userMain = userMainRepo.getOneByIdAndIsActive(userId);
        return UserRes.build(userMain);
    }

}

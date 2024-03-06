package com.schfoo.force.beuser.service;

import com.schfoo.force.helper.exception.TrxException;
import com.schfoo.force.helper.util.ExceptionUtil;
import com.schfoo.force.model.entity.user.UserMainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = TrxException.class)
public class PrivilegeService {

    public void checkByUserType(UserMainEntity userMain, List<String> userTypes, boolean rollbackIfError) {
        boolean isEmpty = userTypes.stream().filter(e -> userMain.getUserType().equalsIgnoreCase(e))
                .toList().isEmpty();
        if (isEmpty) {
            throw ExceptionUtil.thr("You dont have an access", rollbackIfError);
        }
    }

}

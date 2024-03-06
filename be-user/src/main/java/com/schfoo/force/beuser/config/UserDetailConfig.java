package com.schfoo.force.beuser.config;

import com.schfoo.force.beuser.repo.UserMainRepo;
import com.schfoo.force.model.entity.user.UserMainEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDetailConfig implements UserDetailsService {

    @Autowired
    private UserMainRepo userMainRepo;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMainEntity userMain = userMainRepo.getOneByUsernameOrEmailAndIsActive(username);
        if (Objects.isNull(userMain)) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(userMain.getUsername(), userMain.getPassword(), List.of());
    }

}

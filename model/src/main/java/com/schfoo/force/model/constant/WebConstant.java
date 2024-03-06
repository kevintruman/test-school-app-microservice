package com.schfoo.force.model.constant;

public interface WebConstant {

    class Auth {
        public static final String[] whitelist = {
                "/user/v1.0/auth/sign-in",
                "/user/v1.0/auth/validate-token",
                "/user/v1.0/auth/register",
        };
    }

}

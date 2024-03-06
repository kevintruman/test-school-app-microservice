package com.schfoo.force.model.constant;

public interface CommonConstant {

    class Status {
        public static final String active = "ACTIVE";
        public static final String notActive = "NOT_ACTIVE";
    }

    class Regex {
        public static final String numeric = "\\d+";
        public static final String alphanumeric = "^[a-zA-Z0-9]+$";
        public static final String alphabet = "^[a-zA-Z]+$";
        public static final String alphanumericDotSpace = "^[a-zA-Z0-9.' ]+$";
        public static final String email = "/[^\\s]*@[a-z0-9.-]*/i";
    }

}

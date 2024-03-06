package com.schfoo.force.model.constant;

public interface DateConstant {

    class Day {
        public static final String sunday = "SUNDAY";
        public static final String monday = "MONDAY";
        public static final String tuesday = "TUESDAY";
        public static final String wednesday = "WEDNESDAY";
        public static final String thursday = "THURSDAY";
        public static final String friday = "FRIDAY";
        public static final String saturday = "SATURDAY";

        public static String getDayName(int day) {
            return switch (day) {
                case 2 -> monday;
                case 3 -> tuesday;
                case 4 -> wednesday;
                case 5 -> thursday;
                case 6 -> friday;
                case 7 -> saturday;
                default -> sunday;
            };
        }
    }

}

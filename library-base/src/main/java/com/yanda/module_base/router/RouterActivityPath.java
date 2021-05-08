package com.yanda.module_base.router;

public class RouterActivityPath {
    /**
     * Main组件
     */
    public static class Main {
        private static final String MAIN = "/module_main";

        public static final String PAGER_POSTER = MAIN + "/Poster";
        /**
         * 主页面
         */
        public static final String PAGER_MAIN = MAIN + "/Main";
    }

    /**
     * 登录组件
     */
    public static class Login {
        private static final String LOGIN = "/module_login";
        /**
         * 登录页
         */
        public static final String PAGER_LOGIN = LOGIN + "/Login";

    }

    /**
     * 课程
     * **/
    public static class Course{
        public static final String COURSE = "/module_course";
        public static final String PAGER_COURSE = COURSE + "/Course";
    }

    /**
     * 考试
     * **/
    public static class Exam{
        public static final String EXAM = "/module_exam";
        public static final String PAGER_BEGIN = EXAM + "/Exam";
    }

    /**
     * web 组件
     */
    public static class Web {
        public static final String WEB = "/module_web";
        public static final String PAGER_WEB = WEB + "/Web";

    }

    public static class Square {
        public static final String SQUARE = "/module_square";
        public static final String PAGER_SQUARE_LIST = SQUARE + "/square_list";
    }

}

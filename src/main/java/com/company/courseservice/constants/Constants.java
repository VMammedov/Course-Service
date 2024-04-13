package com.company.courseservice.constants;

public class Constants {

    public static class Roles {
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
    }

    public static class ErrorMessages{
        public static final String COURSE_NOT_FOUND = "Course not found!";
        public static final String SECTION_NOT_FOUND = "Section not found!";
    }

    public static class CacheNames {
        public static final String CATEGORY_NAMES = "categoryNames";
        public static final String CATEGORY_NAMES_WITH_SUB = "categoryNamesWithSubCategories";
        public static final String SUB_CATEGORY_NAMES = "subCategoryNames";
        public static final String COURSE = "course";

        public static final String REVIEW = "review";

        public static final String SECTION = "section";
    }

}

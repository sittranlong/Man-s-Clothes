package io.github.pudo58.constant;

import java.util.List;

public class UserConst {
    public static Integer STATUS_ACTIVE = 1;
    public static Integer STATUS_INACTIVE = 0;
    public static Integer STATUS_LOCKED = 2;

    public static String ROLE_ADMIN = "ROLE_ADMIN";
    public static String ROLE_USER = "ROLE_USER";
    public static String ROLE_GUEST = "ROLE_GUEST";

    public static List<String> ROLE_LIST = List.of(ROLE_ADMIN, ROLE_USER, ROLE_GUEST);
}

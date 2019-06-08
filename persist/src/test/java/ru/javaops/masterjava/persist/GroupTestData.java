package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public class GroupTestData {
    public static Group TOPJAVA06;
    public static Group TOPJAVA07;
    public static Group TOPJAVA08;
    public static Group MASTERJAVA01;
    public static List<Group> GROUPS;

    public static void init() {
        TOPJAVA06 = new Group("topjava06", GroupType.FINISHED, new Project(10000, "topjava", "Topjava"));
        TOPJAVA07 = new Group("topjava07", GroupType.FINISHED, new Project(10000, "topjava", "Topjava"));
        TOPJAVA08 = new Group("topjava08", GroupType.CURRENT, new Project(10000, "topjava", "Topjava"));
        MASTERJAVA01 = new Group("masterjava01", GroupType.CURRENT, new Project(10001, "masterjava", "Masterjava"));
        GROUPS = ImmutableList.of(MASTERJAVA01, TOPJAVA06, TOPJAVA07, TOPJAVA08);
    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            GROUPS.forEach(dao::insert);
        });
    }
}

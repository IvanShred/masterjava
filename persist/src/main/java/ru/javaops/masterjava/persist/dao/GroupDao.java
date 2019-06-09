package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao {

    public Group insert(Group group) {
        if (group.isNew()) {
            int id = insertGeneratedId(group.getName(), group.getType(), group.getProjectId());
            group.setId(id);
        } else {
            insertWitId(group.getId(), group.getName(), group.getType(), group.getProjectId());
        }
        return group;
    }

    @SqlUpdate("INSERT INTO groups (name, type, project_id) VALUES (:name, CAST(:type AS GROUP_TYPE), :projectId) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@Bind("name") String name,
                                   @Bind("type") GroupType groupType,
                                   @Bind("projectId") int projectId);

    @SqlUpdate("INSERT INTO groups (id, name, type, project_id) VALUES (:id, :name, CAST(:type AS GROUP_TYPE), :projectId) ")
    abstract void insertWitId(@Bind("id") int id,
                              @Bind("name") String name,
                              @Bind("type") GroupType groupType,
                              @Bind("projectId") int projectId);

    @SqlUpdate("TRUNCATE groups CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM groups ORDER BY groups.name LIMIT :it")
    public abstract List<Group> getWithLimit(@Bind int limit);
}

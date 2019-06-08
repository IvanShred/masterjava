package ru.javaops.masterjava.persist.model;

import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Group extends BaseEntity {
    private @NonNull String name;
    private @NonNull GroupType type;
    private @NonNull Project project;

    public Group(Integer id, String name, GroupType type, Project project) {
        this(name, type, project);
        this.id = id;
    }
}

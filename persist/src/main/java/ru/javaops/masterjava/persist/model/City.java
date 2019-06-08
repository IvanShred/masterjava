package ru.javaops.masterjava.persist.model;

import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    private @NonNull String designation;
    private @NonNull String cityName;

    public City(Integer id, String designation, String cityName) {
        this(designation, cityName);
        this.id = id;
    }
}

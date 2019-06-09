package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    private @NonNull String designation;
    @Column("city_name")
    private @NonNull String cityName;

    public City(Integer id, String designation, String cityName) {
        this(designation, cityName);
        this.id = id;
    }
}

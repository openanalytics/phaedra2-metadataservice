package eu.openanalytics.phaedra.metadataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Data
@Table("hca_property")
public class Property {
    @Id
    @JsonIgnore
    private Long id;
    @Column("object_id")
    @NotNull
    private Long objectId;
    @Column("object_class")
    @NotNull
    private String objectClass;
    @Column("property_name")
    @NotNull
    private String propertyName;
    @Column("property_value")
    @NotNull
    private String propertyValue;

    public Property(Long objectId, String objectClass, String propertyName) {
        this.objectId = objectId;
        this.objectClass = objectClass;
        this.propertyName = propertyName;
    }
}

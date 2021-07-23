package eu.openanalytics.phaedra.metadataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("hca_tagged_object")
public class TaggedObject {
    @Id
    @JsonIgnore
    private Long id;
    @Column("object_id")
    private Long objectId;
    @Column("object_class")
    private String objectClass;
    @Column("tag_id")
    private Long tagId;

    public TaggedObject(Long objectId, String objectClass, Long tagId) {
        this.objectId = objectId;
        this.objectClass = objectClass;
        this.tagId = tagId;
    }
}

package eu.openanalytics.phaedra.metadataservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Table("hca_tag")
public class Tag {
    @Id
    private Long id;
    private String name;

    @MappedCollection(keyColumn = "tag_id", idColumn = "id")
    private Set<TaggedObject> taggedObjects = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public void addObject(TaggedObject taggedObject) {
        this.taggedObjects.add(taggedObject);
    }

    public void removeObject(Long objectId, String objectClass) {
        Set<TaggedObject> toBeRemoved = taggedObjects.stream()
                .filter(to -> (to.getObjectId().equals(objectId) && to.getObjectClass().equals(objectClass)))
                .collect(Collectors.toSet());
        taggedObjects.removeAll(toBeRemoved);
    }
}

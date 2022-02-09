package eu.openanalytics.phaedra.metadataservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table("hca_tag")
public class Tag {

	@Id
    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}

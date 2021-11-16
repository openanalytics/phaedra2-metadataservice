package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

//    @Query("select * from metadata.hca_tag as ht where ht.name = :tagName")
    Tag findByName(String tagName);

    @Query("select * from metadata.hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id where hto.object_id = :objectId")
    List<Tag> findByTaggedObjectId(Long objectId);

    @Query("select * from metadata.hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id where hto.object_class = :objectClass")
    List<Tag> findByTaggedObjectClass(String objectClass);

}

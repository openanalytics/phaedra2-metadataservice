package eu.openanalytics.phaedra.metadataservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.metadataservice.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String tagName);

    List<Tag> findByIdIn(Set<Long> ids);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId")
    List<Tag> findByObjectId(Long objectId);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_class = :objectClass")
    List<Tag> findByObjectClass(String objectClass);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId " +
            "and hto.object_class = :objectClass")
    List<Tag> findByObjectIdAndObjectClass(Long objectId, String objectClass);

}

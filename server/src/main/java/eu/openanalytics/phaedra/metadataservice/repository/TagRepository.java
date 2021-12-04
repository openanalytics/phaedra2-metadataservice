package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Tag;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String tagName);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId")
    List<Tag> findByObjectId(Long objectId);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_class = cast(:objectClass as objectclass)")
    List<Tag> findByObjectClass(ObjectClass objectClass);

    @Query("select * from hca_tag as ht inner join hca_tagged_object as hto on ht.id = hto.tag_id " +
            "where hto.object_id = :objectId " +
            "and hto.object_class = cast(:objectClass as objectclass)")
    List<Tag> findByObjectIdAndObjectClass(Long objectId, ObjectClass objectClass);

}

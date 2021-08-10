package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggedObjectRepository extends CrudRepository<TaggedObject, Long> {
    @Query("select * from metadata.hca_tagged_object hto where hto.object_id = :objectId and hto.object_class = :objectClass")
    TaggedObject findTaggedObjectByObjectIdAndObjectClass(Long objectId, String objectClass);

    @Query("select * from metadata.hca_tagged_object hto where hto.object_class = :objectClass")
    List<TaggedObject> findAllByObjectClass(String objectClass);
}

package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggedObjectRepository extends CrudRepository<TaggedObject, Long> {
    @Query("select * from hca_tagged_object hto " +
            "where hto.object_id = :objectId " +
            "and hto.object_class = cast(:objectClass as objectclass)")
    List<TaggedObject> findTaggedObjectByObjectIdAndObjectClass(Long objectId, ObjectClass objectClass);

    @Query("select * from hca_tagged_object hto where hto.object_class = cast(:objectClass as objectclass)")
    List<TaggedObject> findAllByObjectClass(ObjectClass objectClass);
}
package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.metadataservice.model.Property;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query("select * from hca_property as p " +
            "where p.object_id = :objectId " +
            "and p.property_name = :propertyName " +
            "and p.object_class = :objectClass")
    Property findByObjectIdAndPropertyNameAndObjectClass(Long objectId, String propertyName, String objectClass);

    @Query("select * from hca_property as p " +
            "where (:objectId is null or p.object_id = :objectId) " +
            "and (:propertyName is null or p.property_name = :propertyName) " +
            "and (:objectClass is null or p.object_class = :objectClass)")
    List<Property> findAll(Long objectId, String propertyName, String objectClass);
}

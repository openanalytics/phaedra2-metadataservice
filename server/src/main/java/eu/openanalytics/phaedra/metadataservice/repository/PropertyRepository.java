package eu.openanalytics.phaedra.metadataservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.metadataservice.model.Property;

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
    
    List<Property> findByObjectIdInAndObjectClass(Set<Long> objectId, String objectClass);
   
}

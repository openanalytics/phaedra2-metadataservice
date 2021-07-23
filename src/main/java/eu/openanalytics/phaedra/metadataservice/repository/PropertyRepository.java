package eu.openanalytics.phaedra.metadataservice.repository;

import eu.openanalytics.phaedra.metadataservice.model.Property;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    @Query("select * from metadata.hca_property as p where p.object_id = :objectId")
    List<Property> findAllByObjectId(Long objectId);

    @Query("select distinct p.property_name, p.object_class from metadata.hca_property as p")
    List<Property> findAvailableProperties();

    @Query("select * from metadata.hca_property as p where p.object_class = :objectClass")
    List<Property> findAvailablePropertiesByObjectClass(String objectClass);

    @Query("select * from metadata.hca_property as p where p.property_name = :propertyName")
    List<Property> findAvailablePropertiesByByPropertyName(String propertyName);
}

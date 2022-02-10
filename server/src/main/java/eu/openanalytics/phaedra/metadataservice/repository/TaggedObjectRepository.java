package eu.openanalytics.phaedra.metadataservice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.openanalytics.phaedra.metadataservice.model.TaggedObject;

@Repository
public interface TaggedObjectRepository extends CrudRepository<TaggedObject, Long> {
	
	@Modifying
	@Query("delete from hca_tagged_object hto where hto.tag_id = :tagId and hto.object_id = :objectId and hto.object_class = :objectClass")
	void deleteByTagIdAndObjectIdAndObjectClass(Long tagId, Long objectId, String objectClass);
	
    List<TaggedObject> findByObjectIdInAndObjectClass(Set<Long> objectIds, String objectClass);
}

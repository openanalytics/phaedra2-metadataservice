package eu.openanalytics.phaedra.metadataservice.client.impl;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import eu.openanalytics.phaedra.metadataservice.dto.PropertyDTO;
import eu.openanalytics.phaedra.metadataservice.dto.TagDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import java.util.List;
import java.util.Map;
import org.springframework.core.env.Environment;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GraphQLMetaDataServiceClient implements MetadataServiceClient {

  private final IAuthorizationService authService;
  private final WebClient webClient;
  private final HttpGraphQlClient httpGraphQlClient;

  private static final String PROP_BASE_URL = "phaedra.metadata-service.base-url";
  private static final String DEFAULT_BASE_URL = "http://phaedra-metadata-service:8080/phaedra/metadata-service";

  public GraphQLMetaDataServiceClient(IAuthorizationService authService, Environment environment) {
    String baseUrl = environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL);
    this.authService = authService;
    String bearerToken = authService.getCurrentBearerToken();
    this.webClient = WebClient.builder()
        .baseUrl(String.format("%s/graphql", baseUrl))
        .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();

    this.httpGraphQlClient = HttpGraphQlClient.create(this.webClient);
  }

  @Override
  public void addTags(String objectClass, long objectId, List<String> tags) {

  }

  @Override
  public void addProperties(String objectClass, long objectId, Map<String, String> properties) {

  }

  @Override
  public List<TagDTO> getTags(String objectClass, long objectId) {
    return List.of();
  }

  @Override
  public List<PropertyDTO> getProperties(String objectClass, long objectId) {
    return List.of();
  }

  public List<MetadataDTO> getMetadata(List<Long> objectIds, ObjectClass objectClass) {
    String document = """
        { 
          metadata(objectIds: %s, objectClass: "%s") { 
            objectId
            tags {
              tag
            }
            properties { 
              propertyName
              propertyValue
            }
          }
        }
        """.formatted(objectIds, objectClass.name());
    MetadataDTO[] result = httpGraphQlClient
        .document(document)
        .retrieveSync("metadata")
        .toEntity(MetadataDTO[].class);

    return List.of(result);
  }

}

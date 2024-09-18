package eu.openanalytics.phaedra.metadataservice.client.impl;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceGraphQlClient;
import eu.openanalytics.phaedra.metadataservice.dto.MetadataDTO;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import eu.openanalytics.phaedra.util.auth.IAuthorizationService;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MetaDataServiceHttpGraphQlClient implements MetadataServiceGraphQlClient {

  private final IAuthorizationService authService;
  private final WebClient webClient;
  private final HttpGraphQlClient httpGraphQlClient;

  private static final String PROP_BASE_URL = "phaedra.metadata-service.base-url";
  private static final String DEFAULT_BASE_URL = "http://phaedra-metadata-service:8080/phaedra/metadata-service";

  public MetaDataServiceHttpGraphQlClient(IAuthorizationService authService, Environment environment) {
    String baseUrl = environment.getProperty(PROP_BASE_URL, DEFAULT_BASE_URL);
    this.authService = authService;
    String bearerToken = authService.getCurrentBearerToken();
    this.webClient = WebClient.builder()
        .baseUrl(String.format("%s/graphql", baseUrl))
        .defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();

    this.httpGraphQlClient = HttpGraphQlClient.builder(this.webClient)
        .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", bearerToken))
        .build();
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

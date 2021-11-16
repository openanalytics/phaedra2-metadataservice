package eu.openanalytics.phaedra.metadataservice.client.impl;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpMetadataServiceClient implements MetadataServiceClient {

    private final RestTemplate restTemplate;

    public HttpMetadataServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}

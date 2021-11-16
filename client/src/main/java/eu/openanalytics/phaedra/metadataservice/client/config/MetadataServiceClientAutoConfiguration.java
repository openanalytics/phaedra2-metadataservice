package eu.openanalytics.phaedra.metadataservice.client.config;

import eu.openanalytics.phaedra.metadataservice.client.MetadataServiceClient;
import eu.openanalytics.phaedra.metadataservice.client.impl.HttpMetadataServiceClient;
import eu.openanalytics.phaedra.util.PhaedraRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetadataServiceClientAutoConfiguration {

    @Bean
    public MetadataServiceClient metadataServiceClient(PhaedraRestTemplate phaedraRestTemplate) {
        return new HttpMetadataServiceClient(phaedraRestTemplate);
    }
}

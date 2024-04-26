package eu.openanalytics.phaedra.metadataservice.config;

import eu.openanalytics.phaedra.metadataservice.enumeration.Actor;
import eu.openanalytics.phaedra.metadataservice.enumeration.ObjectClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;

@Configuration
public class DatabaseConfig extends AbstractJdbcConfiguration {

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                new ObjectClassReadConverter(),
                new ObjectClassWriteConverter(),
                new ActorReadConverter(),
                new ActorWriteConverter()
        ));
    }

    @WritingConverter
    public class ObjectClassWriteConverter implements Converter<ObjectClass, String> {
        @Override
        public String convert(ObjectClass objectClass) {
            return objectClass == null ? null : objectClass.name();
        }
    }

    @ReadingConverter
    public class ObjectClassReadConverter implements Converter<String, ObjectClass> {
        @Override
        public ObjectClass convert(String dbValue) {
            try {
                return dbValue == null ? null : ObjectClass.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    @WritingConverter
    public class ActorWriteConverter implements Converter<Actor, String> {
        @Override
        public String convert(Actor actor) {
            return actor == null ? null : actor.name();
        }
    }

    @ReadingConverter
    public class ActorReadConverter implements Converter<String, Actor> {
        @Override
        public Actor convert(String dbValue) {
            try {
                return dbValue == null ? null : Actor.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}

package com.example.myuserservice.config;

import com.example.myuserservice.entity.history.ChangedValues;
import com.example.myuserservice.utils.JsonUtils;
import io.micrometer.common.lang.NonNullApi;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.util.List;

@Configuration
public class DatabaseConfig {

    @Bean
    R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory factory) {
        R2dbcDialect dialect = DialectResolver.getDialect(factory);
        return R2dbcCustomConversions.of(dialect,
                List.of(
                        new ChangedValuesToJsonConverter(),
                        new JsonToChangedValuesConverter()
                )
        );
    }

    @WritingConverter
    @NonNullApi
    private static class ChangedValuesToJsonConverter implements Converter<ChangedValues, Json> {

        @Override
        public Json convert(ChangedValues source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToChangedValuesConverter implements Converter<Json, ChangedValues> {

        @Override
        public ChangedValues convert(Json source) {
            return JsonUtils.readValue(source.asString(), ChangedValues.class);
        }
    }
}

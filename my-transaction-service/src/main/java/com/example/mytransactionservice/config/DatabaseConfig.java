package com.example.mytransactionservice.config;

import com.example.mytransactionservice.entity.transaction.CardData;
import com.example.mytransactionservice.entity.transaction.Customer;
import com.example.mytransactionservice.entity.transaction.Transaction;
import com.example.mytransactionservice.utils.JsonUtils;
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
                        new CardDataToJsonConverter(),
                        new JsonToCardDataConverter(),
                        new CustomerToJsonConverter(),
                        new JsonToCustomerConverter(),
                        new TransactionToJsonConverter(),
                        new JsonToTransactionConverter()
                )
        );
    }

    @WritingConverter
    @NonNullApi
    private static class CardDataToJsonConverter implements Converter<CardData, Json> {

        @Override
        public Json convert(CardData source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToCardDataConverter implements Converter<Json, CardData> {

        @Override
        public CardData convert(Json source) {
            return JsonUtils.readValue(source.asString(), CardData.class);
        }
    }

    @WritingConverter
    @NonNullApi
    private static class CustomerToJsonConverter implements Converter<Customer, Json> {

        @Override
        public Json convert(Customer source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToCustomerConverter implements Converter<Json, Customer> {

        @Override
        public Customer convert(Json source) {
            return JsonUtils.readValue(source.asString(), Customer.class);
        }
    }

    @WritingConverter
    @NonNullApi
    private static class TransactionToJsonConverter implements Converter<Transaction, Json> {

        @Override
        public Json convert(Transaction source) {
            return Json.of(JsonUtils.writeValueAsString(source));
        }
    }

    @ReadingConverter
    private static class JsonToTransactionConverter implements Converter<Json, Transaction> {

        @Override
        public Transaction convert(Json source) {
            return JsonUtils.readValue(source.asString(), Transaction.class);
        }
    }
}

package com.atman.server.Graphql;

import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class GraphQLScalarConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType localDateTimeScalar = GraphQLScalarType.newScalar()
                                                                 .name("DateTime")
                                                                 .description("Java LocalDateTime scalar")
                                                                 .coercing(new Coercing<LocalDateTime, String>() {
                                                                     @Override
                                                                     public String serialize(Object dataFetcherResult) {
                                                                         if(dataFetcherResult instanceof LocalDateTime) {
                                                                             return ((LocalDateTime) dataFetcherResult).format(DateTimeFormatter.ISO_DATE_TIME);
                                                                         }
                                                                         throw new CoercingSerializeException("Expected a LocalDateTime object.");
                                                                     }

                                                                     @Override
                                                                     public LocalDateTime parseValue(Object input) {
                                                                         try {
                                                                             return LocalDateTime.parse(input.toString(), DateTimeFormatter.ISO_DATE_TIME);
                                                                         } catch(Exception e) {
                                                                             throw new CoercingParseValueException("Expected a valid ISO-8601 DateTime string.");
                                                                         }
                                                                     }

                                                                     public LocalDateTime parseLiteral(graphql.language.Value<?> input) {
                                                                         if(input instanceof graphql.language.StringValue) {
                                                                             try {
                                                                                 return LocalDateTime.parse(((graphql.language.StringValue) input).getValue(), DateTimeFormatter.ISO_DATE_TIME);
                                                                             } catch(Exception e) {
                                                                                 throw new CoercingParseLiteralException("Expected a valid ISO-8601 DateTime string literal.");
                                                                             }
                                                                         }
                                                                         throw new CoercingParseLiteralException("Expected a StringValue.");
                                                                     }
                                                                 })
                                                                 .build();
        return wiringBuilder -> wiringBuilder.scalar(localDateTimeScalar)
                                             .scalar(ExtendedScalars.Date)
                                             .scalar(ExtendedScalars.GraphQLBigDecimal)
                                             .scalar(ExtendedScalars.UUID);
    }
}

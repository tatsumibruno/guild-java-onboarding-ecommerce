package com.totvs.onboarding.ecommerce.catalogoproduto.infrastructure.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.totvs.onboarding.ecommerce.catalogoproduto.domain.CodigoProduto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodigoProdutoJsonConverter {

    public static class CodigoProdutoJsonSerializer extends JsonSerializer<CodigoProduto> {
        @Override
        public void serialize(CodigoProduto codigoProduto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(codigoProduto.asString());
        }
    }

    public static class CodigoProdutoJsonDeserializer extends JsonDeserializer<CodigoProduto> {
        @Override
        public CodigoProduto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return CodigoProduto.from(jsonParser.getValueAsString());
        }
    }
}

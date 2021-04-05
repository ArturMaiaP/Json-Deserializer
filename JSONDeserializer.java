package br.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.gov.al.sefaz.shared.converter.JsonConverter.asJsonToList;
import static br.gov.al.sefaz.util.DateUtils.FORMATO_US;
import static br.gov.al.sefaz.util.DateUtils.stringToZonedDateTime;
import static br.gov.al.sefaz.util.NumbersUtil.convertStringToBigDecimal;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ObjectDTODeserializer extends StdDeserializer<ObjectDTO> {

    public  ObjectDTODeserializer() {
        this(null);
    }

    public  ObjectDTODeserializer(Class vc) {
        super(vc);
    }

    @Override
    public ObjectDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        
        ObjectDTO jsonObject = new ObjectDTO();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if(node.has("id")){
            Long id = node.get("id").asLong();
            jsonObject.setId(id);
        }

        if(node.has("price")) {
            String price = node.get("price").asText();
            jsonObject.setPrice(convertStringToBigDecimal(price));
        }

        if(node.has("quantity")){
            Integer quantity = node.get("quantity").asInt();
            jsonObject.setQuantity(quantity);
        }

        if(node.has("description")){
            String description = (node.get("description").isNull()) ? null : node.get("description").asText();
            jsonObject.setDescription(description);
        }

        if(node.has("hasStock")){
            boolean hasStock = node.get("hasStock").asBoolean();
            jsonObject.setHasStock(hasStock);
        }

        if(node.has("releaseDate")){
            String releaseDate = node.get("releaseDate").asText();
            ZonedDateTime date = stringToZonedDateTime(releaseDate, FORMATO_US);
            jsonObject.setReleaseDate(date);
        }


        if(node.has("pathImages")){
            String pathImagesString = node.get("pathImages").toString();
            List<String> pathImagesList = asJsonToList(pathImagesString, String.class);
            jsonObject.setPathImages(pathImagesList);
        }

        return jsonObject;
    }
}

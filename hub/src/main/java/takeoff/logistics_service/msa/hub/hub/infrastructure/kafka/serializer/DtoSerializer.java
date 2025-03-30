package takeoff.logistics_service.msa.hub.hub.infrastructure.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public class DtoSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing DTO", e);
        }
    }
}

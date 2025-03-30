package takeoff.logistics_service.msa.hub.hub.infrastructure.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public class DtoDeserializer<T> implements Deserializer<T> {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> targetType;

    public DtoDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, targetType);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing to DTO", e);
        }
    }
}

package takeoff.logisticsservice.msa.delivery.delivery.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryId is a Querydsl query type for DeliveryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeliveryId extends BeanPath<DeliveryId> {

    private static final long serialVersionUID = 1630180841L;

    public static final QDeliveryId deliveryId = new QDeliveryId("deliveryId");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public QDeliveryId(String variable) {
        super(DeliveryId.class, forVariable(variable));
    }

    public QDeliveryId(Path<? extends DeliveryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryId(PathMetadata metadata) {
        super(DeliveryId.class, metadata);
    }

}


package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryRouteId is a Querydsl query type for DeliveryRouteId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeliveryRouteId extends BeanPath<DeliveryRouteId> {

    private static final long serialVersionUID = -1266865323L;

    public static final QDeliveryRouteId deliveryRouteId = new QDeliveryRouteId("deliveryRouteId");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public QDeliveryRouteId(String variable) {
        super(DeliveryRouteId.class, forVariable(variable));
    }

    public QDeliveryRouteId(Path<? extends DeliveryRouteId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryRouteId(PathMetadata metadata) {
        super(DeliveryRouteId.class, metadata);
    }

}


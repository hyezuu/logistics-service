package takeoff.logistics_service.msa.order.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderId is a Querydsl query type for OrderId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrderId extends BeanPath<OrderId> {

    private static final long serialVersionUID = -1798885526L;

    public static final QOrderId orderId1 = new QOrderId("orderId1");

    public final ComparablePath<java.util.UUID> orderId = createComparable("orderId", java.util.UUID.class);

    public QOrderId(String variable) {
        super(OrderId.class, forVariable(variable));
    }

    public QOrderId(Path<? extends OrderId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderId(PathMetadata metadata) {
        super(OrderId.class, metadata);
    }

}


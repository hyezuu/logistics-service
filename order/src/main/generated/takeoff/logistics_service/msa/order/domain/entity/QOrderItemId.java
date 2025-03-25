package takeoff.logistics_service.msa.order.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderItemId is a Querydsl query type for OrderItemId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QOrderItemId extends BeanPath<OrderItemId> {

    private static final long serialVersionUID = 1987997277L;

    public static final QOrderItemId orderItemId1 = new QOrderItemId("orderItemId1");

    public final ComparablePath<java.util.UUID> orderItemId = createComparable("orderItemId", java.util.UUID.class);

    public QOrderItemId(String variable) {
        super(OrderItemId.class, forVariable(variable));
    }

    public QOrderItemId(Path<? extends OrderItemId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderItemId(PathMetadata metadata) {
        super(OrderItemId.class, metadata);
    }

}


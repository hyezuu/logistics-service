package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryRoute is a Querydsl query type for DeliveryRoute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryRoute extends EntityPathBase<DeliveryRoute> {

    private static final long serialVersionUID = 1437786266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryRoute deliveryRoute = new QDeliveryRoute("deliveryRoute");

    public final takeoff.logistics_service.msa.common.domain.QBaseEntity _super = new takeoff.logistics_service.msa.common.domain.QBaseEntity(this);

    public final QActualArrivalInfo actualArrivalInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> deletedBy = _super.deletedBy;

    public final ComparablePath<java.util.UUID> deliveryId = createComparable("deliveryId", java.util.UUID.class);

    public final NumberPath<Long> deliveryManagerId = createNumber("deliveryManagerId", Long.class);

    public final QEstimatedArrivalInfo estimatedArrivalInfo;

    public final ComparablePath<java.util.UUID> fromHubId = createComparable("fromHubId", java.util.UUID.class);

    public final QDeliveryRouteId id;

    public final NumberPath<Integer> sequenceNumber = createNumber("sequenceNumber", Integer.class);

    public final EnumPath<DeliveryRouteStatus> status = createEnum("status", DeliveryRouteStatus.class);

    public final ComparablePath<java.util.UUID> toHubId = createComparable("toHubId", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QDeliveryRoute(String variable) {
        this(DeliveryRoute.class, forVariable(variable), INITS);
    }

    public QDeliveryRoute(Path<? extends DeliveryRoute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryRoute(PathMetadata metadata, PathInits inits) {
        this(DeliveryRoute.class, metadata, inits);
    }

    public QDeliveryRoute(Class<? extends DeliveryRoute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actualArrivalInfo = inits.isInitialized("actualArrivalInfo") ? new QActualArrivalInfo(forProperty("actualArrivalInfo")) : null;
        this.estimatedArrivalInfo = inits.isInitialized("estimatedArrivalInfo") ? new QEstimatedArrivalInfo(forProperty("estimatedArrivalInfo")) : null;
        this.id = inits.isInitialized("id") ? new QDeliveryRouteId(forProperty("id")) : null;
    }

}


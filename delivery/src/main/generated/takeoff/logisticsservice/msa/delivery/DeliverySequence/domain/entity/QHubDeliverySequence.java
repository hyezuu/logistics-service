package takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHubDeliverySequence is a Querydsl query type for HubDeliverySequence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHubDeliverySequence extends EntityPathBase<HubDeliverySequence> {

    private static final long serialVersionUID = -607641295L;

    public static final QHubDeliverySequence hubDeliverySequence = new QHubDeliverySequence("hubDeliverySequence");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QHubDeliverySequence(String variable) {
        super(HubDeliverySequence.class, forVariable(variable));
    }

    public QHubDeliverySequence(Path<? extends HubDeliverySequence> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHubDeliverySequence(PathMetadata metadata) {
        super(HubDeliverySequence.class, metadata);
    }

}


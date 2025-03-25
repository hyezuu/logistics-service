package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEstimatedArrivalInfo is a Querydsl query type for EstimatedArrivalInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEstimatedArrivalInfo extends BeanPath<EstimatedArrivalInfo> {

    private static final long serialVersionUID = 1266164966L;

    public static final QEstimatedArrivalInfo estimatedArrivalInfo = new QEstimatedArrivalInfo("estimatedArrivalInfo");

    public final NumberPath<Integer> estimatedDistance = createNumber("estimatedDistance", Integer.class);

    public final NumberPath<Integer> estimatedDuration = createNumber("estimatedDuration", Integer.class);

    public QEstimatedArrivalInfo(String variable) {
        super(EstimatedArrivalInfo.class, forVariable(variable));
    }

    public QEstimatedArrivalInfo(Path<? extends EstimatedArrivalInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEstimatedArrivalInfo(PathMetadata metadata) {
        super(EstimatedArrivalInfo.class, metadata);
    }

}


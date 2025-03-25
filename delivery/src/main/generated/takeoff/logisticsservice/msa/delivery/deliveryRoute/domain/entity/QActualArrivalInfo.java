package takeoff.logisticsservice.msa.delivery.deliveryRoute.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActualArrivalInfo is a Querydsl query type for ActualArrivalInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActualArrivalInfo extends BeanPath<ActualArrivalInfo> {

    private static final long serialVersionUID = -545875650L;

    public static final QActualArrivalInfo actualArrivalInfo = new QActualArrivalInfo("actualArrivalInfo");

    public final NumberPath<Integer> actualDistance = createNumber("actualDistance", Integer.class);

    public final NumberPath<Integer> actualDuration = createNumber("actualDuration", Integer.class);

    public QActualArrivalInfo(String variable) {
        super(ActualArrivalInfo.class, forVariable(variable));
    }

    public QActualArrivalInfo(Path<? extends ActualArrivalInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActualArrivalInfo(PathMetadata metadata) {
        super(ActualArrivalInfo.class, metadata);
    }

}


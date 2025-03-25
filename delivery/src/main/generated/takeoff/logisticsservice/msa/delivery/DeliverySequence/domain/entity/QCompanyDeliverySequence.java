package takeoff.logisticsservice.msa.delivery.DeliverySequence.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanyDeliverySequence is a Querydsl query type for CompanyDeliverySequence
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyDeliverySequence extends EntityPathBase<CompanyDeliverySequence> {

    private static final long serialVersionUID = -1464910535L;

    public static final QCompanyDeliverySequence companyDeliverySequence = new QCompanyDeliverySequence("companyDeliverySequence");

    public final ComparablePath<java.util.UUID> hubId = createComparable("hubId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public QCompanyDeliverySequence(String variable) {
        super(CompanyDeliverySequence.class, forVariable(variable));
    }

    public QCompanyDeliverySequence(Path<? extends CompanyDeliverySequence> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyDeliverySequence(PathMetadata metadata) {
        super(CompanyDeliverySequence.class, metadata);
    }

}


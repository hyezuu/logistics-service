package takeoff.logistics_service.msa.user.infrastructure.persistence.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Page<DeliveryManager> findAllDeliveryManagers(Specification<DeliveryManager> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<DeliveryManager> query = cb.createQuery(DeliveryManager.class);
        Root<DeliveryManager> root = query.from(DeliveryManager.class);

        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        if (pageable.getSort().isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = pageable.getSort().stream()
                    .map(order -> order.isAscending() ?
                            cb.asc(root.get(order.getProperty())) :
                            cb.desc(root.get(order.getProperty())))
                    .toList();
            query.orderBy(orders);
        }

        List<DeliveryManager> content = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<DeliveryManager> countRoot = countQuery.from(DeliveryManager.class);
        countQuery.select(cb.count(countRoot)).where(spec.toPredicate(countRoot, countQuery, cb));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }
}

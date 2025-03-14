package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.hub.hub.model.entity.QHub.hub;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.hub.hub.infrastructure.persistence.JpaHubRepositoryCustom;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.model.entity.QHub;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.SearchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.SearchHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RequiredArgsConstructor
public class JpaHubRepositoryCustomImpl implements JpaHubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchHubResponseDto> searchHub(SearchHubRequestDto requestDto, Pageable pageable) {

        List<Hub> fetch = queryFactory.select(hub)
            .from(hub)
            .where(
                containsHubName(requestDto.hubName())
            )
            //Auditor 생성시 정렬생성
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long totalCount = queryFactory.select(hub.count())
            .from(hub)
            .where(
                containsHubName(requestDto.hubName())
            )
            .fetchOne();

        if (totalCount == null) totalCount = 0L;

        List<SearchHubResponseDto> responseDtoList = fetch.stream()
            .map(SearchHubResponseDto::from)
            .toList();


        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }

    private BooleanExpression containsHubName(String hubName) {
        return hubName == null ? null : hub.hubName.containsIgnoreCase(hubName);
    }

    //Auditor 구성시 정렬 추가
//    private List<OrderSpecifier<?>> dynamicOrder(Pageable pageable) {
//        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();
//
//        if (pageable.getSort() != null) {
//            for (Sort.Order sortOrder : pageable.getSort()) {
//                Order direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;
//
//                switch (sortOrder.getProperty()) {
//                    case "createdAt":
//                        orderSpecifierList.add(new OrderSpecifier<>(direction, hub.createdAt));
//                        break;
//                    case "updatedAt":
//                        orderSpecifierList.add(new OrderSpecifier<>(direction, hub.updatedAt));
//                        break;
//                    default:
//                        throw new IllegalArgumentException(
//                            "잘못된 정렬 필드입니다. : " + sortOrder.getProperty());
//                }
//            }
//        } else {
//            orderSpecifierList.add(new OrderSpecifier<>(Order.ASC, hub.createdAt));
//        }
//        return orderSpecifierList;
//
//    }
}

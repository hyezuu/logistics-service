package takeoff.logistics_service.msa.order.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.order.application.client.DeliveryClient;
import takeoff.logistics_service.msa.order.application.client.StockClient;
import takeoff.logistics_service.msa.order.application.client.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRoutesRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.PrePareStockRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.StockIdRequestDto;
import takeoff.logistics_service.msa.order.application.client.dto.request.StockItemRequestDto;
import takeoff.logistics_service.msa.order.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.order.application.dto.request.SearchOrderRequestDto;
import takeoff.logistics_service.msa.order.application.dto.response.SearchOrderResponseDto;
import takeoff.logistics_service.msa.order.domain.entity.ModifyQuantityCommand;
import takeoff.logistics_service.msa.order.domain.entity.Order;
import takeoff.logistics_service.msa.order.domain.entity.OrderId;
import takeoff.logistics_service.msa.order.domain.entity.OrderItem;
import takeoff.logistics_service.msa.order.domain.repository.OrderRepository;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequest;
import takeoff.logistics_service.msa.order.presentation.dto.response.PatchOrderResponse;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponse;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final DeliveryClient deliveryClient;
  private final StockClient stockClient;


  @Transactional
  public PostOrderResponse saveOrder(PostOrderRequest dto) {
    Order order = Order.builder().supplierId(dto.supplierId()).orderItems(dto.orderItems().stream()
            .map(orderItemDto -> OrderItem.builder().productId(orderItemDto.productId())
                .quantity(orderItemDto.quantity()).build()).toList()).customerId(dto.customerId())
        .address(dto.deliveryAddress()).requestNotes(dto.requestNotes()).build();

    UUID deliveryId = deliveryClient.saveDelivery(order.getId().getOrderId());
    order.modifyDeliveryId(deliveryId);
    // TODO : 비동기로직으로 수정

    // 배송 경로 추적
    deliveryClient.saveDeliveryRoute(new PostDeliveryRoutesRequestDto(
        deliveryId,
        null,
        null
    ));
    // TODO : company 로 부터 hubid 받아오는 로직 추가

    // 재고 관리
    List<StockItemRequestDto> stocks = dto.orderItems().stream().map(
            item -> new StockItemRequestDto(
                new StockIdRequestDto(item.productId(), parseHubId(item.productId())), item.quantity()))
        .toList();

    PrePareStockRequestDto prePareStockRequestDto = new PrePareStockRequestDto(stocks);
    stockClient.prepareStock(prePareStockRequestDto);

    orderRepository.save(order);
    return PostOrderResponse.from(order);
  }

  @Transactional
  public PatchOrderResponse updateOrder(PatchOrderRequest dto, UUID orderId) {
    Order order = orderRepository.findById(OrderId.from(orderId))
        .orElseThrow(() -> new IllegalArgumentException(("주문을 찾을 수 없습니다.")));
    // TODO : 커스텀 예외로 변경

    List<ModifyQuantityCommand> commands = dto.orderItems().stream().map(
        orderItemDto -> ModifyQuantityCommand.from(orderItemDto.productId(),
            orderItemDto.quantity())).toList();

    order.modifyAllQuantityByProduct(commands);

    return PatchOrderResponse.from(order);
  }

  @Transactional
  public void deleteOrder(UUID orderId) {
    Order order = orderRepository.findById(OrderId.from(orderId))
        .orElseThrow(() -> new IllegalArgumentException(("주문을 찾을 수 없습니다.")));

    // 재고 관리
    List<StockItemRequestDto> stocks = order.getOrderItems().stream().map(
        item -> new StockItemRequestDto(
            new StockIdRequestDto(item.getProductId(), parseHubId(item.getProductId())),
            item.getQuantity())).toList();

    AbortStockRequestDto abortStockRequestDto = new AbortStockRequestDto(stocks);
    stockClient.abortStock(abortStockRequestDto);

    order.delete(1L); // TODO: 사용자 ID를 받아와야 함
  }

  public PaginatedResultDto<SearchOrderResponseDto> searchOrder(SearchOrderRequestDto dto) {
    return PaginatedResultDto.from(orderRepository.findAllBySearchParams(dto.toSearchCriteria()));
  }

  private UUID parseHubId(UUID pid) {
    return stockClient.getStock(pid).hubId();
  }
}

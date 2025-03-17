package takeoff.logistics_service.msa.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.order.application.client.DeliveryClient;
import takeoff.logistics_service.msa.order.model.entity.Order;
import takeoff.logistics_service.msa.order.model.entity.OrderItem;
import takeoff.logistics_service.msa.order.model.repository.OrderRepository;
import takeoff.logistics_service.msa.order.presentation.dto.request.OrderSaveRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.OrderSaveResponseDto;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final DeliveryClient deliveryClient;


  @Transactional
  public OrderSaveResponseDto saveOrder(OrderSaveRequestDto dto) {
    Order order = Order.builder()
        .supplierId(dto.supplierId())
        .orderItems(
            dto.orderItems().stream()
                .map(orderItemDto -> OrderItem.builder()
                    .productId(orderItemDto.productId())
                    .quantity(orderItemDto.quantity())
                    .build())
                .toList()
        )
        .deliveryId(deliveryClient.saveDelivery())
        .customerId(dto.customerId())
        .address(dto.deliveryAddress())
        .requestNotes(dto.requestNotes())
        .build();

    orderRepository.save(order);
  }
}

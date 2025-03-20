package takeoff.logistics_service.msa.order.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.order.application.client.DeliveryClient;
import takeoff.logistics_service.msa.order.model.entity.ModifyQuantityCommand;
import takeoff.logistics_service.msa.order.model.entity.Order;
import takeoff.logistics_service.msa.order.model.entity.OrderItem;
import takeoff.logistics_service.msa.order.model.repository.OrderRepository;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.PatchOrderResponseDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponseDto;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final DeliveryClient deliveryClient;


  @Transactional
  public PostOrderResponseDto saveOrder(PostOrderRequestDto dto) {
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
    return PostOrderResponseDto.from(order);
  }

  @Transactional
  public PatchOrderResponseDto updateOrder(PatchOrderRequestDto dto, UUID orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException(("주문을 찾을 수 없습니다.")));
    // TODO : 커스텀 예외로 변경

    List<ModifyQuantityCommand> commands = dto.orderItems().stream()
        .map(orderItemDto -> ModifyQuantityCommand.from(
            orderItemDto.productId(),
            orderItemDto.quantity()))
        .toList();

    order.modifyAllQuantityByProduct(commands);

    return PatchOrderResponseDto.from(order);
  }

  @Transactional
  public void deleteOrder(UUID orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException(("주문을 찾을 수 없습니다.")));

    order.delete(1L); // TODO: 사용자 ID를 받아와야 함
  }
}

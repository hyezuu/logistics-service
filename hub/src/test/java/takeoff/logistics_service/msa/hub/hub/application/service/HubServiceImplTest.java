package takeoff.logistics_service.msa.hub.hub.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.hub.hub.application.dto.LocationDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PatchHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.GetHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PatchHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.dto.response.PostHubResponseDto;
import takeoff.logistics_service.msa.hub.hub.application.exception.HubErrorCode;
import takeoff.logistics_service.msa.hub.hub.domain.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.domain.repository.HubRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@DisplayName("HubRoute Service Test")
@ExtendWith(MockitoExtension.class)
class HubServiceImplTest {

    @Mock
    private HubRepository hubRepository;

    @InjectMocks
    private HubServiceImpl hubserviceImpl;

    @DisplayName("허브 생성 테스트")
    @Test
    void create_Hub() {
        // given
        LocationDto locationDto = new LocationDto("testLocation", 135.1234, 70.1234);
        PostHubRequestDto requestDto = new PostHubRequestDto("test", locationDto);

        Hub hub = requestDto.toEntity();
        PostHubResponseDto expectedResponse = PostHubResponseDto.from(hub);

        BDDMockito.given(hubRepository.save(any(Hub.class)))
            .willAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        PostHubResponseDto postHubResponseDto = hubserviceImpl.saveHub(requestDto);

        // then
        assertThat(postHubResponseDto)
            .usingRecursiveComparison()  // 객체 비교에 사용
            .isEqualTo(expectedResponse);

        verify(hubRepository, times(1)).save(any(Hub.class));
    }


    @DisplayName("허브 수정 테스트")
    @Test
    void update_Hub() {
        // given
        UUID hubId = UUID.randomUUID();
        LocationDto locationDto = new LocationDto("testLocation", 135.1234, 70.1234);
        PostHubRequestDto requestDto = new PostHubRequestDto("test", locationDto);

        Hub hub = Hub.builder()
            .id(hubId)
            .hubName("test")
            .location(locationDto.toVo())
            .build();

        PatchHubRequestDto patchRequestDto = new PatchHubRequestDto("updatedTest");


        BDDMockito.given(hubRepository.findByIdAndDeletedAtIsNull(hubId)).willReturn(Optional.of(hub));
        BDDMockito.given(hubRepository.save(any(Hub.class)))
            .willAnswer(AdditionalAnswers.returnsFirstArg());

        hub.modifyHubName(patchRequestDto.hubName());

        hubRepository.save(hub);


        PatchHubResponseDto expectedResponse = PatchHubResponseDto.from(hub);

        // when
        PatchHubResponseDto actualResponse = hubserviceImpl.updateHub(hubId, patchRequestDto);

        // then
        assertThat(actualResponse)
            .usingRecursiveComparison()
            .isEqualTo(expectedResponse);

        verify(hubRepository, times(1)).findByIdAndDeletedAtIsNull(hubId);
        verify(hubRepository, times(1)).save(any(Hub.class));
    }


    @DisplayName("허브 조회 테스트")
    @Test
    void findByHubId_Hub() {
        // given
        UUID hubId = UUID.randomUUID();
        LocationDto locationDto = new LocationDto("testLocation", 135.1234, 70.1234);
        PostHubRequestDto requestDto = new PostHubRequestDto("test", locationDto);

        Hub hub = Hub.builder()
            .id(hubId)
            .hubName("test")
            .location(locationDto.toVo())
            .build();


        GetHubResponseDto expectedResponse = GetHubResponseDto.from(hub);


        BDDMockito.given(hubRepository.findByIdAndDeletedAtIsNull(hubId)).willReturn(Optional.of(hub));

        // when
        GetHubResponseDto actualResponse = hubserviceImpl.findByHubId(hubId);

        // then
        assertThat(actualResponse)
            .usingRecursiveComparison()
            .isEqualTo(expectedResponse);

        verify(hubRepository, times(1)).findByIdAndDeletedAtIsNull(hubId);
    }
    @DisplayName("허브 수정 실패 테스트")
    @Test
    void update_Hub_Fail() {
        // given
        UUID hubId = UUID.randomUUID();
        PatchHubRequestDto patchRequestDto = new PatchHubRequestDto("updateTest");

        // 허브가 존재하지 않을 경우 예외가 발생해야 함
        BDDMockito.given(hubRepository.findByIdAndDeletedAtIsNull(hubId))
            .willReturn(Optional.empty());

        // when //then
        //HubBusinessException은 상속이라 BusinessException으로 검증
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            hubserviceImpl.updateHub(hubId, patchRequestDto);
        });


        assertThat(exception.getErrorCode()).isEqualTo(HubErrorCode.HUB_NOT_FOUND);


        verify(hubRepository, times(1)).findByIdAndDeletedAtIsNull(hubId);
    }

}

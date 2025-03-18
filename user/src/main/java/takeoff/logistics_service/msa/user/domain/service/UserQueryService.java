package takeoff.logistics_service.msa.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import takeoff.logistics_service.msa.user.domain.entity.User;
import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.repository.UserSpecifications;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;
    public Page<User> searchUsers(UserSearchCondition condition, Pageable pageable) {
        return userRepository.findAll(UserSpecifications.toSpecification(condition), pageable);
    }
}

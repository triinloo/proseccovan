package proseccovan.backend.persistence.user;

import org.mapstruct.*;
import proseccovan.backend.controller.login.dto.LoginResponseDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "role")
    LoginResponseDto toLoginResponseDto(User user);


}
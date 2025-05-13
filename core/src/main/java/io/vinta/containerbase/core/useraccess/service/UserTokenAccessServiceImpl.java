package io.vinta.containerbase.core.useraccess.service;

import io.vinta.containerbase.common.accesstoken.UserTokenGenerator;
import io.vinta.containerbase.common.enums.UserStatus;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.exceptions.NotFoundException;
import io.vinta.containerbase.common.mapstruct.MapstructCommonDomainMapper;
import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import io.vinta.containerbase.common.security.domains.JwtTokenType;
import io.vinta.containerbase.core.useraccess.UserAccessRepository;
import io.vinta.containerbase.core.useraccess.UserTokenAccessService;
import io.vinta.containerbase.core.useraccess.entities.UserAccessBasicAuthData;
import io.vinta.containerbase.core.useraccess.entities.UserAccessTokenPair;
import io.vinta.containerbase.core.useraccess.request.LoginCommand;
import io.vinta.containerbase.core.useraccess.request.RefreshTokenCommand;
import io.vinta.containerbase.core.users.UserQueryService;
import io.vinta.containerbase.core.users.request.FilterUserQuery;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenAccessServiceImpl implements UserTokenAccessService {
	private final UserTokenGenerator userTokenGenerator;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserAccessRepository repository;

	private final UserQueryService userQueryService;

	@Override
	public UserAccessTokenPair login(LoginCommand loginCommand) {
		final var user = userQueryService.findSingleUser(FilterUserQuery.builder()
				.byEmail(loginCommand.getEmail())
				.byUserStatuses(Set.of(UserStatus.ACTIVE))
				.build())
				.orElseThrow(() -> new BadRequestException("Invalid Login Credentials"));

		final var userAccess = repository.findUserAccess(loginCommand.getAccessType(), user.getId())
				.orElseThrow(() -> new BadRequestException("Invalid Login Credentials"));

		final var userAccessData = (UserAccessBasicAuthData) userAccess.getAccessData();

		if (!passwordEncoder.matches(loginCommand.getPassword(), userAccessData.getEncodedPassword())) {
			throw new BadRequestException("Invalid Login Credentials");
		}

		return UserAccessTokenPair.builder()
				.accessToken(userTokenGenerator.generateToken(JwtTokenClaim.builder()
						.userId(MapstructCommonDomainMapper.INSTANCE.userIdToString(user.getId()))
						.userType(user.getUserType())
						.type(JwtTokenType.ACCESS_TOKEN)
						.build()))
				.refreshToken(userTokenGenerator.generateToken(JwtTokenClaim.builder()
						.userId(MapstructCommonDomainMapper.INSTANCE.userIdToString(user.getId()))
						.userType(user.getUserType())
						.type(JwtTokenType.REFRESH_TOKEN)
						.build()))
				.build();
	}

	@Override
	public UserAccessTokenPair refreshToken(RefreshTokenCommand command) {
		final var user = userQueryService.findSingleUser(FilterUserQuery.builder()
				.byUserId(command.getUserId())
				.byUserStatuses(Set.of(UserStatus.ACTIVE))
				.build())
				.orElseThrow(() -> new NotFoundException("User Not Found"));
		return UserAccessTokenPair.builder()
				.accessToken(userTokenGenerator.generateToken(JwtTokenClaim.builder()
						.userId(MapstructCommonDomainMapper.INSTANCE.userIdToString(user.getId()))
						.userType(user.getUserType())
						.type(JwtTokenType.ACCESS_TOKEN)
						.build()))
				.refreshToken(userTokenGenerator.generateToken(JwtTokenClaim.builder()
						.userId(MapstructCommonDomainMapper.INSTANCE.userIdToString(user.getId()))
						.userType(user.getUserType())
						.type(JwtTokenType.REFRESH_TOKEN)
						.build()))
				.build();

	}
}

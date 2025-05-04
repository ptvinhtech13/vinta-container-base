/******************************************************************************
 *  (C) Copyright 2008 STYL Solutions Pte. Ltd. , All rights reserved          *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of STYL Solutions Pte. Ltd. and is provided pursuant to a         *
 *  Software License Agreement.  This code is the proprietary information of   *
 *  STYL Solutions Pte. Ltd. and is confidential in nature. Its use and        *
 *  dissemination by any party other than STYL Solutions Pte. Ltd. is strictly *
 *  limited by the confidential information provisions of the Agreement        *
 *  referenced above.                                                          *
 ******************************************************************************/
package io.vinta.containerbase.rest.access;

import io.vinta.containerbase.common.security.context.AppSecurityContextHolder;
import io.vinta.containerbase.core.useraccess.UserTokenAccessService;
import io.vinta.containerbase.core.useraccess.request.LoginCommand;
import io.vinta.containerbase.core.useraccess.request.RefreshTokenCommand;
import io.vinta.containerbase.rest.access.mapper.UserAccessResponseMapper;
import io.vinta.containerbase.rest.access.request.LoginUserRequest;
import io.vinta.containerbase.rest.access.response.UserAccessResponse;
import io.vinta.containerbase.rest.api.UserAccessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccessController implements UserAccessApi {
	private final UserTokenAccessService userTokenAccessService;

	@Override
	public UserAccessResponse login(LoginUserRequest request) {
		return UserAccessResponseMapper.INSTANCE.toResponse(userTokenAccessService.login(LoginCommand.builder()
				.accessType(request.getAccessType())
				.email(request.getEmail())
				.password(request.getPassword())
				.build()));
	}

	@Override
	public UserAccessResponse refreshToken() {
		return UserAccessResponseMapper.INSTANCE.toResponse(userTokenAccessService.refreshToken(RefreshTokenCommand
				.builder()
				.userId(AppSecurityContextHolder.getUserId())
				.build()));
	}
}

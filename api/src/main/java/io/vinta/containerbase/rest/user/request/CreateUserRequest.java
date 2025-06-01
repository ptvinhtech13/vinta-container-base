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
package io.vinta.containerbase.rest.user.request;

import io.vinta.containerbase.common.enums.UserType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateUserRequest {

	@NotNull
	private UserType userType;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@Length(max = 120)
	private String fullName;

	@Length(max = 200)
	private String avatarPath;

	@Length(max = 20)
	private String phoneNumber;

	@NotNull
	private String roleId;

	@NotNull
	@Valid
	private CreateUserAccessRequest userAccess;

}

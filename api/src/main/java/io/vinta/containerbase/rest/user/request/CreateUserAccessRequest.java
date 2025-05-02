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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.vinta.containerbase.common.enums.UserAccessType;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = CreateUserAccessRequest.TYPE_FIELD_NAME, visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = CreateUserAccessBasicAuthRequest.class, name = "BASIC_AUTH_PASSWORD"), })
@Getter
public class CreateUserAccessRequest {
	public static final String TYPE_FIELD_NAME = "accessType";
	private final UserAccessType accessType;

	protected CreateUserAccessRequest(UserAccessType accessType) {
		this.accessType = accessType;
	}
}

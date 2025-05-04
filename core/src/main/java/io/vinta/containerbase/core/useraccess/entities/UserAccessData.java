package io.vinta.containerbase.core.useraccess.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.vinta.containerbase.common.enums.UserAccessType;
import lombok.Getter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = UserAccessData.TYPE_FIELD_NAME, visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = UserAccessBasicAuthData.class, name = "BASIC_AUTH"), })
@Getter
public class UserAccessData {
	public static final String TYPE_FIELD_NAME = "accessType";
	private final UserAccessType accessType;

	protected UserAccessData(UserAccessType accessType) {
		this.accessType = accessType;
	}
}

package io.vinta.containerbase.core.useraccess.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.vinta.containerbase.common.enums.UserAccessType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("BASIC_AUTH")
@JsonIgnoreProperties(ignoreUnknown = true)
@With
public class UserAccessBasicAuthData extends UserAccessData {
	private final String password;
	private final String encodedPassword;

	@Builder
	@JsonCreator
	public UserAccessBasicAuthData(@JsonProperty("password") String password,
			@JsonProperty("encodedPassword") String encodedPassword) {
		super(UserAccessType.BASIC_AUTH);
		this.password = password;
		this.encodedPassword = encodedPassword;
	}
}
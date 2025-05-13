package io.vinta.containerbase.common.security.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vinta.containerbase.common.enums.UserType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtTokenClaim implements Serializable {

	private String userId;
	private UserType userType;
	private JwtTokenType type;
}

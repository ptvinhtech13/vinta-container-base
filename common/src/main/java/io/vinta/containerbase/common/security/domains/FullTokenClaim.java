package io.vinta.containerbase.common.security.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FullTokenClaim {
	private String iss;
	private JwtTokenClaim tokenClaim;
	private Long exp;
	private Long iat;
	private String jti;
}

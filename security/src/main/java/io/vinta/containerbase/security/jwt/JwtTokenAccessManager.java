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
package io.vinta.containerbase.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.common.exceptions.BadRequestException;
import io.vinta.containerbase.common.exceptions.config.ErrorCodeConfig;
import io.vinta.containerbase.common.exceptions.constants.CommonErrorConstants;
import io.vinta.containerbase.common.idgenerator.SnowflakeIdGenerator;
import io.vinta.containerbase.common.security.constants.SecurityConstants;
import io.vinta.containerbase.common.security.domains.FullTokenClaim;
import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import io.vinta.containerbase.common.security.domains.JwtTokenType;
import io.vinta.containerbase.common.utils.ECDSAUtils;
import io.vinta.containerbase.common.utils.MapUtils;
import io.vinta.containerbase.security.config.JwtTokenConfigProperties;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenAccessManager {

	private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

	private final ObjectMapper objectMapper;
	private final ErrorCodeConfig errorCodeConfig;
	private final Algorithm algorithm;
	private final JWTVerifier jwtVerifier;
	private final JwtTokenConfigProperties jwtTokenConfigProperties;
	private final Map<JwtTokenType, JwtTokenConfigProperties.TokenConfig> tokenConfigMap;

	@Autowired
	public JwtTokenAccessManager(ObjectMapper objectMapper, ErrorCodeConfig errorCodeConfig,
			JwtTokenConfigProperties jwtTokenConfigProperties) {
		this.objectMapper = objectMapper;
		this.errorCodeConfig = errorCodeConfig;
		this.jwtTokenConfigProperties = jwtTokenConfigProperties;

		this.algorithm = Algorithm.ECDSA512(ECDSAUtils.getPublicKey(jwtTokenConfigProperties.getPublicKey()
				.getBytes(StandardCharsets.UTF_8)), ECDSAUtils.getPrivateKey(jwtTokenConfigProperties.getPrivateKey()
						.getBytes(StandardCharsets.UTF_8)));

		this.tokenConfigMap = jwtTokenConfigProperties.getTokenConfig()
				.stream()
				.collect(Collectors.toMap(JwtTokenConfigProperties.TokenConfig::getTokenType, Function.identity()));

		this.jwtVerifier = JWT.require(algorithm)
				.withIssuer(jwtTokenConfigProperties.getIssuer())
				.build();

	}

	public FullTokenClaim verifyAndDecodeToken(final String token) {
		final var tokenWithoutBearer = token.replace(SecurityConstants.BEARER, "")
				.trim();
		final var jwt = jwtVerifier.verify(tokenWithoutBearer);
		final var tokenClaim = new String(DECODER.decode(jwt.getPayload()), StandardCharsets.UTF_8);
		try {
			return objectMapper.readValue(tokenClaim, FullTokenClaim.class);
		} catch (final JsonProcessingException ex) {
			log.error("Parsing token has failed", ex);
			throw new BadRequestException(errorCodeConfig.getMessage(CommonErrorConstants.PARSING_TOKEN_FAILED), ex);
		}
	}

	public String generateToken(final JwtTokenClaim tokenClaim) {
		final var insNow = Instant.now();
		final var now = new Date(insNow.toEpochMilli());
		final var livedTimeInMillis = Optional.ofNullable(tokenConfigMap.get(tokenClaim.getType()))
				.orElseThrow(() -> new UnsupportedOperationException(String.format("Unsupported the TokenType %s",
						tokenClaim.getType())))
				.getTimeToLive()
				.toMillis();
		final var expiredAt = new Date(insNow.plus(livedTimeInMillis, ChronoUnit.MILLIS)
				.toEpochMilli());

		final Map<String, Object> userClaims = MapUtils.stripNullValue(objectMapper.convertValue(tokenClaim,
				Map.class));
		final var claims = Map.of("tokenClaim", userClaims);
		return JWT.create()
				.withJWTId(createJTI())
				.withPayload(claims)
				.withIssuedAt(now)
				.withIssuer(jwtTokenConfigProperties.getIssuer())
				.withExpiresAt(expiredAt)
				.sign(algorithm);
	}

	private String createJTI() {
		return SnowflakeIdGenerator.randomId()
				.toString();
	}

}

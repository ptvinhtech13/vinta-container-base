package io.vinta.containerbase.common.exceptions.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonErrorConstants {
	public static final long DEFAULT_ERROR = 1001000L;

	//----------------------successful----------------------
	public static final long SUCCESS = 1002000L;

	//----------------------successful----------------------
	public static final long REDIRECTION = 1003000L;

	//----------------------client error----------------------
	public static final long BAD_REQUEST = 1004000L;
	public static final long ACCESS_FORBIDDEN = 1004002L;
	public static final long AUTHORIZATION_MUST = 1004003L;
	public static final long PARSING_TOKEN_FAILED = 1004004L;
	public static final long TOKEN_INVALID = 1004005L;
	public static final long TOKEN_IS_MISSING = 1004006L;
	public static final long TOKEN_EXPIRED = 1004007L;
	public static final long NOT_FOUND_KMS_CRYPTO_KEY_INFO = 1004008L;
	public static final long NOT_FOUND_SECRET_VERSION_EMPTY = 1004009L;
	public static final long TOKEN_FROM_VERSION_INVALID = 10004012L;
	public static final long CACHE_ID_NOT_NULL = 10004013L;
	public static final long PROCESS_IS_LOCKED = 10004014L;
	public static final long TOO_MANY_REQUESTS = 10004015L;

	//----------------------server error----------------------
	public static final long INTERNAL_SERVER_ERROR = 10005000L;
}

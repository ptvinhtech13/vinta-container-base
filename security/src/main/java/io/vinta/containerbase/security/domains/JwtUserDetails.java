package io.vinta.containerbase.security.domains;

import io.vinta.containerbase.common.security.domains.JwtTokenClaim;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private final JwtTokenClaim claim;

	private final List<GrantedAuthority> authorities;

	public JwtUserDetails(final JwtTokenClaim claim) {
		this.authorities = AuthorityUtils.createAuthorityList(claim.getUserType()
				.name());
		this.claim = claim;
	}

	public JwtTokenClaim getTokenClaim() {
		return claim;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection(authorities);
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return claim.getUserId()
				.toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

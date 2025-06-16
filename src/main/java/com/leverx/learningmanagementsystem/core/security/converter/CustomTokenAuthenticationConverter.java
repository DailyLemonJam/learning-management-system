package com.leverx.learningmanagementsystem.core.security.converter;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Profile("cloud")
public class CustomTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLE_PREFIX_TEMPLATE = "ROLE_%s";

    private final TokenAuthenticationConverter tokenAuthenticationConverter;

    public CustomTokenAuthenticationConverter(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        tokenAuthenticationConverter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        var token = tokenAuthenticationConverter.convert(source);
        var authorities = new ArrayList<GrantedAuthority>();

        fillInRoles(token, source, authorities);

        return new JwtAuthenticationToken(source, authorities);
    }

    private void fillInRoles(AbstractAuthenticationToken token, Jwt source, List<GrantedAuthority> target) {
        if (nonNull(token)) {
            target.addAll(token.getAuthorities());

            var roles = getRoles(source);
            target.addAll(roles);
        }
    }

    private List<GrantedAuthority> getRoles(Jwt jwt) {
        var roles = new ArrayList<GrantedAuthority>();
        var roleCollections = jwt.getClaimAsStringList("xs.system.attributes.xs.rolecollections");

        if (nonNull(roleCollections)) {
            var roleList = roleCollections.stream()
                    .map(ROLE_PREFIX_TEMPLATE::formatted)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            roles.addAll(roleList);
        }

        return roles;
    }
}

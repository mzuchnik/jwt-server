package pl.mzuchnik.server.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.mzuchnik.server.jwt.secret.SecretUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;

public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            authenticationToken = getUsernamePasswordAuthenticationToken(authorization);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorization) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {

        File publicKeyFilePem = new File("src/main/resources/keys/public_key.pem");
        RSAPublicKey publicKey = SecretUtil.getPublicKey(publicKeyFilePem);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(
                publicKey,null
        )).build();
        String token = authorization.substring(7);
        DecodedJWT verify = jwtVerifier.verify(token);
        String name = verify.getClaim("name").asString();
        String roleToken = verify.getClaim("role").asString();
        String role = "ROLE_USER";
        if(roleToken.equalsIgnoreCase("user")){
            role = "ROLE_USER";
        }else if(roleToken.equalsIgnoreCase("admin")){
            role = "ROLE_ADMIN";
        }
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        return new UsernamePasswordAuthenticationToken(name, null, Collections.singleton(simpleGrantedAuthority));
    }
}

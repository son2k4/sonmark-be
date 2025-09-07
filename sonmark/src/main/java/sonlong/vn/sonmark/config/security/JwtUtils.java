package sonlong.vn.sonmark.config.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sonlong.vn.sonmark.services.UserDetailsImpl;
import sonlong.vn.sonmark.services.UserTokenService;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private UserTokenService userTokenService ;


   @Value("${son.mark.jwtSecret}")
   private String jwtSecret;

    @Value("${son.mark.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return refreshToken(userPrincipal.getUsername());
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try{
            String username = getUserNameFromJwtToken(authToken) ;
            String latestToken = userTokenService.getCurrentToken(username) ;
            if(!authToken.equals(latestToken)){
                return false ;
            }
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false ;
    }


    public String refreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenWithScope(String username, String scope) {
        return Jwts.builder()
                .setSubject(username)
                .claim("scope", scope)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}



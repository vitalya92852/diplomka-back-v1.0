package com.example.demo1222.jwt;

import com.example.demo1222.repositories.UserRepository;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtCore {
    @Value("${auth.app.secret}")
    private String secret;

    @Value("${auth.app.expiration}")
    private Duration lifeTime;

    private final UserRepository userRepository;

    public JwtCore(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String generateToken(UserDetails userDetails){ // генерация jwt токена
        Map<String,Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream() // Собираем список ролей пользователя в лист
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles",rolesList);
        claims.put("id",userRepository.findByUsername(userDetails.getUsername()).orElseThrow(null).getId());

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime()+lifeTime.toMillis());
        return Jwts.builder()
                .setClaims(claims) // передаваемые значения почта,роли и т д
                .setSubject(userDetails.getUsername()) // передаем логин
                .setIssuedAt(issuedDate) // дата выдачи токена
                .setExpiration(expiredDate) // когда истекает
                .signWith(SignatureAlgorithm.HS256,secret) // секретный ключ
                .compact(); // собрать
    }

    public String getUsername(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles", List.class);
    }


    public Long getUserId(String token){
        return getAllClaimsFromToken(token).get("id", Long.class);
    }

    public boolean validateToken(String token){
        Date expirationDate = getAllClaimsFromToken(token).getExpiration();
        Date now = new Date();

        return !expirationDate.before(now);
    }


    private Claims getAllClaimsFromToken(String token){ // расшифрование токена и получения с него данных
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)// проверяет все,верность токена,срок жизни т д
                .getBody();
    }


}

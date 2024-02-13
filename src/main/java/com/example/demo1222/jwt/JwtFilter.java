package com.example.demo1222.jwt;

import com.example.demo1222.Entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j                                                // Логика фильтров спринг
public class JwtFilter extends OncePerRequestFilter { // Пользователь отправляет данные -> проходит фильтр -> попадает в context ->
    private final JwtCore jwtCore;                    // -> попадает по ссылке
    @Override // фильтр в который пользователь отправил данные для получения доступа к защищенным ссылкам
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){ // есть ли в head в запросе токен
            jwt = authHeader.substring(7);
            try {
                username = jwtCore.getUsername(jwt);
            } catch (ExpiredJwtException e){
                log.debug("Время жизни токена вышло");
            } catch (SignatureException e){
                log.debug("Подпись не верна");
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){ // Собираем пользователя с помощью - JWT токена
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                null,
                jwtCore.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token); // положили пользователя
        }

        filterChain.doFilter(request,response);
    }
}

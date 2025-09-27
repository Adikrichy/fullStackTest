package org.aldoustv.first_lab.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if( path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/webjars") ||
                path.equals("/api/users/login") ||
                path.startsWith("/api/users/registry")){
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("JwtAuthenticationFilter: path = " + request.getRequestURI());
        String jwt = null;
        if(request.getCookies() != null){
            System.out.println("JwtAuthenticationFilter: cookies" + Arrays.toString(request.getCookies()));;
            for(Cookie cookie: request.getCookies()){
                if("jwt".equals(cookie.getName())){
                    jwt = cookie.getValue();
                    break;
                }

                else{
                    System.out.println("No jwt cookie found");
                }

                System.out.println("JwtAuthenticationFilter: jwt" + jwt);
            }
        }

        if(jwt != null){
            try{
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtService.getSecret().getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String email = claims.getSubject();
                Long userId = claims.get("userId", Long.class);
                List<GrantedAuthority> authorities = Collections.emptyList();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email,null,authorities);

                auth.setDetails(userId);

                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Authentication user = " + email + " with id = " + userId);

            }
            catch(Exception e){
                System.out.println("JwtAuthenticationFilter: exception while parsing JWT");
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request,response);

    }
}
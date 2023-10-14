package cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.jwt.authentication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import cat.itacademy.barcelonactiva.morandini.darko.s05.t02.n02.jwt.service.JwtService;


public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String token = extractTokenFromHeader(req);
        
        if (token != null && jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);     
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }

    private String extractTokenFromHeader(ServletRequest req) {

        String header = ((HttpServletRequest) req).getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null; 
    }
}


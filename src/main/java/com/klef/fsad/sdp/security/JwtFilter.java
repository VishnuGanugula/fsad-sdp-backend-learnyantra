package com.klef.fsad.sdp.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.klef.fsad.sdp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter 
{
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException 
    {
        String path = request.getServletPath();
        System.out.println("Request Path=" + path + " Method=" + request.getMethod());

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Public endpoints (no authentication required)
        List<String> publicPaths = List.of(
                "/auth",
                "/swagger-ui",
                "/v3/api-docs",
                "/swagger-ui.html"
        );

        boolean isPublic = publicPaths.stream()
                .anyMatch(path::startsWith);

        if (isPublic) 
        {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        System.out.println("Token Header="+header);

        if (header == null || !header.startsWith("Bearer ")) 
        {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();

        try 
        {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
            {
                UserDetails userDetails = service.loadUserByUsername(username);

                if (userDetails != null && jwtUtil.validateToken(token, userDetails)) 
                {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } 
            }
        } 
        catch (Exception e) 
        {
            // Optional: send error response if token is invalid but present
            // sendErrorResponse(response, 401, "Invalid token: " + e.getMessage());
            // return;
        }

        chain.doFilter(request, response);
    }
}

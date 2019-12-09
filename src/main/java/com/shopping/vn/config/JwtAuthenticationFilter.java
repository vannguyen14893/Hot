package com.shopping.vn.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.UserService;
import com.shopping.vn.utils.SecurityConstants;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  UserService userService;

  String permission = "";
  private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String origin = httpServletRequest.getHeader("Origin");
    //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "*");
    try {
      String jwt = getJWTFromRequest(httpServletRequest);
      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        Long userId = tokenProvider.getUserIdFromJWT(jwt);
        User userDetails = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeExceptionHandling("user not found " + userId));

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails.getEmail(),
                userDetails.getPassword(), new ArrayList<>());

        authentication
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      // Object principal =
      // SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      // if (!userService.checkPermission(principal.toString(), permission)) {
      // httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      // return;
      // }
    } catch (Exception ex) {
      log.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
    permission = request.getHeader("Permission");
    if (StringUtils.hasText(bearerToken)
        && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      return bearerToken.substring(7, bearerToken.length());
    }

    return null;
  }
}

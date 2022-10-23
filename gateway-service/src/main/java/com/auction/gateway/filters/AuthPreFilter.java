package com.auction.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.stereotype.Component;

@Component
public class AuthPreFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 100 - 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Autowired
  RemoteTokenServices remoteTokenServices;

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String requestHeader = request.getHeader("Authorization");
    if (requestHeader != null && requestHeader.contains("Bearer")) {
      OAuth2Authentication authorization = remoteTokenServices.loadAuthentication(
          requestHeader.replace("Bearer ", ""));
      ctx.addZuulRequestHeader("username", authorization.getUserAuthentication().getName());
      ctx.addZuulRequestHeader("role",
          authorization.getAuthorities().stream().findFirst().get().getAuthority());
    }
    return null;
  }
}
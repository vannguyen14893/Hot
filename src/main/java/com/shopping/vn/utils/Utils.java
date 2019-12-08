package com.shopping.vn.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
  public static final Date formatDate(String date) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date dateStr = null;
    try {
      dateStr = formatter.parse(date);

    } catch (ParseException e) {
      log.error("error date" + e);
    }

    return dateStr;
  }

  private Utils() {

  }

  public static final String generateCollection(List<?> list) {
    if (list == null || list.isEmpty())
      return "()";
    StringBuilder result = new StringBuilder();
    result.append("(");
    for (Iterator<?> it = list.iterator(); it.hasNext();) {
      Object ob = it.next();
      result.append(ob.toString());
      if (it.hasNext())
        result.append(" , ");
    }
    result.append(")");
    return result.toString();
  }

  public static final String getPrincipal() {
    Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    return email.toString();
  }
}

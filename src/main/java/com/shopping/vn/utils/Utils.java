package com.shopping.vn.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    String result = "( ";
    for (Iterator<?> it = list.iterator(); it.hasNext();) {
      Object ob = it.next();
      result += ob.toString();
      if (it.hasNext())
        result += " , ";
    }
    result += " )";
    return result;
  }
}

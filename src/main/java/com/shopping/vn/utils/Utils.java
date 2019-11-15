package com.shopping.vn.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}

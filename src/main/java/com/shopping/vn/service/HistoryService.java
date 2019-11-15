package com.shopping.vn.service;

import com.shopping.vn.dto.HistoryDto;
import com.shopping.vn.entity.History;
import com.shopping.vn.entity.User;

public interface HistoryService {
  History historyAdd(HistoryDto history, User user);

  History historyUpdate(HistoryDto history, User user);

  History historyDelete(HistoryDto history, User user);
}

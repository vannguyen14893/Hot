package com.shopping.vn.service;

import com.shopping.vn.dto.HistoryDto;
import com.shopping.vn.entity.History;

public interface HistoryService {
  History historyAdd(HistoryDto history);

  History historyUpdate(HistoryDto history);

  History historyDelete(HistoryDto history);
}

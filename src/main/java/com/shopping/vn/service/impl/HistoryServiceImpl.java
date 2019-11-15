package com.shopping.vn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.HistoryDto;
import com.shopping.vn.entity.History;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.HistoryRepository;
import com.shopping.vn.service.HistoryService;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
  @Autowired
  private HistoryRepository historyRepository;

  @Override
  public History historyAdd(HistoryDto history, User user) {
    return historyRepository.save(History.convertSave(history, user));
  }

  @Override
  public History historyUpdate(HistoryDto history, User user) {
    return historyRepository.save(History.convertUpdate(history, user));
  }

  @Override
  public History historyDelete(HistoryDto history, User user) {
    return historyRepository.save(History.convertDelete(history, user));
  }

}

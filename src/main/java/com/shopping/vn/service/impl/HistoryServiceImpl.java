package com.shopping.vn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.HistoryDto;
import com.shopping.vn.entity.History;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.HistoryRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.HistoryService;
import com.shopping.vn.utils.Utils;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public History historyAdd(HistoryDto history) {
		User findUserByEmail = userRepository.findUserByEmail(Utils.getPrincipal());
		return historyRepository.save(History.convertSave(history, findUserByEmail));
	}

	@Override
	public History historyUpdate(HistoryDto history) {
		User findUserByEmail = userRepository.findUserByEmail(Utils.getPrincipal());
		return historyRepository.save(History.convertUpdate(history, findUserByEmail));
	}

	@Override
	public History historyDelete(HistoryDto history) {
		User findUserByEmail = userRepository.findUserByEmail(Utils.getPrincipal());
		return historyRepository.save(History.convertDelete(history, findUserByEmail));
	}

}

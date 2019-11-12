package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.entity.Menu;
import com.shopping.vn.repository.MenuRepository;
import com.shopping.vn.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<MenuDto> readAll() {
		List<Menu> menus = menuRepository.readAllbyParent();
		ModelMapper mapper = new ModelMapper();
		List<MenuDto> menuDtos = new ArrayList<MenuDto>();
		for (Menu menu : menus) {
			MenuDto menuParent = mapper.map(menu, MenuDto.class);
			List<Menu> menuChilds = menuRepository.readAllByMenuId(menu.getId());
			for (Menu menuChild : menuChilds) {
				MenuDto menuDto = mapper.map(menuChild, MenuDto.class);
				menuDtos.add(menuDto);
			}
			menuDtos.add(menuParent);

		}
		return menuDtos;
	}

}

package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.vn.dto.PrivilegeDto;
import com.shopping.vn.entity.Privilege;
import com.shopping.vn.repository.PrivilegeRepository;
import com.shopping.vn.service.PrivilegeService;
@Service
@Transactional
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;
	@Override
	public List<PrivilegeDto> readAll(String name,List<Long> roleIds) {
		List<Object[]> privileges=privilegeRepository.readAll(name, roleIds);
		List<PrivilegeDto> privilegeDtos=new ArrayList<PrivilegeDto>();
		for (Object[] result : privileges) {
			//ModelMapper mapper=new ModelMapper();
			PrivilegeDto privilegeDto=new PrivilegeDto();
			privilegeDto.setId(Long.parseLong(result[0].toString()));
			privilegeDto.setName(result[1].toString());
			privilegeDtos.add(privilegeDto);
		}
		return privilegeDtos;
	}

	@Override
	public PrivilegeDto detailPrivilege(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPrivilege(PrivilegeDto privilegeDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePrivilege(PrivilegeDto privilegeDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePrivilege(Long id) {
		// TODO Auto-generated method stub

	}

}

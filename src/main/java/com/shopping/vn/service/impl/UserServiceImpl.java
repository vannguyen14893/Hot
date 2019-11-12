package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.dto.PrivilegeDto;
import com.shopping.vn.dto.UserDto;
import com.shopping.vn.entity.Privilege;
import com.shopping.vn.entity.Role;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.UserServiceException;
import com.shopping.vn.repository.MenuRepository;
import com.shopping.vn.repository.PrivilegeRepository;
import com.shopping.vn.repository.RoleRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findUserByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("not user");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true,
				true, true, getAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public UserDto getUser(String email) {
		User user = userRepository.findUserByEmail(email);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public String permissions(Long userId) {
		List<Object[]> privileges = privilegeRepository.readAllByUser(userId);
		List<String> permissions = new ArrayList<>();
		for (Object object[] : privileges) {
			PrivilegeDto privilegeDto = new PrivilegeDto();
			privilegeDto.setName(object[0].toString());
			permissions.add(privilegeDto.getName());
		}
		return permissions.toString();
	}

	@Override
	public List<MenuDto> menus(Long userId) {
		List<Object[]> objects = menuRepository.readMenuByUser(userId);
		List<MenuDto> menus = new ArrayList<>();
		for (Object object[] : objects) {
			MenuDto menuDto = new MenuDto();
			menuDto.setName(object[1].toString());
			menuDto.setId(Long.parseLong(object[0].toString()));
			menus.add(menuDto);
		}
		return menus;
	}

	@Override
	public void registrationUser(UserDto userDto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userDto, User.class);
		List<Role> roles = new ArrayList<>();
		Role role = roleRepository.findRoleByRoleName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(encoder.encode(userDto.getPassword()));
		userRepository.save(user);
	}

	@Override
	public boolean checkEmailDoNotExit(String email) {
		User user = userRepository.findUserByEmail(email);
		if (user != null) {
			return false;
		}
		return true;
	}

	@Override
	public void addUser(UserDto userDto) {
		ModelMapper mapper = new ModelMapper();
		User user = mapper.map(userDto, User.class);
		user.setPassword(encoder.encode(userDto.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void updateUser(UserDto userDto) {
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new UserServiceException("user not found "));
		ModelMapper mapper = new ModelMapper();
		user = mapper.map(userDto, User.class);
		user.setPassword(encoder.encode(userDto.getPassword()));
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserServiceException("user not found " + id));
		user.setStatus(1);
		userRepository.save(user);

	}
}
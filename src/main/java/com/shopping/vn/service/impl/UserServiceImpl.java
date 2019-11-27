package com.shopping.vn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.vn.dto.HistoryDto;
import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.dto.UserDto;
import com.shopping.vn.entity.Privilege;
import com.shopping.vn.entity.Role;
import com.shopping.vn.entity.User;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.MenuRepository;
import com.shopping.vn.repository.PrivilegeRepository;
import com.shopping.vn.repository.RoleRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.HistoryService;
import com.shopping.vn.service.UserService;
import com.shopping.vn.utils.Constants;
import com.shopping.vn.utils.MailConstructor;

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
  @Autowired

  private ModelMapper mapper;
  @Autowired
  private HistoryService historyService;
  @Autowired
  private MailConstructor mailConstructor;
  @Autowired
  private JavaMailSender mailSender;

  @Override
  public UserDetails loadUserByUsername(String username) {

    User user = userRepository.findUserByEmail(username);
    if (user == null) {
      throw new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND);
    }
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), true, true, true, true, getAuthorities(user.getRoles()));
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
    return UserDto.convertUser(user);
  }

  @Override
  public boolean checkPermission(String email, String permission) {
    List<Object[]> privileges = privilegeRepository.readAllByUser(email);
    for (Object[] object : privileges) {
      if (object[0].toString().equals(permission)) {
        return true;
      }
    }
    return false;

  }

  @Override
  public List<MenuDto> menus(Long userId) {
    List<Object[]> objects = menuRepository.readMenuByUser(userId);
    List<MenuDto> menus = new ArrayList<>();
    for (Object[] object : objects) {
      MenuDto menuDto = new MenuDto();
      menuDto.setName(object[1].toString());
      menuDto.setId(Long.parseLong(object[0].toString()));
      menus.add(menuDto);
    }
    return menus;
  }

  @Override
  public void registrationUser(UserDto userDto) {
    Role role = roleRepository.findRoleByRoleName("ROLE_USER");
    if (role == null)
      throw new RuntimeExceptionHandling(Constants.MESSENGER.ROLE_NOT_FOUND);
    userRepository.save(User.convertSave(userDto, role));
    // Send mail after add new user
    SimpleMailMessage email = mailConstructor.constructNewUserEmail(userDto, userDto.getPassword());
    mailSender.send(email);
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
    User user = mapper.map(userDto, User.class);
    user.setPassword(encoder.encode(userDto.getPassword()));
    userRepository.save(user);
    // save history
    
    HistoryDto historyDto = new HistoryDto();
    historyDto.setDescrition("add new user" + " " + userDto.getEmail());
    historyService.historyAdd(historyDto);

  }

  @Override
  public void updateUser(UserDto userDto) {
    User user = userRepository.findById(userDto.getId())
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND));
    List<Role> roles = new ArrayList<>();
    for (Long id : userDto.getRoleIds()) {
      Role role = roleRepository.findById(id).orElseThrow(
          () -> new RuntimeExceptionHandling(Constants.MESSENGER.ROLE_NOT_FOUND + " " + id));
      roles.add(role);
    }
    userRepository.save(User.convertUpdate(userDto, user, roles));
    // save history
    HistoryDto historyDto = new HistoryDto();
    historyDto.setDescrition("update user" + " " + userDto.getEmail());
    historyService.historyUpdate(historyDto);
  }

  @Override
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND + " " + id));
    user.setStatus(1);
    userRepository.save(user);
    
    HistoryDto historyDto = new HistoryDto();
    historyDto.setDescrition("delete user" + " " + user.getEmail());
    historyService.historyDelete(historyDto);
   
  }


  @Override
  public List<UserDto> readAll(SortFilterDto filter) {
    List<User> users = userRepository.readAll(filter);
    List<UserDto> userDtos = new ArrayList<>();
    for (User user : users) {
      userDtos.add(UserDto.convertUser(user));
    }
    return userDtos;
  }

  @Override
  public UserDto findById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.USER_NOT_FOUND + " " + id));
    return UserDto.convertUser(user);
  }

@Override
public UserDto countUser(SortFilterDto filter) {
	Long countUser = userRepository.countUser(filter);
	UserDto userDto=new UserDto();
	userDto.setCount(countUser);
	return userDto;
}

}

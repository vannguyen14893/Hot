package com.shopping.vn.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopping.vn.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "full_name")
	private String fullName;
	private String email;
	private String password;
	private String mobile;
	private int status;
	private Date birthDay;
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private List<Role> roles;
	
	 
	public static final User convertSave(UserDto userDto,Role role) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		User user=new User();
		user.setEmail(userDto.getEmail());
		user.setBirthDay(userDto.getBirthDay());
		user.setFullName(userDto.getFullName());
		user.setMobile(userDto.getMobile());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setStatus(userDto.getStatus());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		
		return user;
	}
	public static final User convertUpdate(UserDto userDto,User user,List<Role> roles) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		user.setId(userDto.getId());
		user.setEmail(userDto.getEmail());
		user.setBirthDay(userDto.getBirthDay());
		user.setFullName(userDto.getFullName());
		user.setMobile(userDto.getMobile());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setStatus(userDto.getStatus());
		user.setRoles(roles);
		return user;
	}
}

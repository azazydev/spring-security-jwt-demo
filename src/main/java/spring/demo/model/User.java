package spring.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "APP_USER")
@NamedEntityGraph(name = "user.withRoles", attributeNodes = @NamedAttributeNode("roles"))
@Data
public class User {
	@Id
	@Column(name = "id")
	private Integer userId;

	@Column(name = "user_name")
	private String username;
	
	@Transient
	private String password;

	@Column(name = "arabic_name")
	private String arabicName;

	@Column(name = "english_name")
	private String englishName;

	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<Role>();

}

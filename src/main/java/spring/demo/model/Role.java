package spring.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "APP_Role")
@Data
public class Role {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "role_code")
	private String roleCode;
	
	@Column(name = "role_desc")
	private String roleDesc;
}

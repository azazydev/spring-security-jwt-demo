package spring.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT user FROM User user WHERE LOWER(user.username) = LOWER(:username)")
	@EntityGraph(value = "user.withRoles", type = EntityGraphType.LOAD)
	public User getByUserName(@Param("username") String username);

}

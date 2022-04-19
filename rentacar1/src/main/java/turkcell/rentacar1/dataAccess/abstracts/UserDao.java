package turkcell.rentacar1.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar1.entities.concretes.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	public User getByUserId(int userId);

}

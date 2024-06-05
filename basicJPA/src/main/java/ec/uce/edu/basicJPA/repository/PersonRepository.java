package ec.uce.edu.basicJPA.repository;

import ec.uce.edu.basicJPA.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface PersonRepository extends JpaRepository<User,Integer> {

    User findByUserAndPassword(String user, String password);

}

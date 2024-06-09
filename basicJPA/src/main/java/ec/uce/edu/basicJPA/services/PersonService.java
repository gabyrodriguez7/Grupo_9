package ec.uce.edu.basicJPA.services;

import ec.uce.edu.basicJPA.models.User;
import ec.uce.edu.basicJPA.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public void save(User person){
       repository.save(person);
    }

    public User findByUserAndPassword(String user, String password) {
        return repository.findByUserAndPassword(user, password);
    }

    public User validateAndUpdateUser(String username, String password, User updatedUser) {
        User foundUser = repository.findByUserAndPassword(username, password);
        if (foundUser == null) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        // Actualizar los campos necesarios sin modificar username y password
        foundUser.setLifeShip(updatedUser.getLifeShip());
        foundUser.setScore(updatedUser.getScore());
        foundUser.setLevel(updatedUser.getLevel());
        foundUser.setLifeAllien(updatedUser.getLifeAllien());
        foundUser.setNumAllien(updatedUser.getNumAllien());
        return repository.save(foundUser);
    }

}
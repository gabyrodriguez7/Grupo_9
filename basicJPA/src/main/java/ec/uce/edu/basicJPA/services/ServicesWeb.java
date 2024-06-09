package ec.uce.edu.basicJPA.services;

import ec.uce.edu.basicJPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicesWeb {

    @Autowired
    private PersonService userService;

    @PostMapping(value = "/createUser")
    private void createUser(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam String user, @RequestParam String password) {
        User foundUser = userService.findByUserAndPassword(user, password);
        if (foundUser == null) {
            throw new RuntimeException("User no encontrado");
        }
        return foundUser;
    }

    @PutMapping(value = "/updateUser")
    public User updateUser(@RequestParam String user, @RequestParam String password, @RequestBody User updatedUser) {
        User foundUser = userService.findByUserAndPassword(user, password);
        if (foundUser == null) {
            throw new RuntimeException("User no encontrado");
        }
        // Actualizar los campos restantes
        foundUser.setLifeShip(updatedUser.getLifeShip());
        foundUser.setScore(updatedUser.getScore());
        foundUser.setLevel(updatedUser.getLevel());
        foundUser.setLifeAllien(updatedUser.getLifeAllien());
        foundUser.setNumAllien(updatedUser.getNumAllien());

        userService.save(foundUser); // Guardar los cambios

        return foundUser;
    }

}
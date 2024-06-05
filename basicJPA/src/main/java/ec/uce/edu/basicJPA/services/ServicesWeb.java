package ec.uce.edu.basicJPA.services;


import ec.uce.edu.basicJPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicesWeb {

    @Autowired
    private PersonService userService;

    @RequestMapping(value="/miurl2",method = RequestMethod.GET)
    public String miSegundoEndPoint(){

       return "Este es mi segundo end point con POST";
    }


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
        foundUser.setLife(updatedUser.getLife());
        foundUser.setScore(updatedUser.getScore());
        foundUser.setLevel(updatedUser.getLevel());

        userService.save(foundUser); // Guardar los cambios

        return foundUser;
    }





}

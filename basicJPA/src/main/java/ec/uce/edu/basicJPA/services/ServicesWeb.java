package ec.uce.edu.basicJPA.services;


import ec.uce.edu.basicJPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServicesWeb {

    @Autowired
    private PersonService userService;

 /* @RequestMapping(value="/user/",method = RequestMethod.GET)
    public String miPrimerEndPoint(@RequestParam("name")String name){

        return "Este es mi primer End Point";
    }*/

    @RequestMapping(value="/miurl2",method = RequestMethod.GET)
    public String miSegundoEndPoint(){

       return "Este es mi segundo end point con POST";
    }

    @PostMapping(value = "/createUser")
    private void createUser(@RequestBody User user) {
         userService.save(user);
    }

}

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
   /* public void printAllPersons() {
        List<Person> persons = repository.findAll();
        System.out.println("Lista de personas en la base de datos:");
        for (Person person : persons) {
            System.out.println(person.toString());
        }
    }

    public void findIdentity(long id){
         //Para decirte que puede ir cualquier tipo de clase
        Optional<Person> optionalPerson = repository.findById(id);

        if(optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            System.out.println("Persona encontrada: " + person);
        } else {
            System.out.println("No se encontró ninguna persona con el ID: " + id);
        }
    }
    public void findLastname(String lastname){
       List<Person> persons = repository.findByLastname(lastname);
        if (!persons.isEmpty()) {
            System.out.println("Personas encontradas con el apellido '" + lastname + "':");
            for (Person person : persons) {
                System.out.println(person);
            }
        } else {
            System.out.println("No se encontraron personas con el apellido '" + lastname + "'");
        }
    }
    public void updatePerson(Person person){
        repository.save(person);
        System.out.println("Persona actualizada exitosamente: " + person);
    }
*/




}

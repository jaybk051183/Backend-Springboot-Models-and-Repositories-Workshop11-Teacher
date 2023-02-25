package com.example.les11.controller;

import com.example.les11.model.Teacher;
import com.example.les11.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController

//Maak root path (/teachers)welke in de applicatie gebruikt gaat worden
@RequestMapping("teachers")
public class TeacherController {

    //Om toegang te krijgen tot de database dien je een repository in the controller klasse aan te maken. (Autowired zorgt ervoor dat je springboot zegt om daar een object komt welke gebruikt gaat worden in de database.)
    //TeacherRepository is een interface. Hoe zorg je ervoor dat er een object ervoor aanwezig is in de database?  => @Autowired (dependency injection)
    @Autowired
    TeacherRepository repos;

    //Maak een GetMapping die een lijst van teachers gaat teruggeven
    //Iterable is een interface van Java welke geavanceerder is dan een List
    @GetMapping
    public ResponseEntity<Iterable<Teacher>> getTeachers(){
        return  ResponseEntity.ok(repos.findAll());
    }

    //Maak een PostMapping om in de datebase wat data te stoppen:
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher t){
        // t wordt als JSON door de client doorgegeven in PostMan welke vervolgens als Teacher object binnenkomt en opgeslagen wordt in de database.
        repos.save(t);

        //Vervolgens een ResponseEntity teruggeven.
        //Het pad van het aangemaakt resource meegeven als parameter in de ResponseEntity.
        //RESTFULL programmeren principe: Bij het aanmaken van een nieuwe Resource in de header van de response vertellen wat het pad is naar het nieuwe resource.
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + t.getId()).toUriString());

        //Roep het pad op van de request en plak vervolgens de ID eraan vast van de entiteit welke is opgeslagen.
        return ResponseEntity.created(uri).body(t);
    }

    @GetMapping("/before")
    public ResponseEntity<Iterable<Teacher>> getTeachersBefore(@RequestParam LocalDate date){
        return ResponseEntity.ok(repos.findByDobBefore(date));

    }
}

//Bij de POST request in POSTMAN geef je geen ID als JSON mee omdat ID serverside wordt gegenereerd en client side wordt meegegeven.
//URI (regel39) wordt in header meegegeven in POSTMAN: http://localhost:8080/teachers/1
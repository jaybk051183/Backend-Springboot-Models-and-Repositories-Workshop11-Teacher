package com.example.les11.repository;

import com.example.les11.model.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    //In de repository kun je queries aanmaken: bijv. een pad query, waarbij je een datum als parameter meegeeft en alleen de teacher terugkrijgt welke voldoen aan de voorwaarde.
    Iterable<Teacher> findByDobBefore(LocalDate date);

    //De query kun je vervolgens gebruiken in de TeacherController klasse.

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro2.CRUD.repository;

import com.registro2.CRUD.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author macbookair
 */
public interface PersonaRepository extends JpaRepository <Persona, Long> {
    
}

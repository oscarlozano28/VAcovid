/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.session;

import com.vacovid.entity.Personal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JEFRY
 */
@Stateless
public class PersonalFacade extends AbstractFacade<Personal> implements PersonalFacadeLocal {

    @PersistenceContext(unitName = "vacovidPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalFacade() {
        super(Personal.class);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.UsersGroups;
import entity.UsersGroupsPK;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Shane
 */
@Stateless
public class UsersGroupsFacade extends AbstractFacade<UsersGroups> {
    @PersistenceContext(unitName = "YBMProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersGroupsFacade() {
        super(UsersGroups.class);
    }
    
    public void addUser(String type, String email){
        
        UsersGroups user = new UsersGroups(type, email);
                
        em.persist(user);
    }//end addUser
}

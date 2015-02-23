/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Customer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Shane
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {
    @EJB
    private UsersGroupsFacade user;
   
    
    @PersistenceContext(unitName = "YBMProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public int addCustomer(String name, String email, String phone, String address, String password) {
    
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setPassword(password);
     
        user.addUser("shane", email);
        
        em.persist(customer);
        
        return 1;
        
    }//end addCustomer
    
}

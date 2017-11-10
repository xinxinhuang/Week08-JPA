/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 721292
 */
public class DBUtil {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("NotePU");
    
    public static EntityManagerFactory getEmFactory(){
        return emf;
    }
    
    public static void close(){
        emf.close(); //shut down factory
    }
    
}

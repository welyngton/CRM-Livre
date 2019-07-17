/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import bean.Conta;
import java.sql.*;
import java.util.List;
/*import javax.persistence.Query;*/
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author W
 */
public class ContaDao {
    
    private Session session;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Conta").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }    
    
    public void inserirConta(Conta conta) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        //session= new Configuration().configure().buildSessionFactory().openSession();
        //Transaction t=session.beginTransaction();  
        session.beginTransaction();
        //t.begin();
        session.save(conta);               
        //t.commit();      
        session.getTransaction().commit();
        session.close();         
    }

    public void atualizarConta(Conta conta) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        //session= new Configuration().configure().buildSessionFactory().openSession();
        //Transaction t=session.beginTransaction();  
        session.beginTransaction();
        //t.begin();
        session.update(conta);               
        //t.commit();      
        session.getTransaction().commit();
        session.close();         
    }

    public void removerConta(Conta conta) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        //session= new Configuration().configure().buildSessionFactory().openSession();
        //Transaction t=session.beginTransaction();  
        session.beginTransaction();
        //t.begin();
        session.delete(conta);               
        //t.commit();      
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarContas(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Conta");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);            
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Conta").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
    
    public List obterContaPorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Conta c WHERE c.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }    
}
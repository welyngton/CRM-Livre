package dao;

import bean.Campanha;
import java.sql.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CampanhaDao {
    
    private Session session;
    private Connection con = null;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Campanha").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }
    
    public void inserirCampanha(Campanha campanha) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(campanha);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void atualizarCampanha(Campanha campanha) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(campanha);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void removerCampanha(Campanha campanha) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(campanha);
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarCampanhas(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Campanha");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Campanha").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
    
    public List obterCampanhaPorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Campanha c WHERE c.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
}
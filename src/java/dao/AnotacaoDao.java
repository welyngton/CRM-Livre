package dao;

import bean.Anotacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/*import javax.persistence.Query;*/
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author W
 */
public class AnotacaoDao {
    
    private Session session;
    private Connection con = null;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Anotacao").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }
    
    public void inserirAnotacao(Anotacao anotacao) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(anotacao);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void atualizarAnotacao(Anotacao anotacao) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(anotacao);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void removerAnotacao(Anotacao anotacao) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(anotacao);
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarAnotacoes(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Anotacao");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Anotacao").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
    
    public List obterAnotacaoPorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Anotacao a WHERE a.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
}
package dao;

import bean.Oportunidade;
import java.sql.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class OportunidadeDao {
    
    private Session session;
    private Connection con = null;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Oportunidade").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }
    
    public void inserirOportunidade(Oportunidade oportunidade) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(oportunidade);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void atualizarOportunidade(Oportunidade oportunidade) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(oportunidade);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void removerOportunidade(Oportunidade oportunidade) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(oportunidade);
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarOportunidades(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Oportunidade");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Oportunidade").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
    
    public List obterOportunidadePorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Oportunidade o WHERE o.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
}
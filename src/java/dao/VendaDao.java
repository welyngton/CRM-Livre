package dao;

import bean.Venda;
import java.sql.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class VendaDao {
    
    private Session session;
    private Connection con = null;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Venda").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }
    
    public void inserirVenda(Venda venda) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(venda);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void atualizarVenda(Venda venda) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(venda);               
        session.getTransaction().commit();
        session.close();         
    }
    
    public void removerVenda(Venda venda) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(venda);
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarVendas(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Venda");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Venda").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
    
    public List obterVendaPorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Venda v WHERE v.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
}
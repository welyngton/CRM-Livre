/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import bean.Tarefa;
import java.sql.*;
import java.util.List;
/*import javax.persistence.Query;*/
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author W
 */
public class TarefaDao {
    
    private Session session;
    private PreparedStatement st;
    private int qtdTotalRegistros = 0;
    
    public int getQtdRegistros() {
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            qtdTotalRegistros = session.createQuery("FROM Tarefa").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return qtdTotalRegistros;
    }
    
    public void inserirTarefa(Tarefa tarefa) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();    
        session.save(tarefa);                     
        session.getTransaction().commit();
        session.close();         
    }
    public void removerTarefa(Tarefa tarefa) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();    
        session.delete(tarefa);                     
        session.getTransaction().commit();
        session.close();         
    }

    public void atualizarTarefa(Tarefa tarefa) throws Exception{
    
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();
        session.beginTransaction();    
        session.update(tarefa);                     
        session.getTransaction().commit();
        session.close();         
    }

    public List selecionarTarefas(int deslocamento, int qtdRegistros) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Tarefa");
            query.setFirstResult(deslocamento);
            query.setMaxResults(qtdRegistros);
            listaRetorno = query.list();
            qtdTotalRegistros = session.createQuery("FROM Tarefa").list().size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }

        public List obterTarefaPorId(int id) {
        
        List listaRetorno = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Tarefa t WHERE t.id = "+ id);
            listaRetorno = query.list();            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        return listaRetorno;
    }
}
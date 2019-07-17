package dao;

import bean.Usuario;
import java.sql.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDao {
    
    private Session session;
    private Connection con = null;
    private PreparedStatement st;

    public Usuario logar(String usuario, String senha) {        
        
        List listResultado = null;
        HibernateUtil.getSessionFactory();      
        session = HibernateUtil.getSession();        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Usuario u WHERE u.login ='"+usuario+"'");
            listResultado = query.list();
            if(listResultado.size() > 0) {
                Usuario usuarioLogin = (Usuario)listResultado.get(0);
                return usuarioLogin;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }        
        return null;
    }
}
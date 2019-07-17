package bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="venda")

public class Venda implements java.io.Serializable
{
    public Venda() {
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;   
    private Oportunidade oportunidade;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
}
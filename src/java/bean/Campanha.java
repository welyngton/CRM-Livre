package bean;

import java.text.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="campanha")

public class Campanha implements java.io.Serializable
{
    public Campanha() {
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
     
    @Column
    private String tema = null;
    @Column
    private String descricao = null;
    @Column
    private String dataCadastro = null;
    @Column
    private String dataConclusao = null;
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Campanha){
            Campanha tarefa = (Campanha) obj;
            return tarefa.getId() == this.getId();
        }         
        return false;
    }
 
    public int getId() {
        return this.id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
       
   public String getTema(){
      return this.tema;
   }
   public String getDescricao(){
      return this.descricao;
   }
   public String getDataCadastro(){
       return this.dataCadastro;
   }
   public String getDataConclusao(){
       return this.dataConclusao;
   }   
   public void setTema(String assunto){
      this.tema = assunto;
   }
   public void setDescricao(String descricao){
      this.descricao = descricao;
   }
   public void setDataCadastro(String dataCadastro) throws ParseException{
       this.dataCadastro = dataCadastro;
   }
   public void setDataConclusao(String dataConclusao) throws ParseException{
       this.dataConclusao = dataConclusao;
   }
}
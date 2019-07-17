
package bean;

import java.text.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="anotacao")

public class Anotacao implements java.io.Serializable
{
    public Anotacao() {
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
     
    @Column
    private String assunto = null;
    @Column
    private String descricao = null;
    @Column
    private String dataCadastro = null;
    @Column
    private String criador = null;
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Anotacao){
            Anotacao tarefa = (Anotacao) obj;
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
    
    public String getCriador() {
        return this.criador;
    }
 
    public void setCriador(String criador) {
        this.criador = criador;
    }
       
   public String getAssunto(){
      return this.assunto;
   }
   public String getDescricao(){
      return this.descricao;
   }
   public String getDataCadastro(){
       return this.dataCadastro;
      //return new SimpleDateFormat().format(data);
   }
   public void setAssunto(String assunto){
      this.assunto = assunto;
   }
   public void setDescricao(String descricao){
      this.descricao = descricao;
   }
   public void setDataCadastro(String dataCadastro) throws ParseException{
       this.dataCadastro = dataCadastro;
      //this.data = new SimpleDateFormat("dd/MM/yy").parse(data);
   }
}
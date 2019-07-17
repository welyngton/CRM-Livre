package bean;

import java.text.ParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name="atividade")

public class Atividade implements java.io.Serializable
{
    public Atividade() {
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
     
    @Column
    private String assunto;
    @Column
    private String descricao;
    @Column
    private String dataCadastro;
    @Column
    private String dataAtividade;
    @Column
    private String tipo;   
    
    private Usuario usuario;
     
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Atividade){
            Atividade tarefa = (Atividade) obj;
            return tarefa.getId() == this.getId();
        }         
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }
 
    public int getId() {
        return this.id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
   
   public String getAssunto(){
      return this.assunto;
   }
   public String getDescricao(){
      return this.descricao;
   }
   public String getTipo(){
      return this.tipo;
   }
   public void setTipo(String tipo){
      this.tipo = tipo;
   }
   public String getDataCadastro(){
       return this.dataCadastro;
      //return new SimpleDateFormat().format(data);
   }
   public String getDataAtividade(){
       return this.dataAtividade;
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
   public void setDataAtividade(String dataAtividade) throws ParseException{
       this.dataAtividade = dataAtividade;
      //this.data = new SimpleDateFormat("dd/MM/yy").parse(data);
   }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
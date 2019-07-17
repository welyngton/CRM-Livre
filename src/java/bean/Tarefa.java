/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="tarefa")

/**
 *
 * @author W
 */
public class Tarefa implements java.io.Serializable
{
    public Tarefa() {
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
    private String dataConclusao = null;
    @Column
    private String tipo = null;
     
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tarefa){
            Tarefa tarefa = (Tarefa) obj;
            return tarefa.getId() == this.getId();
        }         
        return false;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
   
   public String getAssunto(){
      return assunto;
   }
   public String getDescricao(){
      return descricao;
   }
   public String getDataCadastro(){
       return dataCadastro;
      //return new SimpleDateFormat().format(data);
   }
   public void setAssunto(String assunto){
      this.assunto = assunto;
   }
   public void setDescricao(String descricao){
      this.descricao = descricao;
   }
   public void setDataCadastro(String data) throws ParseException{
       this.dataCadastro = data;
      //this.data = new SimpleDateFormat("dd/MM/yy").parse(data);
   }
   
   public void setDataConclusao(String data) {
       this.dataConclusao = data;
   }
   
   public String getDataConclusao() {
       return this.dataConclusao;
   }
   
   public void setTipo(String tipo) {
       this.tipo = tipo;
   }
   
   public String getTipo() {
       return this.tipo;
   }
   
}
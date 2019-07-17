package bean;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 

@Entity
@Table(name="usuario")

public class Usuario implements java.io.Serializable
{
    public Usuario() {
    }
    @Id
    @Column   
    private int id;    
    @Column    
    private String login;    
    @Column
    private String senha;
    @Column
    private String tipo;
    @Column
    private String telefone;
    @Column
    private String cpf;
    @Column
    private String endereco;
    @Column
    private String nome;
    
    private List<Atividade> atividades;
   
     
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Usuario){
            Usuario usr = (Usuario) obj;
            return (usr.getLogin() == null ? this.getLogin() == null : usr.getLogin().equals(this.getLogin()));
        }         
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }
 
    public String getLogin() {
        return login;
    }
 
    public void setLogin(String login) {
        this.login = login;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the atividades
     */
    public List<Atividade> getAtividades() {
        return atividades;
    }

    /**
     * @param atividades the atividades to set
     */
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
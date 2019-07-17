/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import bean.Anotacao;
import dao.AnotacaoDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author W
 */
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if((action != null) && (!action.isEmpty())) {
        if(action.equals("pesquisar")) {  
            int page = 1;
            int recordsPerPage = 3;
            if(request.getParameter("page") != null)
                    page = Integer.parseInt(request.getParameter("page"));
            AnotacaoDao dao = new AnotacaoDao();
            List listaAnotacoes = dao.selecionarAnotacoes((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstAnotacoes",listaAnotacoes.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("anotacoes.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           AnotacaoDao dao = new AnotacaoDao();
           if(request.getParameter("id") != null) {
            int idAnotacao = Integer.parseInt(request.getParameter("id"));
            List anotacaoList = dao.obterAnotacaoPorId(idAnotacao);
            Anotacao anotacaoEdit = (Anotacao)anotacaoList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Anotação</H2>");

            response.getWriter().println("<form class=\"form\" action=\"AnotacaoServlet?action=salvarEdicao&id="+idAnotacao+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Assunto:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"assuntoEdit\" id=\"assuntoEdit\" value=\"" + anotacaoEdit.getAssunto() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + anotacaoEdit.getDescricao() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" + anotacaoEdit.getDataCadastro() + "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Criador:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"criadorEdit\" id=\"criadorEdit\" value=\"" + anotacaoEdit.getCriador() + "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarAnotacao\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id de Anotacao.");  
        } else if (action.equals("remover")){
                       
            AnotacaoDao dao = new AnotacaoDao();
            bean.Anotacao anotacao = new Anotacao();
            try {
                int idAnotacao = Integer.parseInt(request.getParameter("id"));
                anotacao.setId(idAnotacao);
                dao.removerAnotacao(anotacao);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAnotacao(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            AnotacaoDao dao = new AnotacaoDao();
            bean.Anotacao anotacao = new Anotacao();
            try {
                int idAnotacao = Integer.parseInt(request.getParameter("id"));
                anotacao.setId(idAnotacao);
                anotacao.setDescricao(request.getParameter("descricaoEdit")); 
                anotacao.setAssunto(request.getParameter("assuntoEdit"));
                anotacao.setDataCadastro(request.getParameter("dataCadastroEdit"));
                anotacao.setCriador(request.getParameter("criadorEdit"));
                dao.atualizarAnotacao(anotacao);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAnotacao(request,response);
        
        } else if (action.equals("salvar")){           
        
            AnotacaoDao dao = new AnotacaoDao();
            bean.Anotacao anotacao = new Anotacao();
            try {
                anotacao.setDescricao(request.getParameter("descricao")); 
                anotacao.setAssunto(request.getParameter("assunto"));
                anotacao.setDataCadastro(request.getParameter("dataCadastro"));
                anotacao.setCriador(request.getParameter("criador"));
                dao.inserirAnotacao(anotacao);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAnotacao(request,response);
            }
        }
    }

   /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    
    }

    /**
     * Returns a short descrption of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controle da classe Anotacao";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {  
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
    }   
    
       protected void redirectAnotacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
            int page = 1;
            int recordsPerPage = 3;
            if(request.getParameter("page") != null)
                    page = Integer.parseInt(request.getParameter("page"));
            AnotacaoDao dao = new AnotacaoDao();
            List listaAnotacoes = dao.selecionarAnotacoes((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstAnotacoes",listaAnotacoes.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("anotacoes.jsp");
            dispatcher.forward(request, response);            
       }
    

}

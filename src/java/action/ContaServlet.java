/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import bean.Conta;
import dao.ContaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ContaServlet extends HttpServlet {

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
            ContaDao dao = new ContaDao();
            List listaContas = dao.selecionarContas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstContas",listaContas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("contas.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           ContaDao dao = new ContaDao();
           if(request.getParameter("id") != null) {
            int idConta = Integer.parseInt(request.getParameter("id"));
            List contaList = dao.obterContaPorId(idConta);
            Conta contaEdit = (Conta)contaList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Conta</H2>");

            response.getWriter().println("<form class=\"form\" action=\"ContaServlet?action=salvarEdicao&id="+idConta+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Nome:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"nomeEdit\" id=\"nomeEdit\" value=\"" + contaEdit.getNome()+ "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Tipo Documento:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"tipoDocumentoEdit\" id=\"tipoDocumentoEdit\" value=\"" + contaEdit.getTipoDocumento()+ "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Documento:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"documentoEdit\" id=\"documentoEdit\" value=\"" + contaEdit.getDocumento()+ "\" required disabled />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Telefone:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"telefoneEdit\" id=\"telefoneEdit\" value=\"" + contaEdit.getTelefone()+ "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Criador:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"emailEdit\" id=\"emailEdit\" value=\"" + contaEdit.getEmail()+ "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Endereco:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"enderecoEdit\" id=\"enderecoEdit\" value=\"" + contaEdit.getEndereco()+ "\" required disabled/>");
            response.getWriter().println("</div>");
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"criadorEdit\" id=\"criadorEdit\" value=\"" + contaEdit.getDataCadastro()+ "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Anotacoes:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"criadorEdit\" id=\"criadorEdit\" value=\"" + contaEdit.getAnotacoes()+ "\" required disabled/>");
            response.getWriter().println("</div>");             
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarConta\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id da Conta.");  
        } else if (action.equals("remover")){
                       
            ContaDao dao = new ContaDao();
            bean.Conta conta = new Conta();
            try {
                int idConta = Integer.parseInt(request.getParameter("id"));
                conta.setId(idConta);
                dao.removerConta(conta);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectConta(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            ContaDao dao = new ContaDao();
            bean.Conta conta = new Conta();
            try {
                int idConta = Integer.parseInt(request.getParameter("id"));
                conta.setId(idConta);
                conta.setNome(request.getParameter("nome")); 
                conta.setTipoDocumento(request.getParameter("tipoDocumento")); 
                conta.setDocumento(request.getParameter("documento")); 
                conta.setTelefone(request.getParameter("telefone")); 
                conta.setEmail(request.getParameter("email")); 
                conta.setEndereco(request.getParameter("endereco")); 
                conta.setDataCadastro(request.getParameter("dataCadastro")); 
                conta.setAnotacoes(request.getParameter("anotacoes")); 
                dao.atualizarConta(conta);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectConta(request,response);
        
        } else if (action.equals("salvar")){           
        
            ContaDao dao = new ContaDao();
            bean.Conta conta = new Conta();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();           
            try {
                conta.setNome(request.getParameter("nome")); 
                conta.setTipoDocumento(request.getParameter("tipoDocumento")); 
                conta.setDocumento(request.getParameter("documento")); 
                conta.setTelefone(request.getParameter("telefone")); 
                conta.setEmail(request.getParameter("email")); 
                conta.setEndereco(request.getParameter("endereco")); 
                conta.setDataCadastro(formatoData.format(data));
                conta.setAnotacoes(request.getParameter("anotacoes")); 
                dao.inserirConta(conta);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ContaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectConta(request,response);
            }
        }     
    }

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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    protected void redirectConta(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 3;
        if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
        ContaDao dao = new ContaDao();
        List listaContas = dao.selecionarContas((page-1)*recordsPerPage,recordsPerPage);            
        int noOfRecords = dao.getQtdRegistros();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("lstContas",listaContas.toArray());
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("contas.jsp");
        dispatcher.forward(request, response);            
    }        

}

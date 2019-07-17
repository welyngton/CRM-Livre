/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import bean.Tarefa;
import dao.TarefaDao;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author W
 */
public class TarefaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if((action != null) && (!action.isEmpty())) {
        if(action.equals("pesquisar")) {  
            int page = 1;
            int recordsPerPage = 3;
            if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
            TarefaDao dao = new TarefaDao();
            List listaTarefas = dao.selecionarTarefas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstTarefas",listaTarefas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("tarefas.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           TarefaDao dao = new TarefaDao();
           if(request.getParameter("id") != null) {
            int idTarefa = Integer.parseInt(request.getParameter("id"));
            List tarefaList = dao.obterTarefaPorId(idTarefa);
            Tarefa tarefaEdit = (Tarefa)tarefaList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Tarefa</H2>");

            response.getWriter().println("<form class=\"form\" action=\"TarefaServlet?action=salvarEdicao&id="+idTarefa+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Tipo de Tarefa:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"tipoTarefaEdit\" id=\"tipoTarefaEdit\" value=\"" + tarefaEdit.getTipo()+ "\" disabled/>");
            response.getWriter().println("</div>");             
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Assunto:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"assuntoEdit\" id=\"assuntoEdit\" value=\"" + tarefaEdit.getAssunto() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + tarefaEdit.getDescricao() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" + tarefaEdit.getDataCadastro() + "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Conclusão:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"criadorEdit\" id=\"criadorEdit\" value=\"" + tarefaEdit.getDataConclusao() + "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarTarefa\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id da Tarefa.");  
        } else if (action.equals("remover")){
                       
            TarefaDao dao = new TarefaDao();
            bean.Tarefa tarefa = new Tarefa();
            try {
                int idTarefa = Integer.parseInt(request.getParameter("id"));
                tarefa.setId(idTarefa);
                dao.removerTarefa(tarefa);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectTarefa(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            TarefaDao dao = new TarefaDao();
            bean.Tarefa tarefa = new Tarefa();
           
            try {
                int idTarefa = Integer.parseInt(request.getParameter("id"));
                tarefa.setId(idTarefa);
                tarefa.setAssunto(request.getParameter("assunto"));
                tarefa.setDescricao(request.getParameter("descricao"));                  
                tarefa.setDataConclusao(request.getParameter("dataConclusao"));
                tarefa.setTipo(request.getParameter("tipoTarefa"));
                dao.atualizarTarefa(tarefa);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectTarefa(request,response);
        
        } else if (action.equals("salvar")){           
        
            TarefaDao dao = new TarefaDao();
            bean.Tarefa tarefa = new Tarefa();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();            
            try {
                tarefa.setAssunto(request.getParameter("assunto"));
                tarefa.setDescricao(request.getParameter("descricao"));                  
                tarefa.setDataCadastro(formatoData.format(data));
                tarefa.setDataConclusao(request.getParameter("dataConclusao"));
                tarefa.setTipo(request.getParameter("tipoTarefa"));
                dao.inserirTarefa(tarefa);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectTarefa(request,response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TarefaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>  
    
    protected void redirectTarefa(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 3;
        if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
        TarefaDao dao = new TarefaDao();
        List listaTarefas = dao.selecionarTarefas((page-1)*recordsPerPage,recordsPerPage);            
        int noOfRecords = dao.getQtdRegistros();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("lstTarefas",listaTarefas.toArray());
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("tarefas.jsp");
        dispatcher.forward(request, response);            
    }        

}

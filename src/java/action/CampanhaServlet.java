package action;

import bean.Campanha;
import dao.CampanhaDao;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CampanhaServlet extends HttpServlet {

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
            CampanhaDao dao = new CampanhaDao();
            List<Campanha> listaCampanhas = dao.selecionarCampanhas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstCampanhas",listaCampanhas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("campanhas.jsp");
            dispatcher.forward(request, response);                 
        } else if (action.equals("editar")){
           CampanhaDao dao = new CampanhaDao();
           if(request.getParameter("id") != null) {
            int idCampanha = Integer.parseInt(request.getParameter("id"));
            List campanhaList = dao.obterCampanhaPorId(idCampanha);
            Campanha campanhaEdit = (Campanha)campanhaList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Campanha</H2>");

            response.getWriter().println("<form class=\"form\" action=\"CampanhaServlet?action=salvarEdicao&id="+idCampanha+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Tema:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"temaEdit\" id=\"temaEdit\" value=\"" + campanhaEdit.getTema() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + campanhaEdit.getDescricao() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" + campanhaEdit.getDataCadastro() + "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Conclusão:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataConclusaoEdit\" id=\"dataConclusaoEdit\" value=\"" + campanhaEdit.getDataConclusao()+ "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarCampanha\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id da Campanha.");  
        } else if (action.equals("remover")){
                       
            CampanhaDao dao = new CampanhaDao();
            bean.Campanha campanha = new Campanha();
            try {
                int idCampanha = Integer.parseInt(request.getParameter("id"));
                campanha.setId(idCampanha);
                dao.removerCampanha(campanha);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectCampanha(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            CampanhaDao dao = new CampanhaDao();
            bean.Campanha campanha = new Campanha();         
            try {
                int idCampanha = Integer.parseInt(request.getParameter("id"));
                campanha.setId(idCampanha);
                campanha.setDescricao(request.getParameter("descricaoEdit")); 
                campanha.setTema(request.getParameter("temaEdit"));
                campanha.setDataConclusao(request.getParameter("dataConclusaoEdit"));
                dao.atualizarCampanha(campanha);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectCampanha(request,response);
        
        } else if (action.equals("salvar")){           
        
            CampanhaDao dao = new CampanhaDao();
            bean.Campanha campanha = new Campanha();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();               
            try {
                campanha.setDescricao(request.getParameter("descricao")); 
                campanha.setTema(request.getParameter("tema"));
                campanha.setDataCadastro(formatoData.format(data));
                campanha.setDataConclusao(request.getParameter("dataConclusao"));
                dao.inserirCampanha(campanha);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CampanhaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectCampanha(request,response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    
    }

    @Override
    public String getServletInfo() {
        return "Controle da classe Campanha";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {  
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
    }   
    
       protected void redirectCampanha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
            int page = 1;
            int recordsPerPage = 3;
            if(request.getParameter("page") != null)
                    page = Integer.parseInt(request.getParameter("page"));
            CampanhaDao dao = new CampanhaDao();
            List listaCampanhas = dao.selecionarCampanhas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstCampanhas",listaCampanhas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("campanhas.jsp");
            dispatcher.forward(request, response);            
       }
    

}

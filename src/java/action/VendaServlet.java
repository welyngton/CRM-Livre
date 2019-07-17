package action;

import bean.Venda;
import dao.VendaDao;
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

public class VendaServlet extends HttpServlet {
    
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
            VendaDao dao = new VendaDao();
            List listaVendas = dao.selecionarVendas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstVendas",listaVendas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("vendas.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           VendaDao dao = new VendaDao();
           if(request.getParameter("id") != null) {
            int idVenda = Integer.parseInt(request.getParameter("id"));
            List vendaList = dao.obterVendaPorId(idVenda);
            Venda vendaEdit = (Venda)vendaList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Venda</H2>");

            response.getWriter().println("<form class=\"form\" action=\"VendaServlet?action=salvarEdicao&id="+idVenda+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Assunto:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"assuntoEdit\" id=\"assuntoEdit\" value=\"" + "" + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + "" + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" +""+ "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Criador:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"criadorEdit\" id=\"criadorEdit\" value=\"" + "" + "\" required disabled/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarVenda\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id de Venda.");  
        } else if (action.equals("remover")){
                       
            VendaDao dao = new VendaDao();
            bean.Venda venda = new Venda();
            try {
                int idVenda = Integer.parseInt(request.getParameter("id"));
                venda.setId(idVenda);
                dao.removerVenda(venda);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectVenda(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            VendaDao dao = new VendaDao();
            bean.Venda venda = new Venda();
            try {
                int idVenda = Integer.parseInt(request.getParameter("id"));
                venda.setId(idVenda);
  //              venda.setDescricao(request.getParameter("descricaoEdit")); 
    //            venda.setAssunto(request.getParameter("assuntoEdit"));
      //          venda.setDataCadastro(request.getParameter("dataCadastroEdit"));
        //        venda.setCriador(request.getParameter("criadorEdit"));
                dao.atualizarVenda(venda);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectVenda(request,response);
        
        } else if (action.equals("salvar")){           
        
            VendaDao dao = new VendaDao();
            bean.Venda venda = new Venda();
            try {
//                venda.setDescricao(request.getParameter("descricao")); 
  //              venda.setAssunto(request.getParameter("assunto"));
    //            venda.setDataCadastro(request.getParameter("dataCadastro"));
      //          venda.setCriador(request.getParameter("criador"));
                dao.inserirVenda(venda);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(VendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectVenda(request,response);
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
        return "Controle da classe Venda";
    }
    
    @Override
    public void init() throws ServletException {  
        super.init(); 
    }   
    
       protected void redirectVenda(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
            int page = 1;
            int recordsPerPage = 3;
            if(request.getParameter("page") != null)
                    page = Integer.parseInt(request.getParameter("page"));
            VendaDao dao = new VendaDao();
            List listaVendas = dao.selecionarVendas((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstVendas",listaVendas.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("vendas.jsp");
            dispatcher.forward(request, response);            
       }
    

}

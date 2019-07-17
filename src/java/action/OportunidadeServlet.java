package action;

import bean.Oportunidade;
import dao.OportunidadeDao;
import java.io.IOException;
import java.io.PrintWriter;
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

public class OportunidadeServlet extends HttpServlet {

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
            OportunidadeDao dao = new OportunidadeDao();
            List listaOportunidades = dao.selecionarOportunidades((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstOportunidades",listaOportunidades.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("oportunidades.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           OportunidadeDao dao = new OportunidadeDao();
           if(request.getParameter("id") != null) {
            int idOportunidade = Integer.parseInt(request.getParameter("id"));
            List oportunidadeList = dao.obterOportunidadePorId(idOportunidade);
            Oportunidade oportunidadeEdit = (Oportunidade)oportunidadeList.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Oportunidade</H2>");

            response.getWriter().println("<form class=\"form\" action=\"OportunidadeServlet?action=salvarEdicao&id="+idOportunidade+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Titulo:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"tituloEdit\" id=\"tituloEdit\" value=\"" + oportunidadeEdit.getTitulo() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + oportunidadeEdit.getDescricao() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" + oportunidadeEdit.getDataCadastro() + "\" disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Fechamento:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataFechamentoEdit\" id=\"dataFechamentoEdit\" value=\"" + oportunidadeEdit.getDataFechamento()+ "\" required/>");
            response.getWriter().println("</div>");
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Valor (R$):</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"valorEdit\" id=\"valorEdit\" value=\"" + oportunidadeEdit.getValor() + "\" required/>");
            response.getWriter().println("</div>"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("Fechar Venda Ganha: <a class=\"coinsLink\" href=\"VendaServlet?action=fecharOportunidade&id=" + idOportunidade + "\" onclick=\"return confirmarFechamentoVenda();\" ><img class=\"coinsButton\" src=\"images/icons/coins_add.png\" alt=\"Fechar\"/></a>");
            response.getWriter().println("<BR\">");
            response.getWriter().println("Fechar Venda Perdida:<a class=\"coinsLink\" href=\"VendaServlet?action=fecharOportunidade&id=" + idOportunidade + "\" onclick=\"return confirmarFechamentoPerda();\" ><img class=\"coinsButton\" src=\"images/icons/coins_delete.png\" alt=\"Fechar\"/></a>");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarOportunidade\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id de Oportunidade.");  
        } else if (action.equals("remover")){
                       
            OportunidadeDao dao = new OportunidadeDao();
            bean.Oportunidade oportunidade = new Oportunidade();
            try {
                int idOportunidade = Integer.parseInt(request.getParameter("id"));
                oportunidade.setId(idOportunidade);
                dao.removerOportunidade(oportunidade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectOportunidade(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            OportunidadeDao dao = new OportunidadeDao();
            bean.Oportunidade oportunidade = new Oportunidade();
            try {
                int idOportunidade = Integer.parseInt(request.getParameter("id"));
                oportunidade.setId(idOportunidade);
                oportunidade.setTitulo(request.getParameter("tituloEdit"));
                oportunidade.setDescricao(request.getParameter("descricaoEdit"));                 
                //oportunidade.setDataCadastro(request.getParameter("dataCadastroEdit"));
                oportunidade.setValor(request.getParameter("valorEdit"));
                oportunidade.setDataFechamento(request.getParameter("dataFechamentoEdit"));
                dao.atualizarOportunidade(oportunidade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectOportunidade(request,response);
        
        } else if (action.equals("salvar")){           
        
            OportunidadeDao dao = new OportunidadeDao();
            bean.Oportunidade oportunidade = new Oportunidade();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();            
            try {
                oportunidade.setTitulo(request.getParameter("titulo"));
                oportunidade.setDescricao(request.getParameter("descricao"));                 
                oportunidade.setDataCadastro(formatoData.format(data));
                oportunidade.setDataFechamento(request.getParameter("dataFechamento"));
                oportunidade.setValor(request.getParameter("valor").substring(3));
                dao.inserirOportunidade(oportunidade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(OportunidadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectOportunidade(request,response);
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
        return "Servlet de Controle de Oportunidades";
    }

    protected void redirectOportunidade(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 3;
        if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
        OportunidadeDao dao = new OportunidadeDao();
        List listaOportunidades = dao.selecionarOportunidades((page-1)*recordsPerPage,recordsPerPage);            
        int noOfRecords = dao.getQtdRegistros();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("lstOportunidades",listaOportunidades.toArray());
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("oportunidades.jsp");
        dispatcher.forward(request, response);            
    }  
}

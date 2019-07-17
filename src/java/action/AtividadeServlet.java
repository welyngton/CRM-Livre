
package action;

import bean.Atividade;
import bean.Usuario;
import dao.AtividadeDao;
import dao.UsuarioDao;
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

public class AtividadeServlet extends HttpServlet {

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
            AtividadeDao dao = new AtividadeDao();
            List listaAtividades = dao.selecionarAtividades((page-1)*recordsPerPage,recordsPerPage);            
            int noOfRecords = dao.getQtdRegistros();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("lstAtividades",listaAtividades.toArray());
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher dispatcher = request.getRequestDispatcher("atividades.jsp");
            dispatcher.forward(request, response);            
        } else if (action.equals("editar")){
           AtividadeDao dao = new AtividadeDao();
           if(request.getParameter("id") != null) {
            int idAtividade = Integer.parseInt(request.getParameter("id"));
            List listaAtividades = dao.obterAtividadePorId(idAtividade);
            Atividade atividadeEdit = (Atividade)listaAtividades.get(0);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<H2>Editar Atividade</H2>");

            response.getWriter().println("<form class=\"form\" action=\"AtividadeServlet?action=salvarEdicao&id="+atividadeEdit+"\" method=\"POST\">"); 
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Tipo:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"tipoEdit\" id=\"tipoEdit\" value=\"" + atividadeEdit.getTipo()+ "\" required />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Assunto:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"assuntoEdit\" id=\"assuntoEdit\" value=\"" + atividadeEdit.getAssunto() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Descricao:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"descricaoEdit\" id=\"descricaoEdit\" value=\"" + atividadeEdit.getDescricao() + "\" required />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Cadastro:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataCadastroEdit\" id=\"dataCadastroEdit\" value=\"" + atividadeEdit.getDataCadastro() + "\" required disabled />");
            response.getWriter().println("</div>");

            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Data Atividade:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataAtividadeEdit\" id=\"dataAtividadeEdit\" value=\"" + atividadeEdit.getDataAtividade()+ "\" required disabled/>");
            response.getWriter().println("</div>");       
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<label>Criador:</label>");
            response.getWriter().println("<input type=\"text\" class=\"form_input\" name=\"dataAtividadeEdit\" id=\"dataAtividadeEdit\" value=\"" + atividadeEdit.getUsuario().getNome()+ "\" required disabled/>");
            response.getWriter().println("</div>");  
            
            response.getWriter().println("<div class=\"form_row\">");
            response.getWriter().println("<input type=\"submit\" class=\"form_submit\" name=\"salvarAtividade\" value=\"Salvar\" />");
            response.getWriter().println("</div>");
            
            response.getWriter().println("</form>"); 
           } else
               response.getWriter().println("Erro ao obter Id de Atividade.");  
        } else if (action.equals("remover")){
                       
            AtividadeDao dao = new AtividadeDao();
            bean.Atividade atividade = new Atividade();
            try {
                int idAtividade = Integer.parseInt(request.getParameter("id"));
                atividade.setId(idAtividade);
                dao.removerAtividade(atividade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAtividade(request,response);
            
        } else if (action.equals("salvarEdicao")) {
            
            AtividadeDao dao = new AtividadeDao();
            bean.Atividade atividade = new Atividade();
            try {
                int idAtividade= Integer.parseInt(request.getParameter("id"));

                atividade.setId(idAtividade);
                atividade.setTipo(request.getParameter("tipoAtividade"));
                atividade.setDescricao(request.getParameter("descricao")); 
                atividade.setAssunto(request.getParameter("assunto"));
                atividade.setDataCadastro(request.getParameter("dataCadastro"));
                atividade.setDataAtividade(request.getParameter("dataAtividade"));
                dao.atualizarAtividade(atividade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAtividade(request,response);
        
        } else if (action.equals("salvar")){           
            UsuarioDao uDao = new UsuarioDao();
            int idUsuario = Integer.parseInt((String) request.getSession().getAttribute("idUsrSession"));
            System.out.println("id usr session: "+ idUsuario);
            Usuario usuario = (Usuario) uDao.obterUsuarioPorId(idUsuario).get(0);
            AtividadeDao dao = new AtividadeDao();
            bean.Atividade atividade = new Atividade();
            DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();    
            try {
                atividade.setUsuario(usuario);
                atividade.setTipo(request.getParameter("tipoAtividade"));
                atividade.setDescricao(request.getParameter("descricao")); 
                atividade.setAssunto(request.getParameter("assunto"));
                atividade.setDataCadastro(formatoData.format(data));
                atividade.setDataAtividade(request.getParameter("dataAtividade"));
                dao.inserirAtividade(atividade);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(AtividadeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAtividade(request,response);
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
        return "Servlet de controle de atividade";
    }
    
    protected void redirectAtividade(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 3;
        if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));
        AtividadeDao dao = new AtividadeDao();
        List listaAtividades = dao.selecionarAtividades((page-1)*recordsPerPage,recordsPerPage);            
        int noOfRecords = dao.getQtdRegistros();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("lstAtividades",listaAtividades.toArray());
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("atividades.jsp");
        dispatcher.forward(request, response);            
    }

}

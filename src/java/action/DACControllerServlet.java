package action;

import dao.AtividadeDao;
import dao.CampanhaDao;
import dao.ContaDao;
import dao.OportunidadeDao;
import dao.VendaDao;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DACControllerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, JRException, ParserConfigurationException, SAXException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if((action != null) && (!action.isEmpty())) {
        if(action.equals("pesquisarDashboard")) { 
            carregarGlobalWebService(request);
            redirectDashboard(request, response);
        }
        else if(action.equals("relatorioOportunidades")) {
            Connection con =null;
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/daccrm?characterEncoding=UTF-8","root","admin");            
                String jasper = request.getContextPath() +"/CACCRM_A4.jasper";
                String host ="http://"+ request.getServerName() +":"+ request.getServerPort();
                URL jasperURL = new URL(host + jasper);
                HashMap params =new HashMap();
                byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params,con);
                if (bytes !=null) {
                response.setContentType("application/pdf");
                OutputStream ops = null;
                ops = response.getOutputStream();
                ops.write(bytes); 
                }
            }
            finally{
                if(con!=null)
                try{ con.close(); }
                catch (Exception e) {}
            }        
        }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | JRException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DACControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | JRException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(DACControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public String getServletInfo() {
        return "Controle do sistema do DAC CRM";
    }

    @Override
    public void init() throws ServletException {  
        super.init();
        
    }   
    
    protected void redirectDashboard(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int page = 1;
        //Paginas por registro são diferentes no
        int recordsPerPage = 3;
        
        AtividadeDao atividaDao = new AtividadeDao();
        ContaDao contaDao = new ContaDao();
        OportunidadeDao oportunidadeDao = new OportunidadeDao();
        CampanhaDao campanhaDao = new CampanhaDao();
        VendaDao vendaDao = new VendaDao();         
        
        int pageAtividades = 1;
        if(request.getParameter("pageAtividades") != null)
            pageAtividades = Integer.parseInt(request.getParameter("pageAtividades"));
        int noOfRecordsAtividades = atividaDao.getQtdRegistros();
        int noOfPagesAtividades = (int) Math.ceil(noOfRecordsAtividades * 1.0 / noOfRecordsAtividades);
        
        int pageContas = 1;
        if(request.getParameter("pageContas") != null)
            pageContas = Integer.parseInt(request.getParameter("pageContas"));        
        int noOfRecordsContas = contaDao.getQtdRegistros();
        int noOfPagesContas = (int) Math.ceil(noOfRecordsContas * 1.0 / noOfRecordsContas);
        
        int pageOportunidades = 1;
        if(request.getParameter("pageOportunidades") != null)
            pageOportunidades = Integer.parseInt(request.getParameter("pageOportunidades"));        
        int noOfRecordsOportunidades = oportunidadeDao.getQtdRegistros();
        int noOfPagesOportunidades = (int) Math.ceil(noOfRecordsOportunidades * 1.0 / noOfRecordsOportunidades);
        
        int pageCampanhas = 1;
        if(request.getParameter("pageCampanhas") != null)
            pageCampanhas = Integer.parseInt(request.getParameter("pageCampanhas"));        
        int noOfRecordsCampanhas = campanhaDao.getQtdRegistros();
        int noOfPagesCampanhas = (int) Math.ceil(noOfRecordsCampanhas * 1.0 / noOfRecordsCampanhas);
        
        int pageVendas = 1;
        if(request.getParameter("pageVendas") != null)
            pageVendas = Integer.parseInt(request.getParameter("pageVendas"));        
        //int noOfRecordsVendas = vendaDao.getQtdRegistros();
        //int noOfPagesVendas = (int) Math.ceil(noOfRecordsVendas * 1.0 / noOfRecordsVendas); 
        
        List listaAtividades = atividaDao.selecionarAtividades((pageAtividades-1)*recordsPerPage,recordsPerPage);            
        List listaContas = contaDao.selecionarContas((pageContas-1)*recordsPerPage,recordsPerPage);
        List listaOportunidades = oportunidadeDao.selecionarOportunidades((pageOportunidades-1)*recordsPerPage,recordsPerPage);
        List listaCampanhas = campanhaDao.selecionarCampanhas((pageCampanhas-1)*recordsPerPage,recordsPerPage);
        //List listaVendas = vendaDao.selecionarVendas((pageVendas-1)*recordsPerPage,recordsPerPage);
        
        noOfRecordsAtividades = atividaDao.getQtdRegistros();
        noOfPagesAtividades = (int) Math.ceil(noOfRecordsAtividades * 1.0 / recordsPerPage);
        noOfRecordsContas = contaDao.getQtdRegistros();
        noOfPagesContas = (int) Math.ceil(noOfRecordsContas * 1.0 / recordsPerPage);
        noOfRecordsOportunidades = oportunidadeDao.getQtdRegistros();
        noOfPagesOportunidades = (int) Math.ceil(noOfRecordsOportunidades * 1.0 / recordsPerPage);
        noOfRecordsCampanhas = campanhaDao.getQtdRegistros();
        noOfPagesCampanhas = (int) Math.ceil(noOfRecordsCampanhas * 1.0 / recordsPerPage);
        //noOfRecordsVendas = vendaDao.getQtdRegistros();
        //noOfPagesVendas = (int) Math.ceil(noOfRecordsVendas * 1.0 / noOfRecordsVendas);                 
        
        request.setAttribute("lstAtividades",listaAtividades.toArray());
        request.setAttribute("lstContas",listaContas.toArray());
        request.setAttribute("lstOportunidades",listaOportunidades.toArray());
        request.setAttribute("lstCampanhas",listaCampanhas.toArray());
        //request.setAttribute("lstVendas",listaVendas.toArray());
        
        request.setAttribute("noOfPagesAtividades", noOfPagesAtividades);
        request.setAttribute("currentPageAtividades", pageAtividades);
        request.setAttribute("noOfPagesContas", noOfPagesContas);
        request.setAttribute("currentPageContas", pageContas);
        request.setAttribute("noOfPagesOportunidades", noOfPagesOportunidades);
        request.setAttribute("currentPageOportunidades", pageOportunidades);
        request.setAttribute("noOfPagesCampanhas", noOfPagesCampanhas);
        request.setAttribute("currentPageCampanhas", pageCampanhas);
        //request.setAttribute("noOfPages", noOfPagesVendas);
        //request.setAttribute("currentPageVendas", pageVendas);
        
        //request.setAttribute("currentPage", page);        
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);            
    }    
    
        private void carregarGlobalWebService(HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException {
        GlobalWeather global = null;
        String temperaturaWS = "";
        String gwStringXML;        
        String cidade = "Londrina";
        try {
        global = new GlobalWeather();   
        gwStringXML = global.getGlobalWeatherSoap().getWeather(cidade, "Brazil");   
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         InputSource is = new InputSource();
         is.setCharacterStream(new StringReader(gwStringXML));
         Document doc = db.parse(is);
         System.out.println(gwStringXML);
         NodeList location = doc.getElementsByTagName("Location");
         Element line = (Element) location.item(0);
         temperaturaWS = getCharacterDataFromElement(line).split(",")[0]+","; 
         NodeList relativeHumidity = doc.getElementsByTagName("RelativeHumidity");
         line = (Element) relativeHumidity.item(0);
         temperaturaWS += getCharacterDataFromElement(line)+",";
         NodeList wind = doc.getElementsByTagName("Wind");
         line = (Element) wind.item(0);
         temperaturaWS += getCharacterDataFromElement(line)+",";
         NodeList temperature = doc.getElementsByTagName("Temperature");
         line = (Element) temperature.item(0);
         temperaturaWS += getCharacterDataFromElement(line);       
        }
        catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ex.getMessage());
        }
         request.setAttribute("temperaturaWS", temperaturaWS);
    }
    
   public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }
}

package action;

import bean.Usuario;
import dao.LoginDao;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LoginServlet extends HttpServlet {

    private String contextPath;
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SAXException, ParserConfigurationException {
        response.setContentType("text/html;charset=UTF-8");
        LoginDao dao = null;
        String login = null;
        String senha = null;
        login = request.getParameter("usuario");
        senha = request.getParameter("senha");
        if(request.getParameter("action").equals("logar")) {
            dao = new LoginDao();
            Usuario usuarioLogin = dao.logar(login, senha);
            HttpSession session = request.getSession(true);
            if(usuarioLogin != null) {                            
                 if (session != null) { 
                    session = request.getSession();
                    session.setAttribute("idUsrSession",String.valueOf(usuarioLogin.getId()));
                    session.setAttribute("logadoUsrSession","true");
                    Cookie cookieLogin = new Cookie("loginUsuarioCookie",usuarioLogin.getLogin());
                    session.setAttribute("tipoUsrSession",usuarioLogin.getTipo());
                    Cookie cookieTipo = new Cookie("tipoUsuarioCookie",usuarioLogin.getTipo());
                    session.setAttribute("nomeUsrSession",usuarioLogin.getNome());
                    Cookie cookieNome = new Cookie("nomeUsuarioCookie",usuarioLogin.getNome());   
                    response.addCookie(cookieLogin);
                    response.addCookie(cookieTipo);
                    response.addCookie(cookieNome);
                    if(usuarioLogin.getTipo().equals("admin")) {
                        carregarDashboard(request); 
                        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
                        dispatcher.forward(request, response); 
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("AtividadeServlet?action=pesquisar");
                        dispatcher.forward(request, response);                     
                    }
                 }
                }
                 else {                     
                    session.setAttribute("logado","false");
                    response.sendRedirect("login.jsp");
                }
            }
            else if(request.getParameter("action").equals("deslogar")) {    
                HttpSession session = request.getSession(true);                           
                if (session != null) { 
                    session = request.getSession();
                    session.setAttribute("idUsrSession",null);
                    session.setAttribute("logadoUsrSession",null);
                    session.setAttribute("tipoUsrSession",null);
                    session.setAttribute("nomeUsrSession",null);
                }
                Cookie cookie = null;
                Cookie[] cookies = null;
                cookies = request.getCookies();
                if( cookies != null ){
                   for (int i = 0; i < cookies.length; i++){
                      cookie = cookies[i];
                      if((cookie.getName( )).compareTo("loginUsuarioCookie") == 0 ){
                         cookie.setMaxAge(0);
                         response.addCookie(cookie);
                      } else if((cookie.getName( )).compareTo("tipoUsuarioCookie") == 0 ){
                         cookie.setMaxAge(0);
                         response.addCookie(cookie);
                      } else if((cookie.getName( )).compareTo("nomeUsuarioCookie") == 0 ){
                         cookie.setMaxAge(0);
                         response.addCookie(cookie);
                      }
                   }
                }
                response.sendRedirect("login.jsp");
            }
            else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException | ParserConfigurationException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException | ParserConfigurationException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void carregarDashboard(HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException {
        GlobalWeather global = null;
        String temperaturaWS = "";
        String gwStringXML;        
        String cidade = "Curitiba";
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

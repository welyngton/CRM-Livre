/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import dao.LoginDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
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

/**
 *
 * @author W
 */
public class LoginFilter implements Filter{

    private String contextPath;
    private ArrayList urlList;
   
    public String getServletInfo() {
        return "Filtragem de acesso";
    }// </editor-fold>

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();   
        String urls = filterConfig.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }   
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;  
        boolean logado = false;
        String strURL = "";
        // To check if the url can be excluded or not
        for (int i = 0; i < urlList.size(); i++) {
            strURL = urlList.get(i).toString();
            if (strURL.startsWith(strURL)) {
                logado = true;
            }
        }
        Cookie[] cookies = req.getCookies();
        
        System.out.println("doFilter;");
        String userId = null;
        for(Cookie cookie : cookies){
            if("login".equals(cookie.getName())){
                System.out.println("cookie = logado");
                logado = true;
            }
        }
        if (req.getSession().getAttribute("login") != null) {
            logado = true;
            System.out.println("cookie = logado session");
        }
        if(logado) {
            System.out.println("doFilter = logado");
            //RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
            //dispatcher.forward(request, response);   
        } else {
            System.out.println("doFilter nao logado");
            res.sendRedirect("login.jsp");  
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}

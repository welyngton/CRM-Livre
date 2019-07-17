<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${(tipoUsrSession eq null)}">
    <c:redirect url="login.jsp" />
</c:if>
<jsp:include page="jspf/header.jspf" />
                   
    <div class="center_content">  
 
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Configurações</h2>        

    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Geral</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Configurações do sistema</h3>
        
            <form class="form" action="DACControllerServlet?action=salvar" method="POST">    
                
                <div class="form_row">
                <label>Máximo Páginas Pesquisa: </label>
                <input class="form_input" name="maxPages" id="maxPages" value="3" />
                </div>   

                <div class="form_row">
                <label>Máximo Páginas Dashboard: </label>
                <input class="form_input" name="maxPagesDashboard" id="maxPagesDashboard" value="3" />
                </div>  
                
                <div class="form_row">
                <label>Cidade Atual: </label>
                <input class="form_input" name="cidadeAtual" id="cidadeAtual" value="Curitiba" />
                </div>              
                
                <div class="form_row">
                    <input type="submit" class="form_submit" name="salvarConfiguracoes" value="Buscar" />
                </div>   
                
            </form>
            <div class="clear"></div>
    </div>    
    <div class="clear"></div>
    </div>
    </div>        
    </div>
                                              
 <jsp:include page="jspf/sidebar.jspf" />           
    
<jsp:include page="jspf/footer.jspf" />
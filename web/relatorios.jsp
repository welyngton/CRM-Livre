<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${(tipoUsrSession eq null) or (tipoUsrSession eq 'usr')}">
    <c:redirect url="login.jsp" />
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="jspf/header.jspf" />

<script type="text/javascript">
    $(document).ready(function() {
        $("#data").datepicker();
        $("#data").datepicker('setDate', 'today');
    });
</script>
                   
    <div class="center_content">  
 
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Relatórios</h2>      
    
    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Relatórios</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Relatórios PDF</h3>

            <form class="form" action="AnotacaoServlet?action=salvar" method="POST">           

            <div class="form_row">
            <img src="images/icons/table_multiple.png" alt="" title="" border="0" /> 
            <a href="DACControllerServlet?action=relatorioOportunidades">Relatório de Oportunidades<a/>
            </div>              
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="pesquisarAjuda" value="Buscar" />
            </div>           
        </form>
        <div class="clear"></div>
    </div>
        <div class="clear"></div>      
     </div>
     </div>                   
                    
 <jsp:include page="jspf/sidebar.jspf" />           

<jsp:include page="jspf/footer.jspf" />
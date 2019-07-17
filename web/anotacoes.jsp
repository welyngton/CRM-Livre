<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${(tipoUsrSession eq null)}">
    <c:redirect url="login.jsp" />
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="jspf/header.jspf" />

<script type="text/javascript">   
    
    xmlhttp.onreadystatechange = function(){
       if(xmlhttp.readyState == 4 && xmlhttp.status == 200 ){
           document.getElementById("modal2").innerHTML = xmlhttp.responseText;
       } 
       else{
            return false;
       }
    };
    
    $(document).ready(function() {
        $("#dataCadastro").datepicker();
        $("#dataCadastro").datepicker('setDate', 'today');
    });
       
    $(function() {
        $( "#modal2" ).dialog({
             autoOpen: false,
             height: 460,
             width: 700,
             modal: true
         });
         $( ".modalLink" )
            .button()
            .click(function(e) {
            //antes de chamar a modal carregar os dados do BD nela.                     
            var idEdit = $(this).attr("param");        
            $("#idItemEdit").val(idEdit);
            xmlhttp.open("POST","AnotacaoServlet?action=editar&id="+idEdit, true);
            xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xmlhttp.send(null);
            $( "#modal2" ).dialog( "open" );
        });
    });      
</script>  

    <div class="center_content">  
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Anotações</h2>        
    <div id="tabs">
    <ul id="tabsmenu" class="tabsmenu">
        <li id="li1" class="active" ><a href="#tabs-1">Anotações</a></li>
        <li id="li2" ><a id="li2a" href="#tabs-2">Cadastro</a></li>
    </ul>
    <div id="tabs-1" class="tabcontent">
        <h3>Listagem</h3>
        
    <table id="rounded-corner">
    <thead>
        <tr>
            <th>Assunto</th>
            <th>Descricao</th>
            <th>Data</th>
            <th>Ações</th>            
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${lstAnotacoes}">
            <tr class="${(item.id % 2 == 0) ? "even" : "odd" }" >
                <td><c:out value="${item.assunto}" /></td>
                <td><c:out value="${item.descricao}" /></td>
                <td><c:out value="${item.dataCadastro}" /></td>
                <td><img src="images/icons/pencil.png" alt="Editar" param="${item.id}" id="modalLink" class="modalLink"/>
                <a class="removerLink" href="AnotacaoServlet?action=remover&id=${item.id}" onclick="return confirmarRemover();"><img src="images/icons/bin.png" alt="Excluir"/></a></td>
            </tr>
        </c:forEach>
    </tbody>
    </table>
        <table id="page-table"> 
            <tr>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
			<td><a href="AnotacaoServlet?action=pesquisar&page=${i}">${i}</a></td>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
		</tr>
                <tr>
                    <td>
                    <c:if test="${currentPage != 1}">
                        <a href="AnotacaoServlet?action=pesquisar&page=${currentPage - 1}">
                        <img src="images/icons/resultset_previous.png" />
                        </a>
                    </c:if>
                <c:if test="${currentPage lt noOfPages}">
                    <a class="pagLink" href="AnotacaoServlet?action=pesquisar&page=${currentPage + 1}">
                    <img src="images/icons/resultset_next.png" /></a>
                </c:if>
              </td>
            </tr>
	</table>       

    <input id="idItemEdit" type="hidden" value="0" />
    </div>
    <div id="tabs-2" class="tabcontent">
        <h3>Cadastro</h3>
        <form class="form" action="AnotacaoServlet?action=salvar" method="POST">           
            
            <div class="form_row">
            <label>Assunto:</label>
            <input type="text" class="form_input" name="assunto" id="assunto" required />
            </div>                                     
            
            <div class="form_row">
            <label>Descrição:</label>
            <textarea class="form_textarea" name="descricao" id="descricao"></textarea>
            </div>  
            
            <div class="form_row">
            <label>Data Cadastro:</label>
            <input class="form_input" name="dataCadastro" id="dataCadastro" />
            </div> 
  
            <div class="form_row">
            <label>Criador: </label>
            <input class="form_input" name="criador" id="criador" />
            </div>              
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="salvarAnotacao" value="Salvar" />
            </div>           
        </form>
        <div class="clear"></div>
    </div>
         <div class="toogle_wrap">
            <div class="trigger"><a href="#">Expandir</a></div>

            <div class="toggle_container">
			<p>
        Relatórios</p>
            </div>
        </div>
      
     </div>
     </div>
     </div>
            
     <div id="modal2" title="Edição">                            
     </div>        
            
 <jsp:include page="jspf/sidebar.jspf" />           
    
<jsp:include page="jspf/footer.jspf" />
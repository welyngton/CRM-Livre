<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${(tipoUsrSession eq null)}">
    <c:redirect url="login.jsp" />
</c:if>
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
        $("#dataConclusao").datepicker();
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
        xmlhttp.open("POST","TarefaServlet?action=editar&id="+idEdit, true);
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlhttp.send(null);
        $( "#modal2" ).dialog( "open" );
        });
    });
    
</script>
                   
    <div class="center_content">  
 
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Tarefas</h2>        
    
    <ul id="tabsmenu" class="tabsmenu">
  <li class="active"><a href="#tab1">Tarefas</a></li>
        <li ><a href="#tab2">Cadastro</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Tarefas</h3>
<p>
    Listagem</p>
    
    <c:if test="${lstTarefas != null}">
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
        <c:forEach var="item" items="${lstTarefas}">
            <tr class="${(item.id % 2 == 0) ? "even" : "odd" }" >
                <td><c:out value="${item.assunto}" /></td>
                <td><c:out value="${item.descricao}" /></td>
                <td><c:out value="${item.dataConclusao}" /></td>
                <td><img src="images/icons/pencil.png" alt="Editar" param="${item.id}" id="modalLink" class="modalLink" />
                <a class="removerLink" href="TarefaServlet?action=remover&id=${item.id}" onclick="return confirmarRemover();"><img src="images/icons/bin.png" alt="Excluir"/></a>
                </td>
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
			<td><a href="TarefaServlet?action=pesquisar&page=${i}">${i}</a></td>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
		</tr>
                <tr>
                    <td>
                    <c:if test="${currentPage != 1}">
                        <a href="TarefaServlet?action=pesquisar&page=${currentPage - 1}">
                        <img src="images/icons/resultset_previous.png" />
                        </a>
                    </c:if>
                <c:if test="${currentPage lt noOfPages}">
                    <a class="pagLink" href="TarefaServlet?action=pesquisar&page=${currentPage + 1}">
                    <img src="images/icons/resultset_next.png" /></a>
                </c:if>
              </td>
            </tr>
	</table>
    </c:if>                       
    </div>
    <div id="tab2" class="tabcontent">
        <h3>Cadastro</h3>
        <form class="tarefaForm" action="TarefaServlet?action=salvar" method="POST">
            
            <div class="form_row">
            <label>Tipo Tarefa:</label>
            <input type="radio" class="" name="tipoTarefa" value="Telefonema"/>
            <span class="radio_text">Telefonema</span>
            <input type="radio" class="" name="tipoTarefa" value="E-mail"/> 
            <span class="radio_text">E-mail</span>      
            <input type="radio" class="" name="tipoTarefa" value="Reunião"/>
            <span class="radio_text">Reunião</span>
            <input type="radio" class="" name="tipoTarefa" value="Compromisso"/>
            <span class="radio_text">Compromisso</span>
            <input type="radio" class="" name="tipoTarefa" value="Outro"/>
            <span class="radio_text">Outro</span>
            </div>
            
            
            <div class="form_row">
            <label>Assunto:</label>
            <input type="text" class="form_input" name="assunto" id="assunto" required/>
            </div>                                     
            
            <div class="form_row">
            <label>Descrição:</label>
            <textarea class="form_textarea" name="descricao" id="descricao"></textarea>
            </div>                               
  
            <div class="form_row">
            <label>Data Cadastro:</label>
            <input type="text" class="form_input" name="dataCadastro" id="dataCadastro" disabled="true" />
            </div>   
            
            <div class="form_row">
            <label>Data Conclusão:</label>
            <input type="text" class="form_input" name="dataConclusao" id="dataConclusao" />
            </div> 
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="salvarTarefa" value="Salvar" />
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
     </div><!-- end of right content-->
            
     <div id="modal2" title="Edição">                            
     </div>        
                                           
 <jsp:include page="jspf/sidebar.jspf" />             

<jsp:include page="jspf/footer.jspf" />
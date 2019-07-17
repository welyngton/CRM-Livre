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
        $("#dataAtividade").datepicker();
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
        var idEdit = $(this).attr("param");        
        $("#idItemEdit").val(idEdit);
        xmlhttp.open("POST","AtividadeServlet?action=editar&id="+idEdit, true);
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlhttp.send(null);
        $( "#modal2" ).dialog( "open" );
        });
    });
</script>
                   
    <div class="center_content">  
 
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Atividades</h2>        
    
    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Atividades</a></li>
        <li ><a href="#tab2">Cadastro</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Atividades</h3>
<p>
    Listagem</p>

<table id="rounded-corner">
    <thead>
        <tr>
            <th>Tipo</th>
            <th>Assunto</th>
            <th>Descricao</th>
            <th>Data</th>
            <th>Ações</th>            
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${lstAtividades}">
            <tr class="${(item.id % 2 == 0) ? "even" : "odd" }" >          
                <td><c:out value="${item.tipo}" /></td>
                <td><c:out value="${item.assunto}" /></td>
                <td><c:out value="${item.descricao}" /></td>
                <td><c:out value="${item.dataAtividade}" /></td>
                <td><img src="images/icons/pencil.png" param="${item.id}" alt="Editar"  id="modalLink" class="modalLink"/>
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
			<td><a href="AtividadeServlet?action=pesquisar&page=${i}">${i}</a></td>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
		</tr>
                <tr>
                    <td>
                    <c:if test="${currentPage != 1}">
                        <a href="AtividadeServlet?action=pesquisar&page=${currentPage - 1}">
                        <img src="images/icons/resultset_previous.png" />
                        </a>
                    </c:if>
                <c:if test="${currentPage lt noOfPages}">
                    <a class="pagLink" href="AtividadeServlet?action=pesquisar&page=${currentPage + 1}">
                    <img src="images/icons/resultset_next.png" /></a>
                </c:if>
              </td>
            </tr>
	</table>
        
    </div>
    <div id="tab2" class="tabcontent">
        <h3>Cadastro</h3>
        <form class="form" action="AtividadeServlet?action=salvar" method="POST">
            
            <div class="form_row">
            <label>Tipo Atividade:</label>
            <input type="radio" class="" name="tipoAtividade" />
            <span class="radio_text">Telefonema</span>
            <input type="radio" class="" name="tipoAtividade" /> 
            <span class="radio_text">E-mail</span>      
            <input type="radio" class="" name="tipoAtividade" />
            <span class="radio_text">Reunião</span>
            <input type="radio" class="" name="tipoAtividade" />
            <span class="radio_text">Compromisso</span>
            <input type="radio" class="" name="tipoAtividade" />
            <span class="radio_text">Outro</span>
            </div>            
            
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
            <input type="text" class="form_input" name="dataCadastro" id="dataCadastro" disabled="true" />
            </div> 
  
            <div class="form_row">
            <label>Data:</label>
            <input type="text" class="form_input" name="dataAtividade" id="dataAtividade" />
            </div>              
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="salvarAtividade" value="Salvar" />
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
     
     <div id="modal2" title="Edição">                            
     </div>                        
     
                    
 <jsp:include page="jspf/sidebar.jspf" />           
    

<jsp:include page="jspf/footer.jspf" />
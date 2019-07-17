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
        $("#dataFechamento").datepicker();
    });
    
    $(function() {
        $('#valor').maskMoney({prefix:'R$ ', allowNegative: false, thousands:'.', decimal:','});
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
            xmlhttp.open("POST","OportunidadeServlet?action=editar&id="+idEdit, true);
            xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xmlhttp.send(null);
            $( "#modal2" ).dialog( "open" );
        });
    });        
</script>
                   
    <div class="center_content">  
 
    <div id="right_wrap">
    <div id="right_content">             
    <h2>Oportunidades</h2>        
    
    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Oportunidades</a></li>
        <li ><a href="#tab2">Cadastro</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Oportunidades</h3>
<p>
    Listagem</p>

<table id="rounded-corner">
    <thead>
        <tr>
            <th>Título</th>
            <th>Descricao</th>
            <th>Valor</th>
            <th>Ações</th>            
       </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${lstOportunidades}">
            <tr class="${(item.id % 2 == 0) ? "even" : "odd" }" >
                <td><c:out value="${item.titulo}" /></td>
                <td><c:out value="${item.descricao}" /></td>
                <td><c:out value="R$ ${item.valor}" /></td>
                <td>
                <a class="" href="VendaServlet?action=fecharOportunidadeGanha&id=${item.id}" onclick="return confirmarFechamentoVenda();" ><img src="images/icons/coins_add.png" alt="Fechar" param="${item.id}"/></a>
                <a class="" href="OportunidadeServlet?action=fecharOportunidadePerdida&id=${item.id}" onclick="return confirmarFechamentoPerda();" ><img src="images/icons/coins_delete.png" alt="Fechar" param="${item.id}"/></a>
                <img src="images/icons/pencil.png" alt="Editar" param="${item.id}" id="modalLink" class="modalLink"/>                
                <a class="" href="OportunidadeServlet?action=remover&id=${item.id}" onclick="return confirmarRemover();"><img src="images/icons/bin.png" alt="Excluir"/></a>
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
			<td><a href="OportunidadeServlet?action=pesquisar&page=${i}">${i}</a></td>
                    </c:otherwise>
                    </c:choose>
                    </c:forEach>
		</tr>
                <tr>
                    <td>
                    <c:if test="${currentPage != 1}">
                        <a href="OportunidadeServlet?action=pesquisar&page=${currentPage - 1}">
                        <img src="images/icons/resultset_previous.png" />
                        </a>
                    </c:if>
                <c:if test="${currentPage lt noOfPages}">
                    <a class="pagLink" href="OportunidadeServlet?action=pesquisar&page=${currentPage + 1}">
                    <img src="images/icons/resultset_next.png" /></a>
                </c:if>
              </td>
            </tr>
	</table>      
        
    </div>
    <div id="tab2" class="tabcontent">
        <h3>Cadastro</h3>
        <form class="form" action="OportunidadeServlet?action=salvar" method="POST">            
            
            <div class="form_row">
            <label>Título: </label>
            <input type="text" class="form_input" name="titulo" id="titulo" required />
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
            <label>Data Fechamento:</label>
            <input type="text" class="form_input" name="dataFechamento" id="dataFechamento" />
            </div>     
            
            <div class="form_row">
            <label>Valor: </label>
            <input type="text" class="form_input" name="valor" id="valor" />
            </div>
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="salvarOportunidade" value="Salvar" />
            </div>           
        </form>
        <div class="clear"></div>
    </div>
         <div class="toogle_wrap">
            <div class="trigger"><a href="#">Expandir</a></div>

            <div class="toggle_container">
            <p>Relatórios</p>
            </div>
        </div>      
     </div>
     </div>
        
     <div id="modal2" title="Edição">                            
     </div>    
                                              
 <jsp:include page="jspf/sidebar.jspf" />           
    
<jsp:include page="jspf/footer.jspf" />
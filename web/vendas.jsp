<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${(tipoUsrSession eq null)}">
    <c:redirect url="login.jsp" />
</c:if>
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
    <h2>Atividades</h2>        
    
    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Atividades</a></li>
        <li ><a href="#tab2">Cadastro</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Atividades</h3>
<p>
    Listagem</p>
    <% 
        //TarefaDao dao = new TarefaDao();
        //ArrayList<Tarefa> listaTarefas = dao.selecionarTarefas();
        
    %>
    <table border=1>
        <c:forEach var="row" items="${listaTarefas}">
        <tr>
        <td><cut value="${row.assunto}"/></td>
        <td><cut value="${row.descricao}"/></td>
        </tr>
        </c:forEach>
    </table>
        
    </div>
    <div id="tab2" class="tabcontent">
        <h3>Cadastro</h3>
        <form class="form" action="TarefaServlet" method="POST">
            
            <div class="form_row">
            <label>Tipo Tarefa:</label>
            <input type="radio" class="" name="tipoTarefa" />
            <span class="radio_text">Telefonema</span>
            <input type="radio" class="" name="tipoTarefa" /> 
            <span class="radio_text">E-mail</span>      
            <input type="radio" class="" name="tipoTarefa" />
            <span class="radio_text">Reunião</span>
            <input type="radio" class="" name="tipoTarefa" />
            <span class="radio_text">Compromisso</span>
            <input type="radio" class="" name="tipoTarefa" />
            <span class="radio_text">Outro</span>
            </div>
            
            
            <div class="form_row">
            <label>Assunto:</label>
            <input type="text" class="form_input" name="assunto" id="assunto"/>
            </div>                                     
            
            <div class="form_row">
            <label>Descrição:</label>
            <textarea class="form_textarea" name="descricao" id="descricao"></textarea>
            </div>                               
  
            <div class="form_row">
            <label>Data:</label>
            <input type="text" class="form_input" name="data" id="data" />
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
                     
     
                    
 <jsp:include page="jspf/sidebar.jspf" />           
    

<jsp:include page="jspf/footer.jspf" />
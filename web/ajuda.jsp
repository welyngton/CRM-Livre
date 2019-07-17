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
    <h2>Ajuda</h2>      
    
    <ul id="tabsmenu" class="tabsmenu">
        <li class="active"><a href="#tab1">Ajuda</a></li>
        <li ><a href="#tab2">Especificação</a></li>
    </ul>
    <div id="tab1" class="tabcontent">
        <h3>Ajuda</h3>

        <form class="form" action="AnotacaoServlet?action=salvar" method="POST">           

            <div class="form_row">
            <label>Termo: </label>
            <input class="form_input" name="termoBusca" id="criador" />
            </div>              
              
            <div class="form_row">
                <input type="submit" class="form_submit" name="pesquisarAjuda" value="Buscar" />
            </div>           
        </form>
        <div class="clear"></div>
    </div>
    <div id="tab2" class="tabcontent">
                        <div class="text">
                O sistema foi criado para o gerenciamento do relacionamento com os clientes:                                
            </div>
        <h3>Especificação</h3>
        <div class="form">
            
                <h4>Especificação do Trabalho de DAC</h4>
                <h5>Objetivo:</h5>

Desenvolver u
m sistema que dê suporte a CRM (
Customer Relationship Management
)
;
O sistema deve conter
, minimamente
:
<ul>
    <li>Interação com Banco de Dados (CRUD e regra de negócio).</li>
    <li>Login com pelo menos dois perfis diferentes</li>
    <li>Web Service</li>
    <li>Relatório</li>
    <li>Linguagem de programação: JAVA</li>
</ul>
Produtos a serem gerados:
<ul>
    <li>
Plano de Gerenci
amento do Projeto</li>
    <li>
Declaração do Escopo do Projeto
    </li>
    <li>
Diagrama de Casos de Uso
    </li>
Especificação dos Casos de Uso
<li>
Diagrama de Classes de Implementação
</li>
Diagrama Físico do Banco de Dados
<li>
Diagrama de Sequência
de Implementação
</li>
<li>
Sistema em execução, de acordo com a doc
umentação gerada
</li>
</ul>
Formato
dos Trabalhos:
<ul>
    <li>
O trabalho de
ve ser realizado em equipes de 2 ou 3
integrantes;
    </li>
    <li>
Deve ser visto como um “mini
-
TCC”;
    </li>
    <li>
As atividades
não
marcadas com um “*”
e negrito
no plano de aulas serão conduzidas
pelos próprios alunos. Se houver
necessidade de presença da professora, deve ser
previamente solicitado;
    </li>
    <li>
As orientações e
subprodutos
a serem entregues estão especificados no plano de aulas da
disciplina;
    </li>
    <li>
Todos os produtos fazem parte das entregas parciais ao longo do semestre, conforme
p
lano de aulas
, estas entregas valem nota
.
    </li>
    <li>
Todos
os produtos
devem ser entregues em um único PDF, pelo Moodle, ao fim do
semestre.
    </li>
</ul>
Responsabilidades:
<ul>
    <li>
Professora
: conduzir e orientar os trabalhos, tirar dúvidas e auxiliar na elaboração do
projeto;   
suporte n
o desenvolvimento dos aplicativos;
</li>
    <li>
Alunos
: definir o escopo do seu trabalho, elaborar todos os produtos solicitados, cuidar dos
prazos e das atividades desenvolvidas, gerenciar as atividades entre o
s membros da
equipe, procurar a professora
sempre que ho
uver
problemas e
para verificações periódicas
do andamento dos trabalhos;
    </li>
</ul>
<h4>
E
-
mail de contato:
</h4>
rafaela dot otemaier at gmail dot com <br />
dalpra at outlook dot com dot br
        </div>
    </div>
        <div class="clear"></div>
         <div class="toogle_wrap">
            <div class="trigger"><a href="#">Expandir</a></div>

            <div class="toggle_container">
			<p>
        Relatórios</p>
            </div>
        </div>
      
     </div>
     </div>                        
                    
 <jsp:include page="jspf/sidebar.jspf" />               

<jsp:include page="jspf/footer.jspf" />
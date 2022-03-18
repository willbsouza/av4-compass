## Avaliação 4 - Compass.UOL

### API REST com Spring Boot

### Testes Unitários das Classes de Serviços com JUnit5

### Documentação com Swagger pode ser acessada pelo endpoint: /swagger-ui.html

#### Entidade Partido:

<ul>
<li>id : Integer</li>
<li>nomeDoPartido : String </li>
<li>sigla : String</li>
<li>ideologia : Enum</li>
<li>dataDeFundacao : LocalDate</li>
<li>associados : List< Associado > - @OneToMany</li>
</ul>

#### Entidade Associado:

<ul>
<li>id : Integer</li>
<li>nome : String </li>
<li>cargoPolitico : Enum</li>
<li>dataDeNascimento : LocalDate</li>
<li>sexo : Enum</li>
</ul>

#### Endpoints Associado: 

<ul>
<li>POST - /associados</li>
<li>POST - /associados/partidos - Adicionar um associado na lista de associados de um determinado partido. Passando {idAssociado, idPartido}</li>
<li>GET - /associados - Filtra associados por cargo ou, se nulo, retorna todos. ?cargo=</li>
<li>GET - /associados/{id}</li>
<li>PUT - /associados/{id}</li> 
<li>DELETE - /associados/{id}</li> 
<li>DELETE - /associados/{id}/partidos/{id} - Remove um associado da lista de associados de um determinado partido. Passando {idAssociado} e {idPartido} como @PathVariable </li> 
</ul>

### Endpoints Partido:
<ul>
<li>POST - /partidos</li>
<li>GET - /partidos - Filtra partidos por ideologia ou, se nulo, retorna todos. ?ideologia=</li>
<li>GET - /partidos/{id}</li>
<li>GET - /partidos/{id}/associados - Filtra todos associados de um determinado partido informando id por @PathVariable</li>
<li>PUT - /partidos/{id}</li>
<li>DELETE - /partidos/{id}</li>
</ul>

##### H2 Database;
##### Arquivo data.sql inicialmente populado com 5 inserts para cada Entidade;
##### Tratamento de exceções com Handler;
##### O atributo dataDeNascimento e dataDeFundacao aceita e exibe o formato "dd/MM/yyyy". E é salvo no banco no padrão "yyyy-MM-dd".
##### O atributo ideologia só aceita os valores: DIREITA, CENTRO E ESQUERDA;
##### O atributo cargoPolitico só aceita os valores: VEREADOR, PREFEITO, DEPUTADO ESTADUAL, DEPUTADO FEDERAL, SENADOR, GOVERNADOR, PRESIDENTE E NENHUM;
##### O atributo sexo só aceita os valores: MASCULINO e FEMININO;


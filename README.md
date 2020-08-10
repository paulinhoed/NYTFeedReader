![picture](NYT_APP.png | width=100)

################################################################################################
 
 O app foi implementado seguindo o TDD para arquitetura Clean Code (VIPER).
 
 Comportamento geral:
  - Ao abrir o APP pela primeira vez o sistema irá consumir a API de "Top Stories" do New York Times apontando para a seção "Home (Default)";
  - O retorno dessa consulta é uma lista contendo uma thumbnail da notícia, assim como o título dela e a data de atualização e serão exibidas no máximo 20 itens;
  - Ao clicar em alguma notícia irá abrir a url correspondente da notícia no navegador padrão e a mesma irá para uma lista de notícias lidas (essa lista não é visualizada pelo usuário);
  - No momento que o usuário retorna ao aplicativo, a lista atualizará sozinha e não mostrará o item acessado anteriormente;
  
 Comportamento dos botões:
  - Select section: Ao selecionar a seção desejada, a lista será atualizada imediatamente e mostrará apenas as notícias não lidas;
  - Refresh list: Atualiza a lista e não mostrará as notícias já lidas;
  - Unselect read reports: Limpa a lista de notícias já lidas que está na memória e atualiza automaticamente a lista exibindo todas;
  
################################################################################################
 
 O passo a passo da implementação e a priorização dos itens se deu da seguinte maneira:
 - Primeiramente foi feito o cadastro na API para pegar a chave e testar o endpoint no Postman;
 - Em seguida criou-se um novo projeto e foi gerado a base da Activity principal e suas classes à partir do framework Clean Code Generator previamente instalada no Android Studio;
 - O layout da tela do app foi criado;
 - Foi feita a implementação do acesso à API de forma hardcoded (section=home) usando o Retrofit e o tratamento do json para poder extrair as informações relevantes de cada item da lista;
 - Foi criado um sharedPreferences para guardar os objetos (notícia) que teriam sido lidos pelo usuário;
 - A partir do momento que a lista de notícias lidas pelo usuário estava populada, foi feito o tratamento para exibir apenas as notícias não lidas e limitando para 20 o número delas;
 - Foi implementada as funções referentes aos botões de atualizar e desmarcar as notícias lidas;
 - E por fim foi adicionado a opção de escolher qual seção seria usada, removendo o hardocoded.

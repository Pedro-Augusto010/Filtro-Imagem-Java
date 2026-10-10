# 🖼️ Filtros de Imagem em Java 📸 📷 

## Sobre o projeto

O programa permite carregar uma imagem no formato PNG, escolher um filtro por meio do terminal e visualizar a imagem original e a modificada lado a lado.

A imagem resultante também é salva automaticamente, preservando a transparência e mantendo o arquivo original inalterado.

## Funcionalidades

- Carregamento de imagens PNG.
- Aplicação do filtro em tons de cinza.
- Aplicação do filtro negativo.
- Preservação da transparência dos pixels.
- Exibição das imagens original e modificada.
- Salvamento automático da imagem processada.
- Tratamento de opções inválidas no menu.

## Tecnologias utilizadas

- **Java:** linguagem de programação utilizada no desenvolvimento.
- **Java Swing:** biblioteca utilizada para exibir as imagens.
- **BufferedImage:** classe utilizada para manipular os pixels.
- **ImageIO:** responsável pelo carregamento e salvamento das imagens.
- **Visual Studio Code:** ambiente utilizado para desenvolver e executar o projeto.

## Conceitos de Programação Orientada a Objetos

**Interface:** a interface `Filtro` define o método `aplicar()`, estabelecendo um padrão para os filtros.

**Classe abstrata:** `FiltroPixel` implementa a interface e contém a estrutura necessária para percorrer os pixels da imagem.

**Herança:** as classes `FiltroCinza` e `FiltroNegativo` herdam as funcionalidades da classe abstrata `FiltroPixel`.

**Polimorfismo:** permite utilizar diferentes filtros por meio de uma variável do tipo `Filtro`, executando a transformação correspondente à escolha do usuário.

**Menu disponível:**

- `1` — Tons de cinza
- `2` — Negativo

Após a execução, uma janela será aberta para comparar as imagens. O resultado também será salvo no arquivo `resultado.png`.

## Funcionamento dos filtros

**Tons de cinza:** calcula a média dos valores dos canais vermelho, verde e azul de cada pixel, utilizando o resultado nos três canais.

**Negativo:** inverte os valores dos canais RGB, subtraindo cada componente de 255.

Nos dois filtros, a transparência original é preservada.

## 📁 Estrutura do Repositório

O projeto está organizado da seguinte forma:

- 📄 **filtro_foto.java** — Código-fonte principal, contendo a interface `Filtro`, a classe abstrata `FiltroPixel`, as classes `FiltroCinza` e `FiltroNegativo` e a classe `Principal`.
- 🖼️ **prints_imagens_com_filtro** — Imagem de exemplo utilizada para testar os filtros.



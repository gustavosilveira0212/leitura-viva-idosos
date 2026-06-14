# 📚 Sistema de Biblioteca em Java

Este projeto é um sistema simples de biblioteca desenvolvido em Java com o objetivo de simular, de forma básica, o funcionamento de um controle de livros em memória.

A ideia principal não é apenas cadastrar e consultar livros, mas praticar a construção de um sistema organizado em console, onde as informações são manipuladas de forma estruturada e lógica.

---

## 🎯 Por que este projeto foi feito?

O projeto foi desenvolvido para simular um cenário real simples: o controle de livros de uma biblioteca.

A proposta é entender como um sistema pode:

- Armazenar informações temporariamente
- Permitir consulta de dados específicos
- Organizar operações em diferentes partes do código
- Simular um fluxo de uso parecido com sistemas reais (menu → ação → resultado)

Ele também ajuda a treinar como dividir um problema maior em pequenas partes lógicas.

---

## 💡 Ideia central do sistema

O sistema funciona como um “controle interno” de biblioteca, onde:

- Os livros são cadastrados e armazenados enquanto o programa está rodando
- Cada livro possui informações básicas que representam sua identidade
- O usuário interage com o sistema através de um menu no terminal
- As ações são executadas conforme a escolha do usuário

Ou seja, ele simula a lógica de um sistema real, mas de forma simplificada e em memória.

---

## ⚙️ Como o sistema funciona na prática

1. O programa inicia e exibe um menu de opções
2. O usuário escolhe uma ação (cadastrar, consultar etc.)
3. O sistema processa a solicitação
4. O resultado é exibido no terminal
5. O sistema volta ao menu principal até o usuário encerrar

---

## 🏗️ Organização do projeto

O projeto foi dividido de forma simples para facilitar o entendimento:

/src

│

├── principal

│ └── Program.java

│      └── Responsável pela execução do programa e menu principal

### 📌 Organização lógica

- O sistema é executado a partir da classe `Program`
- Os dados são armazenados em arrays paralelos
- Cada funcionalidade é separada em métodos para melhor organização

---

## ▶️ Como executar o projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/gustavosilveira0212/leitura-viva-idosos.git

2. Abrir o projeto

Abra o projeto em uma IDE Java, como:

IntelliJ IDEA
Eclipse
VS Code (com extensão Java)

3. Executar o programa

Execute a classe principal:

Program.java

💻 Requisitos
Java JDK 8 ou superior
IDE com suporte a Java

🧠 Conceitos aplicados
Programação estruturada
Manipulação de arrays
Modularização com métodos
Entrada e saída de dados via terminal

🚀 Possíveis melhorias futuras
Implementar banco de dados
Criar interface gráfica (JavaFX ou Swing)
Adicionar sistema de login
Permitir edição e remoção de livros
Persistência de dados em arquivos

👨‍💻 Autor

Desenvolvido por equipe: CTRL-Java.
Projeto de estudos voltado para a AED - TRABALHO FINAL de Algoritmo.

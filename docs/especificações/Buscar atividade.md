
# Descrição

Um usuário logado deve poder buscar por atividades no sistema.

As atividades retornadas devem apresentar um status em relação ao usuário:
- "Disponível" caso o usuário não tenha iniciado essa atividade e possa inicia-la
- "Em progresso" caso o usuário tenha iniciado mas ainda não tenha concluído
- "Concluido" caso o usuário tenha concluido a atividade

Deve ser possível que o usuário filtre a buscar por seu status.
Deve ser possível que o usuário filtre a busca pelo nome da atividade.

O filtros podem ser combinados e, nesse caso, o resultado deve ser somente o que satisfazer todos os filtros.
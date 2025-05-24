
# Descrição:

Um usuário logado pode marcar a presença em uma atividade.

Caso seja a primeira presença do usuário nessa atividade, a presença é registrada mas ainda não será concluida a participação.

Caso seja a segunda presença do usuário nessa atividade, deve ser verificado se o intervalo de tempo entre a primeira presença e a segunda presença é maior do que o minimo exigida pela atividade. Caso seja, então a presença é confirmada e a participação é concluida, caso contrário a presença é rejeitada e a participação continua em aberto.
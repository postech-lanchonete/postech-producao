# coding: utf-8
# language: pt

Funcionalidade: Pedidos

  Cenário: Criação de um novo pedido
    Dado que se queira criar um pedido
    E um produto tenha o nome de "Hamburger", valor de R$ 20 e categoria "LANCHE"
    E um produto tenha o nome de "Hamburger", valor de R$ 10 e categoria "LANCHE"
    E o cliente tenha o cpf igual a "1111111111"
    Quando a requisicao do pedido for enviada
    Entao deve retornar o pedido criado
    E o status da requisicao do pedido deve ser igual a 201

  Cenário: Mudar status do pedido
    Dado que se queira mudar o status do pedido 1
    Quando enviar uma requisição para alterar o status deste pedido
    E o status da requisicao do pedido deve ser igual a 202
    E o status deste pedido deve ser igual a "EM_PREPARACAO"

  Cenário: Mudar status do pedido para em preparacao
    Dado que se queira mudar o status do pedido 1
    Quando enviar uma requisição para alterar o status deste pedido
    E o status da requisicao do pedido deve ser igual a 202
    E o status deste pedido deve ser igual a "PRONTO"

  Cenário: Mudar status do pedido para finalizado
    Dado que se queira mudar o status do pedido 1
    Quando enviar uma requisição para alterar o status deste pedido
    E o status da requisicao do pedido deve ser igual a 202
    E o status deste pedido deve ser igual a "FINALIZADO"

  Cenário: Mudar status do pedido para finalizado novamente
    Dado que se queira mudar o status do pedido 1
    Quando enviar uma requisição para alterar o status deste pedido
    E o status da requisicao do pedido deve ser igual a 202
    E o status deste pedido deve ser igual a "FINALIZADO"

  Cenário: Buscar todos os pedidos
    Dado que se queira buscar os pedidos com o status igual a ""
    Quando for feita uma busca pelos pedidos
    Entao o resultado da busca de pedidos deve conter uma lista com 1 pedidos
    E o status da busca dos pedidos deve ser igual a 200

  Cenário: Busca de pedidos por um status especifico
    Dado que se queira buscar os pedidos com o status igual a "FINALIZADO"
    Quando for feita uma busca pelos pedidos
    Entao o resultado da busca de pedidos deve conter uma lista com 1 pedidos
    E o status da busca dos pedidos deve ser igual a 200

  Cenário: Busca de pedidos por um status sem nenhum pedido
    Dado que se queira buscar os pedidos com o status igual a "RECEBIDO"
    Quando for feita uma busca pelos pedidos
    Entao o resultado da busca de pedidos deve conter uma lista com 0 pedidos
    E o status da busca dos pedidos deve ser igual a 200

  Cenário: Busca de pedido pelo status inexistente
      Dado que se queira buscar os pedidos com o status igual a "INEXISTENTE"
      Quando for feita uma busca pelos pedidos
      Entao o resultado da busca de pedidos deve retornar um erro 400
      E conter um erro da mensagem contendo "StatusDoPedido não encontrado para o valor: INEXISTENTE. Os valores permitidos são: RECEBIDO,EM_PREPARACAO,PRONTO,FINALIZADO"
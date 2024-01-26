# coding: utf-8
# language: pt

Funcionalidade: Pedidos

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
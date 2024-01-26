package br.com.lanchonete.producao.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);
}

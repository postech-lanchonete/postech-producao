package br.com.postech.producao.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);
}

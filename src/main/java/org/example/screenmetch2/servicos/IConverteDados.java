package org.example.screenmetch2.servicos;

public interface IConverteDados {
   <T> T obterDados(String json,  Class<T> classe);
}

package com.algaworks.algafood_api.infraestrutura.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal freteInicial, BigDecimal freteFinal){

        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        var parametros = new HashMap<String, Object>();

        if(StringUtils.hasLength(nome)){
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if(freteInicial != null){
            jpql.append("and taxaFrete >= :freteInicial ");
            parametros.put("freteInicial", freteInicial);
        }

        if(freteFinal != null){
            jpql.append("and taxaFrete <= :freteFinal ");
            parametros.put("freteFinal", freteFinal);
        }


        TypedQuery<Restaurante> consulta = entityManager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach((chave, valor) -> consulta.setParameter(chave, valor));
        return consulta.getResultList();

    }

}

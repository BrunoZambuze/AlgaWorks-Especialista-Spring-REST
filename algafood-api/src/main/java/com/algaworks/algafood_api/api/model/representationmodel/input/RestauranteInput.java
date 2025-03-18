package com.algaworks.algafood_api.api.model.representationmodel.input;

import com.algaworks.algafood_api.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @TaxaFrete
    @NotNull
    private BigDecimal taxaFrete;

    @NotNull
    private CozinhaIdInput cozinha;

}

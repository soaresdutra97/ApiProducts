package com.example.FakeApiPrducts.infrastructure.message.consumer;

import com.example.FakeApiPrducts.apiv1.dto.ProductsDTO;
import com.example.FakeApiPrducts.business.service.ProdutoService;
import com.example.FakeApiPrducts.infrastructure.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
//3RD
@AllArgsConstructor
@Component
public class FakeApiConsumer {

    private final ProdutoService produtoService;

    @KafkaListener(topics = "${topico.fake-api.consumer.nome}", groupId = "${topico.fake-api.consumer.group-id}")
    public void consumerProducerProdutos(ProductsDTO productsDTO){
        try {
            produtoService.salvaProdutosConsumer(productsDTO);
        } catch (Exception exception){
            throw new BusinessException("Erro ao consumir mensagens do kafka");
        }
    }
}
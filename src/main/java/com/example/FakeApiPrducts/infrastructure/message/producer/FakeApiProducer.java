package com.example.FakeApiPrducts.infrastructure.message.producer;

import com.example.FakeApiPrducts.infrastructure.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FakeApiProducer {

    @Value("${topico.fake-api.producer.nome}")
    private String topico;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public FakeApiProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviaRespostaCadastroProdutos(final String menesagem){
        try {
            kafkaTemplate.send(topico, menesagem);
        } catch (Exception e){
            throw new BusinessException("Erro ao produzir mensagem do Kafka");
        }
    }
}

package org.eskcti.mine.messages;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eskcti.mine.dto.ProposalDTO;
import org.eskcti.mine.dto.QuotationDTO;
import org.eskcti.mine.services.OpportunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvent {
    private final Logger LOG = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    OpportunityService opportunityService;

    @Incoming("proposal-channel")
    @Transactional
    public void receiveProposal(ProposalDTO proposal) {
        LOG.info("--- Recebendo Nova Proposta do Tópico Kafka ---");
        opportunityService.buildOpportunity(proposal);
    }

    @Incoming("quotation-channel")
    @Transactional
    public void receiveQuotation(QuotationDTO quotation) {
        LOG.info("--- Recebendo Nova Cotação de Moeda do Tópico Kafka ---");
        opportunityService.saveQuotation(quotation);
    }
}

package org.eskcti.mine.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.eskcti.mine.dto.OpportunityDTO;
import org.eskcti.mine.dto.ProposalDTO;
import org.eskcti.mine.dto.QuotationDTO;

import java.util.List;

@ApplicationScoped
public interface OpportunityService {
    void buildOpportunity(ProposalDTO proposal);

    void saveQuotation(QuotationDTO quotation);

    List<OpportunityDTO> generateOpportunityDate();
}

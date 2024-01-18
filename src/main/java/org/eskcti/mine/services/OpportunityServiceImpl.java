package org.eskcti.mine.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eskcti.mine.dto.OpportunityDTO;
import org.eskcti.mine.dto.ProposalDTO;
import org.eskcti.mine.dto.QuotationDTO;
import org.eskcti.mine.entities.OpportunityEntity;
import org.eskcti.mine.entities.QuotationEntity;
import org.eskcti.mine.repositories.OpportunityRepository;
import org.eskcti.mine.repositories.QuotationRepository;
import org.eskcti.mine.utils.CSVHelper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OpportunityServiceImpl implements OpportunityService{

    @Inject
    OpportunityRepository opportunityRepository;
    @Inject
    QuotationRepository quotationRepository;
    @Override
    public void buildOpportunity(ProposalDTO proposal) {
        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();
        Collections.reverse(quotationEntities);

        OpportunityEntity opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposal.getProposalId());
        opportunity.setCustomer(proposal.getCustomer());
        opportunity.setPriceTonne(proposal.getPriceTonne());
        opportunity.setLastDollarQuotation(quotationEntities.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportunity);
    }

    @Override
    public void saveQuotation(QuotationDTO quotation) {
        QuotationEntity createQuotation = new QuotationEntity();
        createQuotation.setDate(new Date());
        createQuotation.setCurrencyPrice(quotation.getCurrencyPrice());

        quotationRepository.persist(createQuotation);
    }

    @Override
    public List<OpportunityDTO> generateOpportunityDate() {
        return null;
    }

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {
        List<OpportunityDTO> opportunityList = new ArrayList<>();

        opportunityRepository.findAll().list().forEach(item -> {
            OpportunityDTO opportunity = OpportunityDTO
                    .builder()
                    .proposalId(item.getProposalId())
                    .customer(item.getCustomer())
                    .priceTonne(item.getPriceTonne())
                    .lastDollarQuotation(item.getLastDollarQuotation())
                    .build();
            opportunityList.add(opportunity);
        });
        return CSVHelper.OpportunitiesToCSV(opportunityList);
    }
}

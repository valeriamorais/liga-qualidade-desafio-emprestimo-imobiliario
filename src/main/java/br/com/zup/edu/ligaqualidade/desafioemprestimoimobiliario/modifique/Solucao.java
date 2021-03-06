package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique;

import entity.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.ProcessMessage.run;

public class Solucao {

    public static final String DELIMITER = ",";

    public static String processMessages(List<String> messages) {
      List<Proposal> proposalList = new ArrayList<>();
      List<Proposal> checkProposalList = new ArrayList<>();

      for (String message: messages) {
          run(proposalList, message);
      };

      boolean success = false;

      for(Proposal proposal: proposalList){
          success = proposal.getProposalLoanValue().compareTo(BigDecimal.valueOf(30000)) > 0 &&
                  proposal.getProposalLoanValue().compareTo(BigDecimal.valueOf(30000)) < 0 ? true : false;
          success = proposal.getProposalNumberOfMonthlyInstallments()>=24 && proposal.getProposalNumberOfMonthlyInstallments()<=180 ? true: false;
          if (success) checkProposalList.add(proposal);
          success = false;
      }

	  return checkProposalList.stream().map(proposal -> proposal.getId().toString()).collect(Collectors.joining(DELIMITER));
  }
}
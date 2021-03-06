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


      for(Proposal proposal: proposalList){
          if (CheckProposal.approved(checkProposalList, proposal)) {
              checkProposalList.add(proposal);
          }
      }

	  return checkProposalList.stream().map(proposal -> proposal.getId().toString()).collect(Collectors.joining(DELIMITER));
  }


}
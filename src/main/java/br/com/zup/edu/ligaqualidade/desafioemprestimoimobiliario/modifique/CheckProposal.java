package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique;

import entity.Proposal;

import java.math.BigDecimal;
import java.util.List;

public class CheckProposal {
    public static boolean approved(List<Proposal> checkProposalList, Proposal proposal) {
        boolean success;
        success = proposal.getProposalLoanValue().compareTo(BigDecimal.valueOf(30000)) > 0 &&
                proposal.getProposalLoanValue().compareTo(BigDecimal.valueOf(30000)) < 0 ? true : false;
        success = proposal.getProposalNumberOfMonthlyInstallments()>=24 && proposal.getProposalNumberOfMonthlyInstallments()<=180 ? true: false;
/*          for (Proponent proponent: proposal.getProponentList()) {
              success = proponent.getAge() >= 18 ? true : false;
          }*/

        return success;
    }
}

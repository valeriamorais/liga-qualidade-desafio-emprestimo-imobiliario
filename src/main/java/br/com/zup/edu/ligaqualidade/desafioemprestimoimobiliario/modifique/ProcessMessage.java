package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique;

import entity.Proponent;
import entity.Proposal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProcessMessage {

    public static final String DELIMITER = ",";

    public static void run(List<Proposal> proposalList, String message) {
        String[] splitMessage = message.split(DELIMITER);
        String event = splitMessage[1];
        String action = splitMessage[2];
        switch (event) {
            case "proposal": {
                proposalEvents(proposalList, splitMessage, action);

                break;
            }

            case "proponent": {
                proponentEvents(proposalList, splitMessage, action);
            }
        }
    }

    private static void proponentEvents(List<Proposal> proposalList, String[] splitMessage, String action) {
        if (action.equals("added")) {
            Proponent proponent = new Proponent();
            proponent.setId(UUID.fromString(splitMessage[5]));
            proponent.setName(splitMessage[6]);
            proponent.setAge(Integer.valueOf(splitMessage[7]));
            proponent.setMonthlyIncome(new BigDecimal(splitMessage[8]));
            proponent.setMain(Boolean.valueOf(splitMessage[9]));

            UUID proposalId = UUID.fromString(splitMessage[4]);
            Optional<Proposal> proposalOptional = proposalList.stream().parallel().filter(
                    proposal ->proposal.getId().equals(proposalId)).findFirst();

            proposalOptional.map(proposal -> {
                proposal.getProponentList().add(proponent);
                return proposal;
            });
        }
    }

    private static void proposalEvents(List<Proposal> proposalList, String[] splitMessage, String action) {
        switch (action) {
            case "created": {
                Proposal proposal = new Proposal();
                proposal.setId(UUID.fromString(splitMessage[4]));
                proposal.setProposalLoanValue(new BigDecimal(splitMessage[5]));
                proposal.setProposalNumberOfMonthlyInstallments(Integer.valueOf(splitMessage[6]));

                proposalList.add(proposal);

                break;
            }

            case "updated": {
                UUID proposalId = UUID.fromString(splitMessage[4]);
                Optional<Proposal> proposalOptional = proposalList.stream().parallel().filter(
                        proposal ->proposal.getId().equals(proposalId)).findFirst();

                proposalOptional.map(proposal -> {
                    Proposal proposalUpdated = new Proposal();
                    proposalUpdated.setId(proposalId);
                    proposalUpdated.setProposalLoanValue(new BigDecimal(splitMessage[5]));
                    proposalUpdated.setProposalNumberOfMonthlyInstallments(Integer.valueOf(splitMessage[6]));

                    proposalList.remove(proposal);
                    proposalList.add(proposalUpdated);

                    return proposalUpdated;
                });

                break;
            }

            case "deleted": {
                UUID proposalId = UUID.fromString(splitMessage[4]);
                Optional<Proposal> proposalOptional = proposalList.stream().parallel().filter(
                        proposal ->proposal.getId().equals(proposalId)).findFirst();

                proposalOptional.map(proposal -> {
                    proposalList.remove(proposal);
                    return proposal;
                });

                break;
            }
        }
    }
}

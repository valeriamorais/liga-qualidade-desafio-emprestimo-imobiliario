package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Proposal {
    private UUID id;
    private BigDecimal proposalLoanValue;
    private ArrayList<Proponent> proponentList = new ArrayList<>();
    private int proposalNumberOfMonthlyInstallments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getProposalLoanValue() {
        return proposalLoanValue;
    }

    public void setProposalLoanValue(BigDecimal proposalLoanValue) {
        this.proposalLoanValue = proposalLoanValue;
    }

    public ArrayList<Proponent> getProponentList() {
        return proponentList;
    }

    public void setProponentList(ArrayList<Proponent> proponentList) {
        this.proponentList = proponentList;
    }

    public int getProposalNumberOfMonthlyInstallments() {
        return proposalNumberOfMonthlyInstallments;
    }

    public void setProposalNumberOfMonthlyInstallments(int proposalNumberOfMonthlyInstallments) {
        this.proposalNumberOfMonthlyInstallments = proposalNumberOfMonthlyInstallments;
    }

    public boolean isValid() {
        BigDecimal minProposalValue = new BigDecimal("30000.00");
        BigDecimal maxProposalValue = new BigDecimal("30000000.00");
        int minNumberOfMonthlyProposal = 24;
        int maxNumberOfMonthlyProposal = 180;

        if (this.proposalLoanValue.compareTo(minProposalValue) < 0 ||
                this.proposalLoanValue.compareTo(maxProposalValue) > 0) {
            return false;
        }

        if (this.proposalNumberOfMonthlyInstallments < minNumberOfMonthlyProposal ||
                this.proposalNumberOfMonthlyInstallments > maxNumberOfMonthlyProposal) {
            return false;
        }

        if (this.proponentList.size() < 2) {
            return false;
        }

        boolean existsMainProponent = false;
        for (Proponent proponent : this.proponentList) {
            if (proponent.getAge() < 18) {
                return false;
            }
            if (proponent.isMain()) {
                if (existsMainProponent) {
                    return false;
                }
                existsMainProponent = true;
            }
        }

        return true;
    }
}

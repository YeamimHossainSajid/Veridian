package com.veridian.riskengine.model;

public class RiskEvaluationResult {

    private final boolean approved;
    private final String reason;
    private final String violationCode;

    private RiskEvaluationResult(boolean approved, String reason, String violationCode) {
        this.approved = approved;
        this.reason = reason;
        this.violationCode = violationCode;
    }

    public static RiskEvaluationResult accept() {
        return new RiskEvaluationResult(true, "Risk checks passed", null);
    }

    public static RiskEvaluationResult reject(String reason, String violationCode) {
        return new RiskEvaluationResult(false, reason, violationCode);
    }

    public boolean isApproved() { return approved; }
    public String getReason() { return reason; }
    public String getViolationCode() { return violationCode; }
}

package pl.ing.pionteching.atm.dto;

public record Task(int region, int atmId, RequestType requestType) {
    public int priority() {
        return this.requestType.getPriority();
    }
}
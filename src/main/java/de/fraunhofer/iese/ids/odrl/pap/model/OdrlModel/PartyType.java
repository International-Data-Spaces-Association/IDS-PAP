package de.fraunhofer.iese.ids.odrl.pap.model.OdrlModel;

public enum PartyType {

    PROVIDER("provider"),

    CONSUMER("consumer"),

    INFORMEDPARTY("informedParty");


    private final String type;

    PartyType(String t) {
        this.type = t;
    }

    public String getType() {
        return type;
    }
}


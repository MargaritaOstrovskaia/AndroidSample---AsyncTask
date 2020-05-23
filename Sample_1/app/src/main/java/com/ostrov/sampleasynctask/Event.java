package com.ostrov.sampleasynctask;

/** Represents an earthquake event. */
class Event {
    /** Title of the earthquake event */
    private final String title;

    /** Number of people who felt the earthquake and reported how strong it was */
    private final String numOfPeople;

    /** Perceived strength of the earthquake from the people's responses */
    private final String perceivedStrength;

    /**
     * Constructs a new {@link Event}.
     * @param eventTitle is the title of the earthquake event
     * @param eventNumOfPeople is the number of people who felt the earthquake and reported how strong it was
     * @param eventPerceivedStrength is the perceived strength of the earthquake from the responses
     */
    Event(String eventTitle, String eventNumOfPeople, String eventPerceivedStrength) {
        this.title = eventTitle;
        this.numOfPeople = eventNumOfPeople;
        this.perceivedStrength = eventPerceivedStrength;
    }

    /** Get title of the earthquake */
    public String getTitle() {
        return title;
    }

    /** Get number of people who felt the earthquake */
    public String getNumOfPeople() {
        return numOfPeople;
    }

    /** Get perceived strength of the earthquake */
    public String getPerceivedStrength() {
        return perceivedStrength;
    }
}
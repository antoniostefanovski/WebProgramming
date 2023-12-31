package mk.ukim.finki.wp.laboratory1.model;

import lombok.Data;

@Data
public class TicketOrder {
    String movieTitle;

    String clientName;

    String clientAddress;

    Long numberOfTickets;

    public TicketOrder(String movieTitle, String clientName, String clientAddress, Long numberOfTickets) {
        this.movieTitle = movieTitle;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.numberOfTickets = numberOfTickets;
    }
}

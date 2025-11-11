package model;

import java.util.List;

public class Bill {
    private int id;
    private float totalPayment;
    private int ticketSellerId;
    private List<Ticket> tickets;
    
    public Bill() {}
    
    public Bill(float totalPayment, int ticketSellerId, List<Ticket> tickets) {
        this.totalPayment = totalPayment;
        this.ticketSellerId = ticketSellerId;
        this.tickets = tickets;
    }
    
    public Bill(int id, float totalPayment, int ticketSellerId, List<Ticket> tickets) {
        this.id = id;
        this.totalPayment = totalPayment;
        this.ticketSellerId = ticketSellerId;
        this.tickets = tickets;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public float getTotalPayment() {
        return totalPayment;
    }
    
    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }
    
    public int getTicketSellerId() {
        return ticketSellerId;
    }
    
    public void setTicketSellerId(int ticketSellerId) {
        this.ticketSellerId = ticketSellerId;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }
    
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}


package com.anshumr.flight.model;

import java.util.Date;

import com.anshumr.flight.model.types.SeatSection;
import com.anshumr.flight.model.types.SeatType;


public class UserSelection {

	private SeatType seatType;
	private SeatSection seatSelection;
	private int numOfTickets;
	private Date date;
	private long flight_number;
	
	
	public UserSelection(SeatType seatType, SeatSection seatSelection, int numOfTickets, Date date,
			long flight_number) {
		super();
		this.seatType = seatType;
		this.seatSelection = seatSelection;
		this.numOfTickets = numOfTickets;
		this.date = date;
		this.flight_number = flight_number;
	}
	
	
	public SeatType getSeatType() {
		return seatType;
	}
	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}
	public SeatSection getSeatSelection() {
		return seatSelection;
	}
	public void setSeatSelection(SeatSection seatSelection) {
		this.seatSelection = seatSelection;
	}
	public int getNumOfTickets() {
		return numOfTickets;
	}
	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}
	
	
	
	
	
	
}

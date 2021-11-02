package com.udaan.flight.model;

import javax.persistence.*;

@Entity
@Table(name = "flight")
public class FlightDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private long id;

    @Column(name ="flight_number" )
    private long flightno;

    @Column(name = "flight_name")
    private String name;

    
	@Column(name = "numRows")
    private int numRows;

    @Column(name = "numColumns")
    private int numColumns;

    public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	
    
}

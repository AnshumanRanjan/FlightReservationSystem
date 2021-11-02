package com.anshumr.flight.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.anshumr.flight.repository.FlightDetailRepository;

public class FlightSeatReservation {

	private long flightno;
	private Date flightDate;
	private String[][] seats;

	private int totalSeats;
	static int filled = 0;

	public FlightSeatReservation(long flightNumber, Date flightDate, UserSelection userSel,
			FlightDetailRepository flightDetailRepository) {

		FlightDetail flight = flightDetailRepository.findByFlightno(flightNumber);

		System.out.println("Query Returned " + flight.getNumRows());
		this.flightDate = flightDate;

		this.seats = new String[flight.getNumRows() + 1][flight.getNumColumns() + 1];
		this.totalSeats = flight.getNumRows() * flight.getNumColumns();

		// Filling the row lables with Row Number
		seats[0][0] = "0";
		seats[0][1] = "A";
		seats[0][2] = "B";
		seats[0][3] = "C";
		seats[0][4] = "D";
		seats[0][5] = "E";
		seats[0][6] = "F";
		seats[0][7] = "G";
		seats[0][8] = "H";

		seats[0][0] = "0";
		seats[1][0] = "1";
		seats[2][0] = "2";
		seats[3][0] = "3";
		seats[4][0] = "4";
		seats[5][0] = "5";
		seats[6][0] = "6";
		seats[7][0] = "7";
		seats[8][0] = "8";
		seats[9][0] = "9";
		seats[10][0] = "10";

		// Filling the column labels
		for (int i = 1; i <= flight.getNumRows(); i++) {
			seats[i][1] = "O";
			seats[i][2] = "O";
			seats[i][3] = "O";
			seats[i][4] = "O";
			seats[i][5] = "O";
			seats[i][6] = "O";
			seats[i][7] = "O";
			seats[i][8] = "O";
		}

	}

	public String[][] returnSeatavailibility() {

		// System.out.println(Arrays.deepToString(seats).replace("], ", "]\n"));
		// return Arrays.deepToString(seats).replace("], ", "]\n") ;

		return seats;

	}

	/* method overrides to generate reserved Seats */

	public String[][] returnSeatavailibility(String[] seatNo) {

		String rowNum;
		String colNum;

		for (int i = 0; i < seatNo.length; i++) {
			if (seatNo[i].length() == 2) {
				rowNum = seatNo[i].substring(1, 2);
				colNum = seatNo[i].substring(0, 1);

			} else if (seatNo[i].length() == 3) {
				rowNum = seatNo[i].substring(1, 2);
				colNum = seatNo[i].substring(0, 0);
			} else {
				rowNum = "Error";
				colNum = "Error";
			}
			this.researveSeats(Integer.parseInt(rowNum), colNum);
			rowNum = "";
			colNum = "";
		}

		return seats;

	}

	public void researveSeats(int row, String col) {

		// Check if input is valid
		int column = (int) col.charAt(0) - 64;
		seats[row][column] = "X";

	}

	public Boolean researveSeats(List<String> seatNumber, FlightDetailRepository flightDetailRepository) {

		// CHeck if the number of available seats are not less than number of seats
		// asked in the list :TODO

		FlightDetail flight = flightDetailRepository.findByFlightno(flightno);
		for (String seat : seatNumber) {

			int rownum = seat.charAt(0) - '1';
			int colnum = seat.charAt(1) - 'A';

			// Verify that the seat input are valid and correct :TODO

			if (rownum < 0 || rownum > flight.getNumRows() || colnum < 0 || colnum > flight.getNumColumns()) {
				System.out.println("Seat selected are not valid");
			}

			else {

				if (seats[rownum][colnum] != "X") {
					seats[rownum][colnum] = "X";
					filled++;

					return true;
				} else {
					System.out.println("Selectd Seats are already researved ");
				}

			}

		}
		return false;
	}

	public void autoResearveSeats(List<String> seatNumber, UserSelection pref) {

	}

}

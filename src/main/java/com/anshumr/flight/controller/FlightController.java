package com.anshumr.flight.controller;

import com.anshumr.flight.model.*;
import com.anshumr.flight.model.types.SeatSection;
import com.anshumr.flight.model.types.SeatType;
import com.anshumr.flight.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

@RestController
public class FlightController {

	@Autowired
	private FlightDetailRepository flightDetailRepository;
	UserSelection userSel;
	FlightSeatReservation reser;

	@GetMapping(value = "/flight/search")
	public String createUserPref(@RequestParam SeatType seatType, @RequestParam SeatSection seatSelection,
			@RequestParam int NumberofTickets, @RequestParam Date tripdate, @RequestParam long numb) {

		userSel = new UserSelection(seatType, seatSelection, NumberofTickets, tripdate, numb);
		reser = new FlightSeatReservation(numb, tripdate, userSel, flightDetailRepository);
		return makeTableHTML(reser.returnSeatavailibility());

	}

	@RequestMapping(value = "/flight/{reserve}")
	public String submitUserPref(@RequestBody String SeatNo) {

		/* Case 1 user selected Auto Select seats */
		if (SeatNo.equalsIgnoreCase("SeatNo=")) {
			System.out.println("Inside the autoselect method" + userSel.getSeatType() + "<--");

			/* Selecting Optimum Seat Selection */
			// Calling getSeatpreference and getAreaPreference

			getSeatPreference(getAreaPreference());

			return makeTableHTML(reser.returnSeatavailibility());

		}

		/* Case 2 , user provided a list of seats */
		// :TODO verify if seat number length = seat number requested

		try {
			SeatNo = java.net.URLDecoder.decode(SeatNo, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// not going to happen - value came from JDK's own StandardCharsets
		}
		if (SeatNo == null) {
			return "Error , PLease select a Seat number ";
		}
		return makeTableHTML(reser.returnSeatavailibility(SeatNo.split("=")[1].split(",")));

	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/flight/reserveSeats/{flightNumber}/{searNo}/{modifiedFlag}")
	public ResponseEntity<?> reserveSeats(@PathVariable int flightNumber, @PathVariable List<String> searNo,
			@PathVariable int modifiedFlag) {

		return new ResponseEntity<>("Basd on  modifiedFlag  either call autoAssign or createReservation",
				HttpStatus.OK);

	}

	/* utility function for HTMl objects */

	public String makeTableHTML(String[][] twoArray) {
		String result = "<html><body><h1>Seat Selection<h1>"
				+ "<table ,td , th style=\"border:2px solid black;width:60%;margin-left:auto;margin-right:auto;\" >";
		for (int i = 0; i < twoArray.length; i++) {
			result += "<tr>";
			for (int j = 0; j < twoArray[i].length; j++) {
				result += "<td>" + twoArray[i][j] + "</td>";
			}
			result += "</tr>";
		}
		result += "</table>";

		/* Adding Submit preference and a AutoAssign Button, */
		String submissionForum = "<form action=\"/flight/reserve\" method = \"post\" enctype=\"application/json\" > <br> <br>  <label for=\"seatNo\">Seat number:</label>  <input type=\"text\" name=\"SeatNo\" value=\"\"/><input type=\"submit\" value=\"Submit Preference\">  <input type=\"submit\" value=\"Auto Assign Seat\"> </form>";
		result += submissionForum;
		result += "</body></html>";
		return result;

	}

	public Integer[] getAreaPreference() {

		int startfrom = 0;
		int endat = 0;
		Integer[] ret = new Integer[2];
		if (userSel.getSeatSelection() == SeatSection.FRONT) {
			ret[0] = 1;
			ret[1] = 3;

		}

		if (userSel.getSeatSelection() == SeatSection.MID) {
			ret[0] = 4;
			ret[1] = 9;

		}

		if (userSel.getSeatSelection() == SeatSection.RARE) {
			ret[0] = 9;
			ret[1] = 11;
		}
		return ret;
	}

	public void getSeatPreference(Integer[] areaPref) {

		int startro = areaPref[0];
		int endRow = areaPref[1];

		if (userSel.getSeatType() == SeatType.WINDOW) {
			// Loop across the row to see which window seats are vacent

			for (int i = 0; i < userSel.getNumOfTickets(); i++) {
				int j = startro;
				while (j < endRow) {
					if (reser.returnSeatavailibility()[j][1] != "X") {
						reser.returnSeatavailibility()[j][1] = "X";
						break;
					} else if (reser.returnSeatavailibility()[j][8] != "X") {
						reser.returnSeatavailibility()[j][8] = "X";
						break;
					} else {
						j++;
					}
				}
			}

		}
		if (userSel.getSeatType() == SeatType.AISL) {
			// Loop across the row to see which window seats are vacent

			for (int i = 0; i < userSel.getNumOfTickets(); i++) {
				int j = startro;
				while (j < endRow) {
					if (reser.returnSeatavailibility()[j][2] != "X") {
						reser.returnSeatavailibility()[j][2] = "X";
						break;
					} else if (reser.returnSeatavailibility()[j][3] != "X") {
						reser.returnSeatavailibility()[j][3] = "X";
						break;
					} else if (reser.returnSeatavailibility()[j][6] != "X") {
						reser.returnSeatavailibility()[j][6] = "X";
						break;
					} else if (reser.returnSeatavailibility()[j][7] != "X") {
						reser.returnSeatavailibility()[j][7] = "X";
						break;
					} else {
						j++;
					}
				}
			}

		}
		if (userSel.getSeatType() == SeatType.MIDDLE) {
			// Loop across the row to see which window seats are vacent

			for (int i = 0; i < userSel.getNumOfTickets(); i++) {
				int j = startro;
				while (j < endRow) {
					if (reser.returnSeatavailibility()[j][4] != "X") {
						reser.returnSeatavailibility()[j][4] = "X";
						break;
					} else if (reser.returnSeatavailibility()[j][4] != "X") {
						reser.returnSeatavailibility()[j][4] = "X";
						break;
					} else {
						j++;
					}
				}
			}

		}

	}

}
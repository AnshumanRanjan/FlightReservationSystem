package com.udaan.flight.controller;

import com.udaan.flight.model.*;
import com.udaan.flight.model.types.SeatSection;
import com.udaan.flight.model.types.SeatType;
import com.udaan.flight.repository.*;
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
	
   /* @GetMapping(value = "/flight/search/{flightNumber}/{date}/{section}/{seatType}/{numTicket}")
    public  String createUserPref( @PathVariable SeatType seatType, @PathVariable SeatSection section,@PathVariable int numTicket,@PathVariable Date date,
    		@PathVariable long flightNumber , Model model) { */
	@GetMapping(value = "/flight/search")
    public  String createUserPref( @RequestParam SeatType seatType, @RequestParam SeatSection seatSelection,@RequestParam int NumberofTickets,@RequestParam Date tripdate,@RequestParam long numb) {
    	
    	
    	userSel =  new UserSelection(seatType,seatSelection , NumberofTickets , tripdate ,numb );
    	reser = new FlightSeatReservation(numb, tripdate, userSel,flightDetailRepository);
    	return makeTableHTML(reser.returnSeatavailibility());

    }
    
    
    @RequestMapping(value = "/flight/{reserve}")
    public  String submitUserPref( @RequestBody String SeatNo) {
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

    	
    	return new ResponseEntity<>( "Basd on  modifiedFlag  either call autoAssign or createReservation", HttpStatus.OK);

       
    }
    
    
    
   /* utility function for HTMl objects */
    
 public String makeTableHTML(String[][] twoArray) { 
	 String result = "<table border=1>";
	    for(int i=0; i<twoArray.length; i++) {
	        result += "<tr>";
	        for(int j=0; j<twoArray[i].length; j++){
	            result += "<td>"+twoArray[i][j]+"</td>";
	        }
	        result += "</tr>";
	    }
	    result += "</table>";
	    
	    /* Adding Submit Buttoon */
	    String submissionForum = "<form action=\"/flight/reserve\" method = \"post\" enctype=\"application/json\" > <label for=\"seatNo\">Seat number:</label> <input type=\"text\" name=\"SeatNo\" value=\"\"/><input type=\"submit\" value=\"Submit\"> </form>";
	    result += submissionForum;
	    return result;
	 
 }
    
    
}
// Loads the header and footer xhtmls into #header and #footer directly
$(function(){
  $("#header").load("header.xhtml"); 
  $("#footer").load("footer.html"); 
});

function showRemaining() {
	
	var end = new Date('12/23/2014 00:00 AM');
	var _second = 1000;
	var _minute = _second * 60;
	var _hour = _minute * 60;
	var _day = _hour * 24;
	var timer;
    var now = new Date();
    var distance = end - now;
   
    var days = Math.floor(distance / _day);
    var hours = Math.floor((distance % _day) / _hour);
    var minutes = Math.floor((distance % _hour) / _minute);
    var seconds = Math.floor((distance % _minute) / _second);

	document.getElementById('Days').innerHTML = "Time left until cristmas: " 
    document.getElementById('Days').innerHTML += days + 'days ';
    document.getElementById('Days').innerHTML += hours + 'hrs ';
    document.getElementById('Days').innerHTML += minutes + 'mins ';
    document.getElementById('Days').innerHTML += seconds + 'sec ';
    
    timer = setInterval(showRemaining, 1000);

}



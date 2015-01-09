// Loads the header and footer xhtmls into #header and #footer directly
$(function(){
  $("#header").load("header.xhtml"); 
  $("#footer").load("footer.xhtml"); 
});

function showRemaining() {

	    var oneMinute = 60 * 1000
	    var oneHour = oneMinute * 60
	    var oneDay = oneHour * 24
	    var today = new Date()
	    var nextXmas = new Date()
	    nextXmas.setMonth(11)
	    nextXmas.setDate(24)
	    if (today.getMonth() == 11 && today.getDate() > 24) {
	        nextXmas.setFullYear(nextXmas.getFullYear() + 1)
	    }
	    var diff = nextXmas.getTime() - today.getTime()
	    diff = Math.floor(diff/oneDay)
	    return diff

}




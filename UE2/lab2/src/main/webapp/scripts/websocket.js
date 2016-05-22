 /*
function test() {
	socket.send("new_bid ae-234f John Doe 120.0");
}
*/
// Depending on the setup of your server, servlet, and socket, you may have to
// change the URL.
var socket = new WebSocket("ws://localhost:8080/socket");

socket.onopen = function() {
	//do nothing
}

socket.onmessage = function(event) {


	document.getElementById('demo').innerHTML = event.data;
	
	
	var server_msg = event.data.split(" ");
	if(server_msg[0] == "new_bid") {
		var product_id = server_msg[1];
		var forename = server_msg[2];
		var lastname = server_msg[3];
		var price = server_msg[4];
		
		//$('.demo2').html("Das Ding kostet jetzt " + price + "&euro;");
		$('.highest-bid').html(price + "&euro;");
		$('.product-price').html(price + "&euro;");
		$('.highest-bidder').html(forename + " " + lastname);
		$('.product-highest').html(forename + " " + lastname);
	}
	
	else if(server_msg[0] == "expired") {
		
    }
	else if(server_msg[0] == "overbid") {
		
	}
    /***  write your code here ***/
	
};

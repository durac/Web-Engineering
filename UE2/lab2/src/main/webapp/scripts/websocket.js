 
function test() {
	socket.send("new_bid ae-234f 120");
}

// Depending on the setup of your server, servlet, and socket, you may have to
// change the URL.
var socket = new WebSocket("ws://localhost:8080/socket");

socket.onopen = function() {
	//do nothing
}

socket.onmessage = function(event) {

	//to-do: update the html according to the server message: "product-id user-name price"
	document.getElementById('demo').innerHTML = event.data;

	
    /***  write your code here ***/
	
};


var stompClient = null;
var interval = null;
var idSet = new Set();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/all', function (event) {
            showEvent(JSON.parse(event.body));
        });
        if (interval != null) {
            clearInterval(interval);
            interval = null;
        }
        interval = setInterval(function() {
            stompClient.send("/app/all", {}, null);
        }, 500);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    if (interval != null) {
        clearInterval(interval);
        interval = null;
    }
    setConnected(false);
    console.log("Disconnected");
}

function showEvent(message) {
    if (!idSet.has(message.id)) {
        $("#events").append("<tr><td>" + message.model + "</td><td>"+message.serialNumber
            +"</td><td>"+message.capacityBytes+"</td></tr>");
        idSet.add(message.id);
    }
}

$(document).ready(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
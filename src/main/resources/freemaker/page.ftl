<html><head><title>Web Socket Test</title></head>
<body>
<script src="js/stomp.js"></script>
<script src="js/script.js"></script>
<script type="text/javascript">

    var socket;

    webSocketLocation = "${webSocketLocation}";
    stompLocation = "${stompLocation}";
    stompTopic = "${stompTopic}";

    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket(webSocketLocation);
        socket.onmessage = function(event) {
            logMessage("WebSocket-response: " + event.data);
        };
        socket.onopen = function(event) {
            logMessage("WebSocket-status: opened");
        };
        socket.onclose = function(event) {
            logMessage("WebSocket-status: closed");
        };
    } else {
        logMessage("WebSocket-error: your browser does not support Web Socket");
    }

    var client = Stomp.client(stompLocation, "v11.stomp");

    client.connect("", "",
            function () {
                client.subscribe(stompTopic,
                        function (message) {
                            mqMessage(message.body);
                        },
                        {priority: 9}
                );
            }
    );

    function sendWebsocketMessage(message) {
        if (!window.WebSocket) { return; }
        if (socket.readyState == WebSocket.OPEN) {
            logMessage("WebSocket-request: " + message);
            socket.send(message);
            client.send(stompTopic, {priority: 9}, "message from web page: " + message);
        } else {
            logMessage("WebSocket-error: socket is not open");
        }
    }
    function logMessage(message) {
        var ta = document.getElementById('logText');
        ta.value = getTs() + " " + message + '\n' +  ta.value;
    }

    function mqMessage(message) {
        var ta = document.getElementById('mqText');
        ta.value = getTs() + " " + message + '\n' +  ta.value;
    }
</script>

<script type="text/javascript">




</script>
<form onsubmit="return false;">
    <input type="text" name="message" id="message" placeholder="Enter text to be sent" autofocus />
    <input type="button" value="Send Data" onclick="sendWebsocketMessage(this.form.message.value)" />
    <h3>MQ messages</h3>
    <textarea id="mqText" style="width:500px;height:300px;" readonly></textarea>
    <h3>Log messages</h3>
    <textarea id="logText" style="width:500px;height:300px;" readonly></textarea>
</form>
</body>
</html>
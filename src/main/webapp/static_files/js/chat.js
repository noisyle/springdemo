(function($)
{
    var cometd = $.cometd;

    $(document).ready(function()
    {
        function _connectionEstablished()
        {
            $('#body').append('<div>连接成功</div>');
        }

        function _connectionBroken()
        {
            $('#body').append('<div>连接出错</div>');
        }

        function _connectionClosed()
        {
            $('#body').append('<div>断开连接</div>');
        }

        // Function that manages the connection status with the Bayeux server
        var _connected = false;
        function _metaConnect(message)
        {
            if (cometd.isDisconnected())
            {
                _connected = false;
                _connectionClosed();
                return;
            }

            var wasConnected = _connected;
            _connected = message.successful === true;
            if (!wasConnected && _connected)
            {
                _connectionEstablished();
            }
            else if (wasConnected && !_connected)
            {
                _connectionBroken();
            }
        }

        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(handshake)
        {
            if (handshake.successful === true)
            {
                cometd.batch(function()
                {
                    cometd.subscribe('/hello', function(message)
                    {
                        $('#body').append('<div>服务器: ' + message.data.greeting + '</div>');
                    });
                    // Publish on a service channel since the message is for the server only
                    cometd.publish('/service/hello', { name: 'user' });
                    
                    cometd.subscribe('/chat', function(message){
                        $('#body').append('<div>' + message.data.name + '</div>');
                    });
                });
            }
        }

        // Disconnect when the page unloads
        $(window).unload(function()
        {
            cometd.disconnect(true);
        });

        var cometURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
        cometd.configure({
            url: cometURL,
            logLevel: 'debug'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
        
        $("#send").click(function(){
        	var msg = $("#msg").val();
        	if(_connected && msg){
                cometd.publish('/chat', { name: msg });
    			$("#msg").val("");
        	}
        });
    });
})(jQuery);
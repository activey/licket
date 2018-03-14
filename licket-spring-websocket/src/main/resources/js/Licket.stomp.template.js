(function(app) {

    var StompClient = function() {
    	return {
    	    connected: false,
    		stompClient: {},
    		addTopicListener: function(listener) {
    		    if (this.connected === true) {
                    this.topicListeners.push(listener);
    		        this.stompClient.subscribe(listener.topic, listener.callbackFunction);
    		        return;
    		    }
    			this.topicListeners.push(listener);
    		},
    		connect: function() {
    			var socket = new SockJS("<stomp_uri>");
    			var vm = this;
    			this.stompClient = Stomp.over(socket);
    			this.stompClient.connect({}, function(frame) {
                    vm.stompClient.subscribe("/user/topic/component/patch", function(patch) {
                        var parsedPatch = JSON.parse(patch.body);
                        app.instance.$licketModelReloader.notifyModelPatched(parsedPatch.compositeId, parsedPatch.patch);
                    });

                    vm.stompClient.subscribe("/user/topic/component/model", function(model) {
                        var parsedModel = JSON.parse(model.body);
                        app.instance.$licketModelReloader.notifyModelChanged(parsedModel.compositeId, parsedModel.model);
                    });
    			});
    		}
    	}
    };

    app.StompClient = new StompClient();
    app.StompClient.connect();
})(window.app || (window.app = {}));
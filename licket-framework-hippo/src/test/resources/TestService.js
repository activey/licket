(function(app) {
    app.Service = ng.core.Class({
        constructor: [ng.http,
            function(angularHttpService) {
                this.httpService = angularHttpService;
            }
        ],
        invokeComponentAction: function(data) {
            console.log(data);
        }
    });
})(window.app || (window.app = {}));
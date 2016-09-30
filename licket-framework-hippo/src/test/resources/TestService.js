(function(app) {
    app.Service = ng.core.Class({
        constructor: [ng.http,
            function(angularHttpService) {
                this.httpService = angularHttpService;
            }
        ],
        invokeComponentAction: function(data) {
            var _this = this;
            return http.post("`", data);
        }
    });
})(window.app || (window.app = {}));
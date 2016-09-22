(function(app) {

    app.ContactsPage = ng.core.Component({
            selector: 'contacts-page'
        })
        .View({
            templateUrl: '/licket/component/contacts-page/view'
        })
        .Class({
            constructor: function() {
                this.model = {

                };

                this.compositeId = "test";

                this.invokeComponentAction = function(actionData, responseListener) {
                    http.post("/licket/component/action", actionData).subscribe(responseListener);
                };
            }
        });

})(window.app || (window.app = {}));
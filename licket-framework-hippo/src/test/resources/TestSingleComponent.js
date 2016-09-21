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
            }
        });

})(window.app || (window.app = {}));
(function(app) {

    app.ContactsPage = ng.core
        .Component({
            selector: 'contacts-page',
            templateUrl: '/licket/component/contacts-page/view'
        })
        .Class({
            constructor: function() {
                this.model = {

                };
            }
        });

})(window.app || (window.app = {}));
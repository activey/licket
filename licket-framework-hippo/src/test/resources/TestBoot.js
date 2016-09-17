(function(app) {
    document.addEventListener('DOMContentLoaded', function() {
        ng.platform.browser.bootstrap(app.TodosComponent);
    });
})(window.app || (window.app = {}));
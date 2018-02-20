 (function(app) {
  var ComponentSecurityStorage = function() {
    return {
        getToken: function() {
            return this.token;
        },
        setToken: function(token) {
            this
        }

    };
  };


  app.componentSecurityStorage = new ComponentSecurityStorage()();
})(window.app || (window.app = {}));
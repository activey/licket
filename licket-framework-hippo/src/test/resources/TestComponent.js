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
                this.invokeComponentAction = function(actionData) {
                    http.post("/licket/component/action", actionData);
                };
            }
        });

    app.ContactsPage.Contacts = ng.core
        .Component({
            selector: 'contacts',
            templateUrl: '/licket/component/contacts-page:contacts/view'
        })
        .Class({
            constructor: function() {
                this.model = {

                };
            }
        });


    app.TodosComponent = ng.core
        .Component({
            selector: 'todos',
            templateUrl: '/app-js/components/todos/template.html'
        })
        .Class({
            constructor: function () {
                this.newTodo = '';
                this.todos = [];
            },
            addTodo: function(event) {
                todoObj = {
                    newTodo: this.newTodo,
                    complete: false
                }
                this.todos.push(todoObj);
                this.newTodo = '';
                event.preventDefault();
            },
            deleteTodo: function (index) {
                this.todos.splice(index, 1);
            }
        });

})(window.app || (window.app = {}));
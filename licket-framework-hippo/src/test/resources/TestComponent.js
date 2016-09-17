(function(app) {

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
        })

})(window.app || (window.app = {}));
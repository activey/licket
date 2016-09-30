app.AppModule = ng.core.NgModule({
    imports: [ng.http.HttpModule, ng.forms.FormsModule, ng.platformBrowser.BrowserModule],
    declarations: [app.ContactsPage, app.ContactsPage_ContactsPanel, app.ContactsPage_ContactsPanel_AddContactForm],
    bootstrap: [app.ContactsPage]
}).Class({
    constructor: function () {}
});
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
function __export(m) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}

exports.SEMANTIC_COMPONENTS = [
    exports.SemanticModalComponent,
    exports.SemanticSegmentComponent
//    exports.SemanticCardComponent,
//    exports.SemanticCardsComponent,
//    exports.SemanticContextMenuComponent,
//    exports.SemanticInputComponent,
//    exports.SemanticTextareaComponent,
//    exports.SemanticCheckboxComponent,
//    exports.SemanticMenuComponent,
//    exports.SemanticMessageComponent,
//    exports.SemanticDimmerComponent,
//    exports.SemanticTransitionComponent,
//    exports.SemanticShapeComponent,
//    exports.SemanticPopupComponent,
//    exports.SemanticDropdownComponent,
//    exports.SemanticListComponent,
//    exports.SemanticSelectComponent,
//    exports.SemanticFlagComponent,
//    exports.SemanticSearchComponent,
//    exports.SemanticItemComponent,
//    exports.SemanticSidebarComponent,
//    exports.SemanticProgressComponent,
//    exports.SemanticTabsComponent,
//    exports.SemanticTabComponent,
//    exports.SemanticButtonComponent,
//    exports.SemanticLoaderComponent,
//    exports.SemanticAccordionComponent,
//    exports.SemanticAccordionItemComponent,
//    exports.SemanticRatingComponent
];

exports.SEMANTIC_DIRECTIVES = [
//    exports.SMTooltipDirective,
//    exports.SMVisibilityDirective,
//    exports.SMDeviceVisibilityDirective
];
var NgSemanticModule = (function () {
    function NgSemanticModule() {
    }
    NgSemanticModule = __decorate([
        core_1.NgModule({
            declarations: [exports.SEMANTIC_DIRECTIVES, exports.SEMANTIC_COMPONENTS],
            exports: [exports.SEMANTIC_COMPONENTS, exports.SEMANTIC_DIRECTIVES]
        }),
        __metadata('design:paramtypes', [])
    ], NgSemanticModule);
    return NgSemanticModule;
}());
exports.NgSemanticModule = NgSemanticModule;

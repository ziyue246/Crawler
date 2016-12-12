/**
 * Created by Lv on 2016/11/16.
 */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {CheckListComponent} from './check-list/check-list.component';
import {RecordComponent} from "./record/record.component";


const routes: Routes = [
    { path: '', redirectTo: '/checklist/1', pathMatch: 'full' },
    { path: 'checklist/:id', component: CheckListComponent },
    { path: 'record/:id', component: RecordComponent },
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}

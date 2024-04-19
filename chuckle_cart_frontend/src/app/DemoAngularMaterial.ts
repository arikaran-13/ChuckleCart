import { NgModule } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatChipsModule} from '@angular/material/chips';
import {MatMenuModule} from '@angular/material/menu';
import {MatRadioModule} from '@angular/material/radio';
import {MatDividerModule} from '@angular/material/divider';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTableModule} from '@angular/material/table';
import { MatNativeDateModule } from "@angular/material/core";

@NgModule({
    exports:[
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatToolbarModule,
        MatSelectModule,
        MatIconModule,
        MatProgressSpinnerModule,
        MatSnackBarModule,
        MatPaginatorModule,
        MatChipsModule,
        MatMenuModule,
        MatRadioModule,
        MatDividerModule,
        MatDatepickerModule,
        MatDialogModule,
        MatNativeDateModule,
        MatTableModule]
})
export class DemoAngularMaterial{

}
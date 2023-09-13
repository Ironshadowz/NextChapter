import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTableModule} from '@angular/material/table'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';

const MATERIALS =
[
  CommonModule,
  MatTableModule,
  MatFormFieldModule,
  MatIconModule,
  MatSelectModule,
  MatRadioModule,
  MatButtonModule,
  MatInputModule,
  MatSortModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatListModule,
  MatPaginatorModule
]

@NgModule
({
  declarations: [],
  imports:
  [
    MATERIALS
  ],
  exports:
  [
    MATERIALS
  ]
})
export class NgMaterialModule { }

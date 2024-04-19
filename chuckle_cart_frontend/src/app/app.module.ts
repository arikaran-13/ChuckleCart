import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoAngularMaterial } from './DemoAngularMaterial';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from 'src/service/auth-service';
import { HttpClientModule } from '@angular/common/http';
import { StorageService } from 'src/service/storage-service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    DemoAngularMaterial,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [AuthService,StorageService],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { Component } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { AuthenticateRequest } from "src/model/AuthenticateRequest";
import { AuthenticateResponse } from "src/model/AuthenticateResponse";
import { User } from "src/model/user";
import { AuthService } from "src/service/auth-service";
import { StorageService } from "src/service/storage-service";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls:["./login.component.scss"]
})
export class LoginComponent{


    constructor(
        private fb:FormBuilder,
        private authService:AuthService,
        private snackbar:MatSnackBar,
        private storage:StorageService,
        private router:Router){

    }
    loginFormGroup = this.fb.group({
        email: ['',[Validators.required,Validators.required]],
        password: ['',[Validators.required]]
    })
    hidePassword:boolean = true;

    public togglePasswordVisibility(){
        this.hidePassword = !this.hidePassword;
    }

    public isLoginFormValid():boolean{
        return !this.loginFormGroup.valid;
    }
  
    public onSubmit(){
           let autheReq = new AuthenticateRequest(
            this.loginFormGroup.value.email,
            this.loginFormGroup.value.password);

        this.authService.login(autheReq).subscribe(
            (data:AuthenticateResponse)=>{
                this.storage.saveAuthToken(data.jwtToken);
                this.storage.saveLoggedInUser(data);
                console.log(data);
                if(this.storage.isAdminLoggedIn()){
                    this.router.navigateByUrl("/admin/dashboard");
                }
                else if ( this.storage.isCustomerLoggedIn()){
                    this.router.navigateByUrl("/customer/dashboard");
                }
                
            },
            (error)=>{
                this.snackbar.open('Bad Credentials','ERROR' , {duration: 4000});
            },
            ()=>{
               this.snackbar.open("Login Success","OK",{duration: 3000});
            }
        )
    }

    public onClickSignUpBtn(){
      this.router.navigateByUrl("/signup");
    }

}
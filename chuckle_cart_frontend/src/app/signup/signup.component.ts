import { Component } from "@angular/core";
import {MatSnackBar} from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators , FormControl } from '@angular/forms';
import { AuthService } from "src/service/auth-service";
import { User } from "../../model/user";
import { Router } from "@angular/router";
@Component({
    selector: "app-signup",
    templateUrl: "./signup.component.html",
    styleUrls: ["./signup.component.scss"]
})
export class SignUpComponent{

    hidePassword:boolean=true;
    hideConfirmPassword:boolean = true;
 
    signUpForm = this.fb.group({
        username: ['',[Validators.required]],
        email: ['',[Validators.required,Validators.email]],
        password: ['',Validators.required],
        confirmPassword: ['',Validators.required]
    });

    constructor(
        private snackBar:MatSnackBar,
        private fb:FormBuilder,
        private authService:AuthService,
        private router : Router){
    }
    
    toggleConfirmPasswordVisibility(){
        this.hideConfirmPassword = !this.hideConfirmPassword;
    }

    togglePasswordVisibility(){
        this.hidePassword = !this.hidePassword;
    }
    onClickRedirectToLogin(){
        this.router.navigateByUrl("/login")
    }

    onSubmit(){
        console.log('on submit called',this.signUpForm.value)
      
        if(this.signUpForm.value.password !== this.signUpForm.value.confirmPassword){
            this.snackBar.open("Password not matching","Close",{duration: 2000});
            return;
        }

        if(this.signUpForm.valid){
        let user = new User(
            this.signUpForm.value.username,
            this.signUpForm.value.email,
            this.signUpForm.value.password
            );

        this.authService.register(user).subscribe(
            (data: User)=>{
              console.log("User details registered: ",data)
            },
            (error)=>{
             console.log("Unkown error occured",error);
            },
            ()=>{
                this.snackBar.open("Singed Up Successfully" , "Close",{duration: 3000});
                this.router.navigateByUrl("/login");
            }
        )
        }
            
        
       
        
    }

    isFormValid():boolean{
        return !this.signUpForm.valid;
    }

}
import { KeyedRead } from "@angular/compiler";
import { Injectable } from "@angular/core";
import { AuthenticateResponse } from "src/model/AuthenticateResponse";
import { User } from "src/model/user";

@Injectable()
export class StorageService{
    
    public saveAuthToken(value:string){
        localStorage.setItem("TOKEN",value);
    }

    public getAuthToken():string{
        return localStorage.getItem("TOKEN");
    }

    public saveLoggedInUser(user:AuthenticateResponse):void{
        localStorage.setItem('USER',JSON.stringify(user));
    }

    public getCurrentLoggedInUser():AuthenticateResponse{
        return JSON.parse(localStorage.getItem('USER'));
    }

    public getUserRole():string{
        return this.getCurrentLoggedInUser().role;
    }

    public getUserEmail():string{
        return this.getCurrentLoggedInUser().email;
    }

    public getUserId():number{
        return this.getCurrentLoggedInUser().id;
    }

    public signOut():void{
        localStorage.removeItem("TOKEN");
        localStorage.removeItem("USER");
    }

    public isAdminLoggedIn():boolean{
       return this.getCurrentLoggedInUser()?.role === "ADMIN"
    }

    public isCustomerLoggedIn():boolean{
        return this.getCurrentLoggedInUser()?.role === "CUSTOMER"
     }
}
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthenticateRequest } from "src/model/AuthenticateRequest";
import { AuthenticateResponse } from "src/model/AuthenticateResponse";
import { User } from "src/model/user";

@Injectable()
export class AuthService{

    constructor(private httpClient: HttpClient){}

    private BASE_URL:string ="http://localhost:8080";

    public register(user:User):Observable<User>{
       return this.httpClient.post<User>(this.BASE_URL+"/api/auth/register",user);
    }

    public login(authenticate:AuthenticateRequest):Observable<AuthenticateResponse>{
        return this.httpClient.post<AuthenticateResponse>(this.BASE_URL+"/api/auth/login",authenticate);
    }

}
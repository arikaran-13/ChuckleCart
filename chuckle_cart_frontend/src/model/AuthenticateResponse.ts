export class AuthenticateResponse{
    constructor(public id:number,public name:string ,public role:string,public email:string , public password:string , public jwtToken:string){

    }
}
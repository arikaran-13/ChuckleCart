import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/service/storage-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
 
  constructor(private storageService:StorageService,
    private router:Router){

  }
  isAdminLoggedIn:boolean = this.storageService.isAdminLoggedIn();
  isCustomerLoggedIn:boolean = this.storageService.isCustomerLoggedIn();

  ngOnInit(){
    this.router.events.subscribe((event)=>{
      this.isAdminLoggedIn = this.storageService.isAdminLoggedIn();
      this.isCustomerLoggedIn = this.storageService.isCustomerLoggedIn();
    
    })
  }

  logout(){
    this.storageService.signOut();
    this.router.navigateByUrl("/login");
  }
  


}

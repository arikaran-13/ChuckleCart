import { Component } from '@angular/core';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent {

  orders:any;
  constructor(private adminService:AdminService,
    private snackBar:MatSnackBar
  ){}

  ngOnInit(){
    this.getPlacedOrders();
  }
  getPlacedOrders(){
    this.adminService.getAllPlacedOrders().subscribe(
      res => {
        console.log(res);
        this.orders = res;

      }
    )
  }

  changeOrderStatus(orderId:number,status:string){
    this.adminService.changeOrderStatus(orderId,status).subscribe(
      res =>{
        if(res.id!=null){
          this.snackBar.open("Order status changed successfully", "Close",{duration:3000});
          this.getPlacedOrders();
        }
        else{
          this.snackBar.open("Something went wrong", "Close",{duration:3000});

        }
      }
    )
  }


}

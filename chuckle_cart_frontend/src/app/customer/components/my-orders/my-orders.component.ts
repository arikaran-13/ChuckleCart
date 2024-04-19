import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.scss']
})
export class MyOrdersComponent {

  myOrders:any;
  constructor(private customerService:CustomerService){}

  ngOnInit(){
    this.getOrdersByUserId();
  }
  getOrdersByUserId(){
    this.customerService.getPlacedOrderByUserId().subscribe(
      res=>{
        console.log(res);
        this.myOrders = res;
      },
      err=>{
         console.log(err.error);
      }
    )
  }
}

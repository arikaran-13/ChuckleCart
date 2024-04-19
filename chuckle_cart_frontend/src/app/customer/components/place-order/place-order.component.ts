import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.scss']
})
export class PlaceOrderComponent {

  orderForm:FormGroup;

  constructor(
    private customerService:CustomerService,
    private router : Router,
    private snackBar:MatSnackBar,
    private dialog:MatDialog,
    private fb: FormBuilder
  ){}

  ngOnInit(){
    this.orderForm = this.fb.group({
      'address': ['',Validators.required],
      'orderDescription': [null],
    });
  }

  placeOrder(){
    console.log(this.orderForm.value);
    this.customerService.placeOrder(this.orderForm.value).subscribe(
      res=>{
        if(res.id!= null){
           this.snackBar.open("Order place successfully","Close",{duration:5000})
           this.router.navigateByUrl("/customer/my_orders");
           this.closeForm(); 
        }else{
          this.snackBar.open("Something went wrong","Close",{duration:5000})
        }
      }
    )
  }

  closeForm(){
    this.dialog.closeAll();
  }
}

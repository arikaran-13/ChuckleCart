import { Component } from '@angular/core';
import { StorageService } from 'src/service/storage-service';
import { CustomerService } from '../../services/customer.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PlaceOrderComponent } from '../place-order/place-order.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
  
  cartItemDetails :any[]=[];
  order:any;
  couponFrom:FormGroup;
  coupons:any[];

  constructor(private storageService: StorageService,
    private customerService:CustomerService,
    private snackBar:MatSnackBar,
    private db: FormBuilder,
    private dialog : MatDialog){
  }

  ngOnInit(){
    this.couponFrom = this.db.group({
      code: ['',Validators.required]
    })
    this.getCart();
    this.getAllCoupons();
  }

  getAllCoupons(){
    this.customerService.getAllCoupons().subscribe(
      res =>{
         this.coupons = res;
         console.log(res);
      },
      err=>{
        console.log(err.err);
      }
    )
  }

  applyCoupon(){  
    let couponCode = this.couponFrom.get('code')!.value;
     console.log("Coupon Code ",couponCode);
    this.customerService.applyCoupons(couponCode).subscribe(
      res =>{
          this.snackBar.open("Coupon applied successfully", "Close",{duration:5000});
          this.getCart();
      } , 
      err =>{
        console.log(err);
        this.snackBar.open(err.error,"Close",{duration: 5000})
      }
    )
  }

  getCart(){
    this.cartItemDetails=[];
    this.customerService.getCartByUserId().subscribe(
      data =>{
        this.order = data;
         console.log(this.order.cartItems[0]);
        data.cartItems.forEach(element=>{
          element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
          this.cartItemDetails.push(element); 
        })
      }
    )
  }

  addQuantityByOne(productId:number,quantity:number){
      console.log("add quantity")
      this.customerService.updateCartQuantity(productId,++quantity).subscribe(
        res =>{
           console.log(res);
           this.snackBar.open("product quantity increased","Close",{duration:3000});
           this.getCart();
        },
        err =>{
          console.log(err.error);
        }
      )
  }

  removeQuantityByOne(productId:number,quantity:number){
      console.log("remove quantity")
      this.customerService.updateCartQuantity(productId,--quantity).subscribe(
        res =>{
          this.snackBar.open("Product quantity decreased","Close",{duration:3000});
           console.log(res);
           this.getCart();
        },
        err =>{
          console.log(err.error);
        }
      )
  }

  placeOrder(){
   this.dialog.open(PlaceOrderComponent);
  }
}

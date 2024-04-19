import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product } from 'src/model/product';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent {
  constructor(
    private router:Router,
    private customerService:CustomerService, 
    private snackBar: MatSnackBar){}
    
    image:any;
    product:any[]=[];
    productDetails:any[]=[];
    wishlists:any;

    ngOnInit(){
      this.getWishListForUser();
    }

    getWishListForUser(){
       this.customerService.getAllWishListForUser().subscribe(
        res=>{
          console.log(res);
          this.wishlists = res;
          this.setByteImageToNormalImage(this.wishlists);
        }
       )
    }

    setByteImageToNormalImage(wishlists:any[]){
      wishlists.forEach(element => {
        element.processImage = "data:image/jpeg;base64,"+ element.img;
      });
    }

    public encode(data:any):any{ 
      return encodeURIComponent(JSON.stringify(data));
    }

   public addToCart(productId:any){
      this.customerService.addToCart(productId).subscribe(
        (res)=> {
          this.snackBar.open("Prodcut added to cart Successfully ", "Close",{duration: 5000});
        },
        (err)=>{
           console.log(err);
        }
      )
   }
}

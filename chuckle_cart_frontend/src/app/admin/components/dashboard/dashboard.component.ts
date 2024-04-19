import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Product } from 'src/model/product';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  constructor(
    private router:Router,
    private adminService:AdminService, 
    private snackBar: MatSnackBar){}

    image:any;
    product:any[]=[];
    productDetails:any[]=[];

    ngOnInit(){
      this.getAllProduct();
    }

    displaySearchedProducts(seachBar:any){
      if(seachBar.value === ''){
        this.getAllProduct();
      }
      else{
       this.adminService.getProductBySearchName(seachBar.value).subscribe(
        (data:Product[]) =>{
          this.product = data;
          this.setByteImageToNormalImage(this.product);
          console.log(this.product);
        },
        (error)=>{
          console.log(error.message);
        },
      )
      }
    }

    getAllProduct(){
       this.adminService.getAllProduct().subscribe(
        (data:any)=>{
          this.product=data;
          this.setByteImageToNormalImage(this.product);
          console.log(this.product)
        }
       )
    }

    setByteImageToNormalImage(product:any[]){
      product.forEach(element => {
        element.processImage = "data:image/jpeg;base64,"+ element.byteImg;
      });
    }

    updateProductDetails(productId:number){
      this.router.navigateByUrl(`/admin/products?productId=${this.encode(productId)}`)
    }

    public encode(data:any):any{ 
      return encodeURIComponent(JSON.stringify(data));
    }

    deleteProduct(id:number , productName: string){
      this.adminService.deleteProduct(id).subscribe(
         (res)=>{
          if(res === true){
            this.snackBar.open(`${productName} Delete successfully` , "Ok" , {duration: 3000})
            this.getAllProduct();
          }
          else{
            this.snackBar.open(`Unable to delete ${productName}` , "ERROR",{duration:3000})
          }
         },
         (error)=>{
           console.log(error);
         },
         ()=>{

         }
      )
  }
}

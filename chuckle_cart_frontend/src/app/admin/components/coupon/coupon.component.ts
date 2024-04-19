import { Component } from '@angular/core';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-coupon',
  templateUrl: './coupon.component.html',
  styleUrls: ['./coupon.component.scss']
})
export class CouponComponent {

  datasource:any[];
  constructor(private adminService:AdminService,private snackBar:MatSnackBar){
  }

  displayedColumns:any[] = ['No',"Name","Discount %", "Coupon code","Action"];

  ngOnInit(){
    this.getCoupons();
  }

  getCoupons(){
    this.datasource=[];
    this.adminService.getAllCoupons().subscribe(
      res=>{
        console.log(res);
         this.datasource = res;
         console.log(this.datasource);
         console.log('res: ',res);
      },
      (err)=>{
        console.log(err)
      }
    )
  }

  deleteCoupon(id){
    this.adminService.deleteCouponById(id).subscribe(
      (res)=>{
         this.snackBar.open("Coupon Deleted Successfully","Ok",{duration:3000});
         this.getCoupons();
      },
      (err)=>{
        console.log(err);
      }
    );
    
  }
}

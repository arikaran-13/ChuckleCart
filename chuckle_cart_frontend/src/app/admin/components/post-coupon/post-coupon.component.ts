import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-coupon',
  templateUrl: './post-coupon.component.html',
  styleUrls: ['./post-coupon.component.scss']
})
export class PostCouponComponent {
  
  constructor(private fb:FormBuilder,
    private adminService:AdminService,
    private snackBar:MatSnackBar,
    private router: Router){}
  postCouponForm: FormGroup;
  ngOnInit(){
    this.postCouponForm = this.fb.group({
       'name' : ['',Validators.required],
       'code' : ['',Validators.required],
       'discount' : ['',Validators.required],
       'date': ['',Validators.required],
    });
  }

  createCoupon():void{
     if(this.postCouponForm.valid){
      let coupon ={
        'name': this.postCouponForm.get('name').value,
        'code': this.postCouponForm.get('code').value,
        'discount': this.postCouponForm.get('discount').value,
        'expirationDate': this.postCouponForm.get('date').value
      }
      this.postCoupon(coupon);
     }
  }

  postCoupon(coupon:any):void{
    this.adminService.createCoupon(coupon).subscribe(
      res =>{
        this.snackBar.open('Coupon created successfully','Ok' , {duration: 5000});
        this.router.navigateByUrl('/admin/dashboard')
      },
      err=>{
        console.log(err);
      }
    )
  }
}

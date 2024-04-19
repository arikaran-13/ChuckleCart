import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AdminService } from './service/admin-service';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { DemoAngularMaterial } from '../DemoAngularMaterial';
import { ReactiveFormsModule } from '@angular/forms';
import { PostProductComponent } from './components/post-product/post-product.component';
import { CouponComponent } from './components/coupon/coupon.component';
import { PostCouponComponent } from './components/post-coupon/post-coupon.component';
import { OrdersComponent } from './components/orders/orders.component';


@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    PostCategoryComponent,
    PostProductComponent,
    CouponComponent,
    PostCouponComponent,
    OrdersComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule,
    DemoAngularMaterial
  ],
  providers:[AdminService]
})
export class AdminModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomerRoutingModule } from './customer-routing.module';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { CustomerService } from './services/customer.service';
import { DemoAngularMaterial } from '../DemoAngularMaterial';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CartComponent } from './components/cart/cart.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';


@NgModule({
  declarations: [
    CustomerComponent,
    DashboardComponent,
    CartComponent,
    PlaceOrderComponent,
    MyOrdersComponent,
    WishlistComponent
  ],
  imports: [
    CommonModule,
    CustomerRoutingModule,
    FormsModule,
    HttpClientModule,
    DemoAngularMaterial,
    ReactiveFormsModule
  ],
  providers: [CustomerService]
})
export class CustomerModule { }

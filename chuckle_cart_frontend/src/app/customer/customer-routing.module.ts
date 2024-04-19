import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerComponent } from './customer.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CartComponent } from './components/cart/cart.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';

const routes: Routes = [
  { path: '', component: CustomerComponent },
  {path: 'dashboard' , component: DashboardComponent},
  {path : 'cart', component: CartComponent},
  {path : 'my_orders', component: MyOrdersComponent},
  {path : 'wishlist', component: WishlistComponent},


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }

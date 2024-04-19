import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/model/product';
import { StorageService } from 'src/service/storage-service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient,private storageService: StorageService) { }
 
  BASE_URL:string = "http://localhost:8080/api/customer";

  public getAllProduct():Observable<Product[]>{
   return this.http.get<Product[]>(this.BASE_URL+"/products",{
    headers : this.createHttpHeaders()
   });
  }

  public getAllWishListForUser():Observable<any[]>{
    return this.http.get<any[]>(
      this.BASE_URL+`/user/wishlist/${this.storageService.getUserId()}`,
      {headers: this.createHttpHeaders()}
    );
  }

  public addToCart(productId:any):Observable<any>{
    const cart = {
      productId: productId,
      userId: this.storageService.getUserId()
    }
    return this.http.post<any>(this.BASE_URL+"/cart", cart,{
     headers : this.createHttpHeaders()
    });
   }

   public placeOrder(orderDto:any):Observable<any>{
    orderDto.userId = this.storageService.getUserId();
    return this.http.post<any>(this.BASE_URL+"/placeOrder", orderDto,{
     headers : this.createHttpHeaders()
    });
   }

  public getProductByName(name:String):Observable<Product[]>{
    return this.http.get<Product[]>(this.BASE_URL+`/products/${name}`,{
      headers: this.createHttpHeaders()
    });
  }

  public getCartByUserId():Observable<any>{
    return this.http.get<any>(this.BASE_URL+ `/cart/${this.storageService.getUserId()}`,{
      headers: this.createHttpHeaders()
    });
  }

  public getPlacedOrderByUserId():Observable<any>{
    return this.http.get<any>(this.BASE_URL+ `/orders/${this.storageService.getUserId()}`,{
      headers: this.createHttpHeaders()
    });
  }

  public applyCoupons(code:any):Observable<any>{
    return this.http.get<any>(this.BASE_URL+ `/coupon/${this.storageService.getUserId()}/${code}`,{
      headers: this.createHttpHeaders()
    });
  }

  public getAllCoupons():Observable<any>{
    return this.http.get<any>(this.BASE_URL+ `/coupon/all`,{
      headers: this.createHttpHeaders()
    });
  }

  public updateCartQuantity(productId:number,quantity:number):Observable<any>{
    const updateCartQuantityForActiveOrder = {
       'productId': productId,
       'userId': this.storageService.getUserId(),
       'quantity': quantity
    };
    return this.http.put<any>(
      this.BASE_URL+'/cart',
      updateCartQuantityForActiveOrder,
      {
      headers: this.createHttpHeaders()
      }
  )
  }

  public addProductToWishList(productId:number,fav:string){
    const wishlist = {
      'userId': this.storageService.getUserId(),
      'productId':productId,
      'fav': fav
    };
    return this.http.post<any>(this.BASE_URL+'/wishlist',wishlist,{
      headers: this.createHttpHeaders()
    });
  }

private createHttpHeaders():HttpHeaders{
  return new HttpHeaders().set(
      "Authorization" , this.storageService.getAuthToken()
  );   
  }

}

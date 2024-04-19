import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Category } from "src/model/Category";
import { Product } from "src/model/product";
import { SearchProductDetails } from "src/model/searchProductDetails";
import { StorageService } from "src/service/storage-service";

@Injectable()
export class AdminService{

    public BASE_URL:string = "http://localhost:8080/api/admin"
    constructor(private httpClient:HttpClient,private storage:StorageService){

    }

    public createCategory(category:Category):Observable<Category>{
        return this.httpClient.post<Category>(this.BASE_URL+"/create/category",category,{
            headers: this.createHttpHeaders()
        })
    }

    public getAllCategory():Observable<Category[]>{
        return this.httpClient.get<Category[]>(this.BASE_URL+"/category",{
            headers: this.createHttpHeaders()
        })
    }

    public getAllPlacedOrders():Observable<any[]>{
        return this.httpClient.get<any[]>(this.BASE_URL+"/placedOrder",{
            headers: this.createHttpHeaders()
        })
    }

    public getAllProducts():Observable<any>{
        return this.httpClient.get<any>(this.BASE_URL+"/product/all",{
            headers: this.createHttpHeaders()
        })
    }

    public changeOrderStatus(orderId:number,status:string):Observable<any>{
        return this.httpClient.get<any>(this.BASE_URL+`/order/${orderId}/${status}`,{
            headers: this.createHttpHeaders()
        })
    }

    public createProduct(product:any):Observable<any>{
        return this.httpClient.post<any>(this.BASE_URL+"/product/create",product,{
            headers: this.createHttpHeaders()
        })
    }

    public deleteProduct(productId:any):Observable<boolean>{
        return this.httpClient.delete<any>(this.BASE_URL+`/product/delete/${productId}`,{
            headers: this.createHttpHeaders()
        })
    }

    public getAllProduct():Observable<Product[]>{
        return this.httpClient.get<Product[]>(this.BASE_URL+"/product/all",{
            headers: this.createHttpHeaders()
        })
    }

    public getProduct(productId:number):Observable<Product>{
        return this.httpClient.get<Product>(this.BASE_URL+`/product/${productId}`,{
            headers: this.createHttpHeaders()
        })
    }

    public getProductBySearchName(data:string){
        let search = new SearchProductDetails(data);
        return this.httpClient.get<Product[]>(this.BASE_URL+`/product/search/${data}`,{
            headers: this.createHttpHeaders()
        })
    }

    public createCoupon(coupon:any):Observable<any>{
        return this.httpClient.post<any>(this.BASE_URL+"/coupon",coupon,{
            headers: this.createHttpHeaders()
        })
    }

    public getAllCoupons():Observable<any[]>{
        return this.httpClient.get<any[]>(this.BASE_URL+`/coupon/all`,{
            headers: this.createHttpHeaders()
        })
    }

    public deleteCouponById(id:number):Observable<any>{
        return this.httpClient.delete<any>(this.BASE_URL+`/coupon/${id}`,{
            headers: this.createHttpHeaders()
        })
    }

    private createHttpHeaders():HttpHeaders{
    return new HttpHeaders().set(
        "Authorization" , this.storage.getAuthToken()
    );   
    }

}

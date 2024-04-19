import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/model/Category';
import { StorageService } from 'src/service/storage-service';
import { AdminService } from '../../service/admin-service';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrls: ['./post-product.component.scss']
})
export class PostProductComponent {

productForm:FormGroup;
listOfCategories:Category[];
selectedFile:File|null;
imagePreview: string | ArrayBuffer | null;
selectedProductId:string=null;

constructor(
  private fb: FormBuilder,
  private router: Router,
  private service: AdminService,
  private snackBar: MatSnackBar,
  private route: ActivatedRoute
){}

 formData = new FormData();

onFileSelected(event:any){
  this.selectedFile = event.target.files[0];
  this.previewImage();
}

previewImage(){
  if(this.selectedFile instanceof File){
  const reader = new FileReader();
  reader.onload = ()=>{
    this.imagePreview = reader.result;
  }
  reader.readAsDataURL(this.selectedFile);
}
}

ngOnInit():void{
  this.productForm = this.fb.group({
    categoryId: ['',Validators.required],
    name: ['',Validators.required],
    price: ['',Validators.required],
    description: ['',Validators.required] 
  });
  this.getAllCategories();

}

ngAfterViewInit(){
  this.route.queryParams.subscribe(params=>{
    const qProduct = params['productId'];
    const decodedProductId = decodeURIComponent(qProduct);
    this.selectedProductId = decodedProductId;
    let productDetails = this.service.getProduct(parseInt(decodedProductId)).subscribe(
      data =>{
        this.setProductFormDetails(data);
      }
    );
    console.log('from ng after view init', decodedProductId , productDetails);
  })
}

  getAllCategories() {
    this.service.getAllCategory().subscribe(
      (data:Category[])=>{
        this.listOfCategories = data;
      }
    )
  }

  setProductFormDetails(product:any){
    console.log("set product details")
    console.log(product);
    // this.imagePreview = "data:image/jpeg;base64,"+product.byteImg
    if(this.selectedProductId){
      console.log("select product id if block",this.selectedProductId);
      this.formData.append('id',this.selectedProductId);
    }
    this.productForm.get('description').setValue(product.description);
    this.productForm.get('price').setValue(product.price);
    this.productForm.get('categoryId').setValue(product.category_id);
    this.productForm.get('name').setValue(product.name);

    // this.previewImage();
  }
  addProduct() : void{
    if(this.productForm.valid){
    this.formData.append('img',this.selectedFile);
    this.formData.append('category_id',this.productForm.get('categoryId').value)
    this.formData.append('name',this.productForm.get('name').value);
    this.formData.append('price',this.productForm.get('price').value);
    this.formData.append('description',this.productForm.get('description').value);

    this.service.createProduct(this.formData).subscribe(
      (data)=>{
        console.log(data);
        if(data.id != null){
          this.snackBar.open("Product posted Successfully!" ,"Ok", {duration:3000});
          this.router.navigateByUrl('/admin/dashboard');
        }
        else{
          this.snackBar.open(data.message,"ERROR",{duration: 3000});
        }
      }
    )

    }else{
      for(let i in this.productForm.controls){
        this.productForm.controls[i].markAsDirty();
        this.productForm.controls[i].updateValueAndValidity();
      }
    }
  }
}

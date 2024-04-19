import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from '../../service/admin-service';
import { Router } from '@angular/router';
import { Category } from 'src/model/Category';

@Component({
  selector: 'app-post-category',
  templateUrl: './post-category.component.html',
  styleUrls: ['./post-category.component.scss']
})
export class PostCategoryComponent {

  postCategoryFormGroup = this.fb.group({
    name: ['',Validators.required],
    description: ['',[Validators.required]]
  })

  constructor(private fb:FormBuilder, 
    private snackBar:MatSnackBar,
    private service: AdminService,
    private router:Router){

  }

  onCategorySubmit(){
     
    let category = new Category(
      this.postCategoryFormGroup.value.name,
      this.postCategoryFormGroup.value.description
      );

      if(this.postCategoryFormGroup.valid){

      this.service.createCategory(category).subscribe(
        (res:Category)=>{

            
      },
      (error)=>{
        this.snackBar.open(`${error}`,"ERROR",{duration:3000});
      },
      ()=>{
        this.snackBar.open("Category created successfully","Ok",{duration: 3000});
        this.router.navigateByUrl("/admin/dashboard")
      
      }
      )
    }else{
      this.postCategoryFormGroup.markAllAsTouched();
    }
  }
}

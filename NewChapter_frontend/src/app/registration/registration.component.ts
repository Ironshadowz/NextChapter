import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BackendService } from '../backend.service';
import { Registration } from '../user';
import { HttpService } from '../http.service';

@Component
({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent
{
  form!:FormGroup
  sub!:Subscription
  password1!:string
  password2!:string
  samepassword!:string
  newuser!:Registration
  errorMsg!:String

  private fb = inject(FormBuilder)
  private router = inject(Router)
  private servicer = inject(BackendService)

  ngOnInit()
  {
    this.form = this.fb.group
        ({
           username: this.fb.control<string>('', Validators.required),
           email: this.fb.control<string>('', [Validators.required, Validators.email]),
           password1: this.fb.control<string>('', [Validators.required, Validators.minLength(8)]),
           password2: this.fb.control<string>('', [Validators.required, Validators.minLength(8)]),
           country: this.fb.control<string>('', Validators.required),
           city: this.fb.control<string>('', Validators.required)
        })
  }
  submit()
  {
    this.newuser = this.form.value
    this.sub = this.servicer.registration(this.newuser).subscribe
                ({
                  next: (result)=>
                  {
                    if(result.token!=null)
                    {
                      alert("Account created")
                      this.router.navigate(['/'])
                    }
                  },
                  error: (e)=>
                  {
                    this.ngOnInit()
                    this.errorMsg='The user already exists'
                  }
                })
  }
  back()
  {
    this.router.navigate(['/'])
  }
  checkSame(password: string)
  {
    this.password2 = password;
    if (this.password2 === this.password1)
    {
      this.samepassword=''
    } else
    {
      this.samepassword='password does not match'
    }
  }

}

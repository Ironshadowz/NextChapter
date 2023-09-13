import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BackendService } from '../backend.service';
import { Subscription } from 'rxjs';
import { User } from '../user';
import { Token } from '@angular/compiler';
import { HttpService } from '../http.service';

@Component
({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent
{
  form!:FormGroup
  sub!:Subscription
  login!:Boolean
  loginfail!:Boolean
  user!:User

  private fb = inject(FormBuilder)
  private router = inject(Router)
  private servicer = inject(BackendService)
  private auth = inject(HttpService)

  ngOnInit()
  {
    this.form = this.fb.group
        ({
           username: this.fb.control<string>('', Validators.required),
           password: this.fb.control<string>('', [Validators.required, Validators.minLength(8)])
        })
  }
  submit()
  {
    this.user = this.form.value
    console.log(typeof this.user)
    this.sub = this.servicer.login(this.user).subscribe
            ({
              next: (result)=>
              {
                this.auth.setAuthToken(result.token)
                this.router.navigate(['/main', this.user.username])
              },
              error: (e)=>
              {
                this.auth.setAuthToken(null)
                alert('Invalid password')
              }
            })
  }
  create()
  {
    this.router.navigate(['/registration'])
  }
}


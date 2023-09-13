import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BackendService } from '../backend.service';
import { Subscription } from 'rxjs';
import { HttpService } from '../http.service';
import { Setting } from '../user';

@Component
({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent
{
  @ViewChild('toupload')
  toupload!:ElementRef

  form!:FormGroup
  sub:Subscription = Subscription.EMPTY
  sub2:Subscription = Subscription.EMPTY
  sub3?:Subscription
  name!:string
  setting!:Setting // = { name:'', frontSize : 24, weatherEffect : true, weather : 'something'}
  errorMsg!:string
  formData = new FormData()
  weather!:string

  private route = inject(ActivatedRoute)
  private fb = inject(FormBuilder)
  private router = inject(Router)
  private servicer = inject(BackendService)
  private auth = inject(HttpService)

  ngOnInit()
  {
    this.form = this.fb.group
              ({
                frontSize: this.fb.control<number>(0, [Validators.required, Validators.min(14), Validators.max(40)]),
                weatherEffect: this.fb.control<Boolean>(true, Validators.required),
                myfile : ['']
              })
    this.name = String(this.route.snapshot.paramMap.get('name'))
    this.sub = this.auth.request('GET', '/setting/'+this.name, null).subscribe
                ({
                  next: (result)=>
                  {
                    this.setting=result
                    document.body.style.fontSize = this.setting.frontsize + 'px';
                    console.log("Getting result "+this.setting.frontsize)
                    if(result!=null)
                    {
                      console.log("initialise form")
                      this.form = this.fb.group
                      ({
                        frontSize: this.fb.control<number>(this.setting.frontsize, [Validators.required, Validators.min(14), Validators.max(40)]),
                        weatherEffect: this.fb.control<Boolean>(this.setting.weatherEffect, Validators.required),
                        myfile : ['']
                      })
                      console.log("Weather here "+this.setting.weather)
                    }
                  },
                  error: (e)=>
                  {
                    this.errorMsg='Cannot get user setting'
                    console.log(this.errorMsg)
                  }
                })
  }
  onSubmit(formdata : FormData)
  {
    //this.setting = this.form.value
    //console.log("save setting "+this.setting.frontSize)
    this.sub2 = this.auth.request('PUT', '/setting/'+this.name, formdata).subscribe
   // this.sub2 = this.servicer.updateSetting(this.name, formdata).subscribe
                ({
                    next: (result)=>
                    {
                      if(result==true)
                        {
                          this.ngOnInit()
                          this.formData.delete
                          alert('Setting updated')
                        }
                    },
                    error: (e)=>
                    {
                      alert('Error updating setting')
                    }
                })
  }
  save()
  {
    console.log(this.toupload.nativeElement[0])
    this.formData.set('frontSize', this.form.value['frontSize'])
    this.formData.set('weatherEffect', this.form.value['weatherEffect'])
    //this.formData.set('myfile', this.toupload.nativeElement[0])
    //Access the file input element and its files property
    const files = this.toupload.nativeElement.files;

     if (files && files.length > 0)
     {
        this.formData.set('myfile', files[0]);
     }
    this.onSubmit(this.formData)
  }
  logout()
  {
    this.auth.setAuthToken(null);
    this.router.navigate(['/']);
  }
  ngOnDestroy()
  {
    this.sub.unsubscribe()
    this.sub2.unsubscribe()
    this.sub3?.unsubscribe()
  }
}

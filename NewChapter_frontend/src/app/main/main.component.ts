import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { BackendService } from '../backend.service';
import { Novel, Setting, UpdateChapter } from '../user';
import { Location } from '@angular/common';
import { HttpService } from '../http.service';
import { HttpHeaders } from '@angular/common/http';

@Component
({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent
{
  name!:string | null
  sub!:Subscription
  sub2:Subscription = Subscription.EMPTY
  sub3:Subscription = Subscription.EMPTY
  sub4:Subscription = Subscription.EMPTY
  updates!:Novel[]
  addchapter:Boolean=false
  updatedchapter!:Boolean
  added!:string
  value = ''
  nextchapter!:UpdateChapter
  errorMsg!:string
  setting :Setting = { name:'', frontsize : 24, weatherEffect : true, weather : 'something', imageUrl:''}

  private route = inject(ActivatedRoute)
  private backServ = inject(BackendService)
  private router = inject(Router)
  private locat = inject(Location)
  private auth = inject(HttpService)

  ngOnInit()
  {
    this.name = this.route.snapshot.paramMap.get('username')
    console.log(this.name)
    this.sub4 = this.auth.request('GET', '/setting/'+this.name, null).subscribe
                ({
                  next: (result)=>
                  {
                    this.setting=result
                    document.body.style.fontSize = this.setting.frontsize + 'px';
                    console.log("Getting result "+this.setting.frontsize)
                  },
                  error: (e)=>
                  {

                  }
                })
    const header = new HttpHeaders().set("Authorization", localStorage.getItem("nextchapter") as string)
    this.sub=this.auth.request('GET', '/home/'+this.name, null).subscribe
                ({
                  next: (result)=>
                  {
                    this.updates=result
                  },
                  error: (e)=>
                  {
                    this.errorMsg='Cannot get chapters'
                  }
                })
  }
  update(value: string)
  {
    this.value = value
  }
  add()
  {
    this.addchapter=true;
  }
  addnewchapter()
  {
    console.log('addnewchapter '+this.value)
    this.sub2 = this.auth.request('POST', '/addLink/'+this.name, this.value).subscribe
                ({
                  next: (result)=>
                  {
                    if(result==true)
                    {
                      console.log('Chapter added')
                      alert('Chapter added')
                      this.ngOnInit()
                      this.added=''
                    }
                    else
                    {
                      this.added = result
                      console.log(this.added)
                      alert('Item already exists')
                    }
                  },
                  error: (e)=>
                  {
                    alert('Error adding chapter')
                  }
                })
  }
  updatechapter(nextchapter:string, title:string)
  {
    console.log(this.name+' '+title)
    let chapter:UpdateChapter =
    {
      url: nextchapter,
      title: title,
      name: this.name as string
    }
    this.sub3 = this.auth.request('PUT', '/updatechapter', chapter).subscribe
    //this.backServ.updatechapter(chapter).subscribe
                ({
                  next: (result)=>
                  {
                    this.updatedchapter=result
                    console.log(result)
                    if(result==true)
                    window.location.href=nextchapter
                  },
                  error: (e)=>
                  {
                    this.errorMsg='Error updating chapter'
                  }
                })
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
    this.sub3.unsubscribe()
    this.sub4.unsubscribe()
  }
}

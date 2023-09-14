import { Component, inject } from '@angular/core';
import { BackendService } from '../backend.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Novel, Setting } from '../user';
import { HttpService } from '../http.service';

@Component
({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent
{
  name!:string | null
  novels!:Novel[]
  errorMsg!:string
  addchapter:Boolean=false
  added!:string
  value = ''
  sub!:Subscription
  sub2:Subscription = Subscription.EMPTY
  sub3:Subscription = Subscription.EMPTY
  sub4:Subscription = Subscription.EMPTY
  setting :Setting = { name:'', frontsize : 24, weatherEffect : false, weather : '', imageUrl:''}

  private backServ = inject(BackendService)
  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private auth = inject(HttpService)

  ngOnInit()
  {
    this.name = this.route.snapshot.paramMap.get('name')
    this.sub = this.auth.request('GET', '/setting/'+this.name, null).subscribe
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
    this.sub2 = this.backServ.getAllNovel(this.name as string).subscribe
                (
                  result=>
                  {
                    if(result!=null)
                    this.novels=result
                    else
                    this.errorMsg='No items to display'
                  }
                )
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
    this.addchapter=false
    console.log('addnewchapter '+this.value)
    this.sub3 = this.auth.request('POST', '/addLink/'+this.name, this.value).subscribe
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
  delete(title:string)
  {
    this.sub4 = this.backServ.deleteNovel(title, this.name as string).subscribe
                (
                  result=>
                  {
                    if(result==true)
                    {
                      this.ngOnInit()
                    }
                  }
                )
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

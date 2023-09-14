import { Component, inject } from '@angular/core';
import { BackendService } from '../backend.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Novel } from '../user';
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
  sub!:Subscription
  novels!:Novel[]
  errorMsg!:string
  sub2:Subscription = Subscription.EMPTY

  private backServ = inject(BackendService)
  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private auth = inject(HttpService)

  ngOnInit()
  {
    this.name = this.route.snapshot.paramMap.get('name')
    this.sub = this.backServ.getAllNovel(this.name as string).subscribe
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
  delete(title:string)
  {
    this.sub2 = this.backServ.deleteNovel(title, this.name as string).subscribe
                (
                  result=>
                  {
                    if(result==true)
                    {
                      this.ngOnInit
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
  }
}

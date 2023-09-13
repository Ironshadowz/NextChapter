import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { AuthUser, Novel, Registration, Setting, UpdateChapter, User } from './user';

@Injectable
({
  providedIn: 'root'
})
export class BackendService
{

  headers!: any;
  constructor() { }
  private http = inject(HttpClient)

  getAuthToken(): string | null {
    return window.localStorage.getItem("auth_token");
  }

  setAuthToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem("auth_token", token);
    }
    else {
      window.localStorage.removeItem("auth_token");
    }
  }

  login(form:User)
  {
    console.log("login service")
    return this.http.post<AuthUser>('/authenticate', form)
  }



    // request(method: string, url: string, data: any): Observable<any> {
    //   let headers: any;

    //   if (this.getAuthToken() !== null) {
    //     headers = new HttpHeaders().set('Authorization', `Bearer ${this.getAuthToken()}`);
    //   }

    //   switch(method.toLowerCase()) {
    //     case 'get':
    //         return this.httpClient.get<any>(url, {headers: headers});
    //       break;
    //     case 'post':
    //         return this.httpClient.post<any>(url, data, {headers: headers});
    //       break;
    //     case 'put':
    //         return this.httpClient.put<any>(url, data, {headers: headers});
    //       break;
    //     default:
    //         return this.httpClient.delete<any>(url);
    //   }
    // }


  registration(newuser:Registration)
  {
    if (this.getAuthToken() !== null) {
      this.headers = new HttpHeaders().set('Authorization', `Bearer ${this.getAuthToken()}`);
    }
    console.log("Registration service")
    return this.http.post<AuthUser>('/registeruser', newuser, {headers: this.headers})
  }
  getNewUpdates(username:string)
  {
    console.log("newupdate service")
    return this.http.get<Novel[]>('/home/'+username)
  }
  addchapter(url:string, username:string)
  {
    console.log("Add Chapter Service")
    return this.http.post<Boolean>('/addLink/'+username, url)
  }
  updatechapter(nextchapter:UpdateChapter)
  {
    console.log("Update chapter service")
    return this.http.put<Boolean>('/updatechapter', nextchapter)
  }
  getAllNovel(name:string)
  {
    console.log("getAllNovel service")
    return this.http.get<Novel[]>('/watchlist/'+name)
  }
  deleteNovel(title:string, name:string)
  {
    console.log('deleteNovel service')
    return this.http.put<Boolean>('/delete/'+name, title)
  }
  getSetting(name:string)
  {
    console.log('getSetting service')
    return this.http.get<Setting>('/setting/'+name)
  }
  updateSetting(name:string, data:any)
  {
    return this.http.put<any>('/setting/'+name, data)
  }
}


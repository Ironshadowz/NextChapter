import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { Observable, firstValueFrom } from 'rxjs';

@Injectable
({
  providedIn: 'root'
})
export class HttpService
{
  constructor(private httpClient: HttpClient) { }

  getAuthToken(): string | null
  {
    return window.localStorage.getItem("nextchapter");
  }

  setAuthToken(token: string | null): void
  {
    if (token !== null)
    {
      window.localStorage.setItem("nextchapter", token);
    }
    else
    {
      window.localStorage.removeItem("nextchapter");
    }
  }

  request(method: string, url: string, data: any): Observable<any>
  {
    let headers: any;
    console.log("request method")
    if (this.getAuthToken() !== null)
    {
      headers = new HttpHeaders().set('Authorization', `Bearer ${this.getAuthToken()}`);
    }
    console.log("request method before switch")
    switch(method.toLowerCase())
    {
      case 'get':
          return this.httpClient.get<any>(url, {headers: headers});
        break;
      case 'post':
          return this.httpClient.post<any>(url, data, {headers: headers});
        break;
      case 'put':
          return this.httpClient.put<any>(url, data, {headers: headers});
        break;
      default:
          return this.httpClient.delete<any>(url);
    }
  }
  upload(description:string, elementRef:ElementRef):Promise<any>
  {
    console.log("Service section-------")
    const data = new FormData()
    data.set("myfile", elementRef.nativeElement.files[0])
    return firstValueFrom(this.httpClient.post<any>('/upload', data))
  }
}

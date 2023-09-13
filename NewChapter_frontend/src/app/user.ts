import { SettingComponent } from './setting/setting.component';
import { RegistrationComponent } from './registration/registration.component';
export interface User
{
  username:string
  password:string
}
export interface Novel
{
  image:string
  url:string
  title:string
}
export interface UpdateChapter
{
  url:string
  title:string
  name:string
}
export interface Registration
{
  username:string
  email:string
  password1:string
  password2:string
  country:string
  city:string
}
export interface Setting
{
  name:string
  frontsize:number
  weatherEffect:boolean
  imageUrl:string
  weather:string
}
export interface AuthUser
{
  id:string
  username:string
  token:string
}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { RegistrationComponent } from './registration/registration.component';
import { WatchlistComponent } from './watchlist/watchlist.component';
import { SettingComponent } from './setting/setting.component';

const routes: Routes =
[
  {path:'', component:LoginComponent},
  {path:'main/:username', component:MainComponent},
  {path:'registration', component:RegistrationComponent},
  {path:'watchlist/:name', component:WatchlistComponent},
  {path:'settings/:name', component:SettingComponent}
];

@NgModule
({
  imports: [RouterModule.forRoot(routes, {useHash:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './pages/contact/contact.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import {ProfileComponent} from './pages/profile/profile.component';
import { StatusComponent } from './pages/status/status.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { SettingsComponent } from './pages/settings/settings.component';
import {RegisterComponent} from './pages/register/register.component';
import {LoginComponent} from './pages/login/login.component';
import { AuthGuard } from './_helpers/auth.guard';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'welcome', canActivate: [AuthGuard] },
  { path: 'welcome', component: WelcomeComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'sign-in', component: SignInComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'status', component: StatusComponent},
  { path: 'settings', component : SettingsComponent},
  { path: 'register', component : RegisterComponent},
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

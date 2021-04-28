import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ContactComponent } from './pages/contact/contact.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import {ProfileComponent} from './pages/profile/profile.component';
import { StatusComponent } from './pages/status/status.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { SettingsComponent } from './pages/settings/settings.component';
import {RegisterComponent} from './pages/register/register.component';
import { HomeComponent } from './pages/home';
import { LoginComponent } from './pages/login';
import { AuthGuard } from './_helpers';
import {AppComponent} from '@app/app.component';

const routes: Routes = [
//  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '', component: AppComponent, canActivate: [AuthGuard] },
  {path: 'login', component: LoginComponent},
 // {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  { path: 'welcome', component: WelcomeComponent},
  { path: 'profile', component: ProfileComponent},
    { path: 'contact', component: ContactComponent},
    { path: 'sign-in', component: SignInComponent},
    { path: 'status', component: StatusComponent},
    { path: 'settings', component : SettingsComponent},
    { path: 'register', component : RegisterComponent},

    // otherwise redirect to home
    { path: '**', redirectTo: 'register' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
    exports: [RouterModule]
})
export class AppRoutingModule { }

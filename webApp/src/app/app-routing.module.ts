import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './pages/contact/contact.component';
import {ProfileComponent} from './pages/profile/profile.component';
import { StatusComponent } from './pages/status/status.component';
import { SettingsComponent } from './pages/settings/settings.component';
import {RegisterComponent} from './pages/register/register.component';
import { LoginComponent } from './pages/login';
import { AuthGuard } from './_helpers';

const routes: Routes = [
  { path: '', redirectTo: '/profile', pathMatch: 'full'},
  {path: 'profile', component: ProfileComponent},
  {path: 'login', component: LoginComponent},
    { path: 'contact', component: ContactComponent},
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

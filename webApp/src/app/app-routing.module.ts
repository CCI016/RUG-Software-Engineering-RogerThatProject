import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from "./pages/contact/contact.component";
import { SignInComponent } from './pages/sign-in/sign-in.component';
import {ProfileComponent} from "./pages/profile/profile.component";
import { StatusComponent } from './pages/status/status.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { SettingsComponent } from './pages/settings/settings.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'welcome' },
  { path: 'welcome', component: WelcomeComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'sign-in', component: SignInComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'status', component: StatusComponent},
  { path: 'settings', component : SettingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

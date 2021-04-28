import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { CommonModule, registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { ContactComponent } from './pages/contact/contact.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { NzListModule } from 'ng-zorro-antd/list';
import { StatusComponent } from './pages/status/status.component';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { SettingsComponent } from './pages/settings/settings.component';
import { RegisterComponent } from './pages/register/register.component';
import {NzFormModule} from 'ng-zorro-antd/form';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
// used to create fake backend
import {AuthGuard, fakeBackendProvider} from './_helpers';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { JwtInterceptor, ErrorInterceptor } from './_helpers';
import { HomeComponent } from './pages/home';
import { LoginComponent } from './pages/login';
import { IconsProviderModule } from 'src/icons-provider.module';
import {RouterModule} from '@angular/router';

@NgModule({
    imports: [
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule,
        NzLayoutModule,
        NzMenuModule,
        NzListModule,
        NzGridModule,
        NzButtonModule,
        NzIconModule,
        NzUploadModule,
        NzFormModule,
        CommonModule,
        BrowserModule,
        IconsProviderModule,
        BrowserAnimationsModule,
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        LoginComponent,
        SignInComponent,
        ProfileComponent,
        StatusComponent,
        SettingsComponent,
        RegisterComponent,
        ContactComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        { provide: NZ_I18N, useValue: en_US },

        // provider used to create fake backend
        fakeBackendProvider,
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }

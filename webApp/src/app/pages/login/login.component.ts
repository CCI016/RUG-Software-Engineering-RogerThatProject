import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WebRequestService } from '@app/services/web.service';
import { UserIDService } from '@app/services/user-id.service';


@Component({ templateUrl: 'login.component.html', styleUrls: ['./login.component.css'] })
export class LoginComponent implements OnInit {
    userId = 1;
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    error = '';

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private webService: WebRequestService,
        private messageService : UserIDService,
    ) {
        // redirect to home if already logged in
    
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.webService.getData("rest/auth/auth?email=" + this.f.username.value + "&password=" + this.f.password.value).subscribe(
             data => {
                    this.messageService.changeUserId(data as string);
                    const returnUrl = '/profile';
                    this.router.navigate([returnUrl]);
             }
        )
    }
}

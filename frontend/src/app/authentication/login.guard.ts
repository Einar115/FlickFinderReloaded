import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "../services/Autentificador/auth.service";

@Injectable({
    providedIn: 'root'
})
export class LoginGuard implements CanActivate{
    constructor(private authService: AuthService, private router: Router){}

    //si hay una sesion activa en alguna pagina se redirige automaticamente a la pagina principal
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
        if (this.authService.isLoggedIn()) {
            this.router.navigate(['/home']);
            return false;
        }
        return true;
    }
}
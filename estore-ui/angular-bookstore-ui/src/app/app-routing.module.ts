import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';
import { LoginComponent } from './login/login.component';
import { NavigationComponent } from './navigation/navigation.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'main', component: NavigationComponent},
  { path: 'admin', component: AdminButtonsComponent},
  { path: '', redirectTo: '/login', pathMatch: 'full' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

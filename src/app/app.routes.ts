import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';

import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RecommendationsComponent } from './pages/recommendations/recommendations.component';
import { PlannerComponent } from './pages/planner/planner.component';
import { ShoppingComponent } from './pages/shopping/shopping.component';
import { SavedPlansComponent } from './pages/saved-plans/saved-plans.component';
import { FavoritesComponent } from './pages/favorites/favorites.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [

  // 🌍 PUBLIC AREA
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // 🔒 PRIVATE APP AREA (ONLY AFTER LOGIN)
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'recommendations', component: RecommendationsComponent },
      { path: 'planner', component: PlannerComponent },
      { path: 'shopping', component: ShoppingComponent },
      { path: 'saved-plans', component: SavedPlansComponent },
      { path: 'favorites', component: FavoritesComponent },

      { path: '', redirectTo: 'profile', pathMatch: 'full' }
    ]
  },

  // fallback
  { path: '**', redirectTo: '' }
];
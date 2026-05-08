import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { HealthProfile } from '../models/health-profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private apiUrl = 'http://localhost:8081/api/profile';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<HealthProfile> {
    return this.http.get<HealthProfile>(this.apiUrl);
  }

  saveProfile(profile: HealthProfile): Observable<HealthProfile> {
    return this.http.post<HealthProfile>(this.apiUrl, profile);
  }
}
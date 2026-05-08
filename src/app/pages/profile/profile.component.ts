import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProfileService } from '../../services/profile.service';
import { HealthProfile } from '../../models/health-profile';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profile: HealthProfile = {
    age: 25,
    gender: '',
    heightCm: 170,
    weightKg: 70,
    activityLevel: '',
    goal: ''
  };

  activityLevels = [
    {
      value: 'sedentary',
      title: 'Sedentary',
      description: 'Little or no exercise'
    },
    {
      value: 'moderate',
      title: 'Moderate',
      description: 'Light exercise 3–5 days/week'
    },
    {
      value: 'active',
      title: 'Active',
      description: 'Moderate exercise 6–7 days/week'
    },
    {
      value: 'very active',
      title: 'Very Active',
      description: 'Hard exercise daily'
    }
  ];

  goals = [
    {
      value: 'lose',
      title: 'Lose Weight',
      description: 'Reduce body fat'
    },
    {
      value: 'maintain',
      title: 'Maintain Weight',
      description: 'Keep current weight'
    },
    {
      value: 'gain',
      title: 'Build Muscle',
      description: 'Increase muscle mass'
    }
  ];

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {

    this.profileService.getProfile().subscribe({
      next: (data) => {
        this.profile = data;
      },
      error: () => {
        console.log('No profile yet');
      }
    });
  }

  saveProfile(): void {

    this.profileService.saveProfile(this.profile).subscribe({
      next: (data) => {
        this.profile = data;
        alert('Profile saved successfully!');
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  selectActivity(level: string): void {
    this.profile.activityLevel = level;
  }

  selectGoal(goal: string): void {
    this.profile.goal = goal;
  }
}
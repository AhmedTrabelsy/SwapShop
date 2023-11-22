import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class SharedService {
  constructor() {}

  calculateAnimationDelay(index?: number): string {
    if(index){
      const delay = 60 * index;
      return `${delay}ms`;
    }
    return "undefined";
  }
}

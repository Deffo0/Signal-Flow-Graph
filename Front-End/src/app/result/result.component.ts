import { Component, OnInit } from '@angular/core';
import { HomeComponent } from '../home/home/home.component';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  static data:string[][];
  constructor() { }

  ngOnInit():void {
    console.log(ResultComponent.data);
  }
  get data():string[][]{
    return ResultComponent.data;
  }
}

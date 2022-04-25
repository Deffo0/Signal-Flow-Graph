import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  constructor() { }
  public data :string[][] = [["6","5","4","1"],["asa"],["451"],["olk"],["lol"]];
  ngOnInit() {
  }

}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable()
export class HomeService {

  constructor(private http:HttpClient) { }
  public generateNetwork(productionNetwork : string):Observable<string>{
    return this.http.post("http://localhost:8080/generateNetwork", productionNetwork,{responseType:"text"})
  }

  public getResult():Observable<string[][]>{
    return this.http.get<string[][]>("http://localhost:8080/getResult")
  }

}
